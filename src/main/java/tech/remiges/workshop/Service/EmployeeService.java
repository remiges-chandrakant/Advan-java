package tech.remiges.workshop.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import tech.remiges.workshop.Entity.Employee;
import tech.remiges.workshop.Entity.EmployeeShadow;
import tech.remiges.workshop.Repository.EmployeeRepository;
import tech.remiges.workshop.Repository.EmployeeShadowRepository;
import tech.remiges.workshop.Utils.CommonUtils;
import tech.remiges.workshop.controller.EmployeeController;

@Service
public class EmployeeService {

    private static final Logger logger = LoggerFactory.getLogger(EmployeeController.class);

    @Autowired
    EmployeeRepository emprepo;

    @Autowired
    EmployeeShadowRepository empshrepo;

    @Autowired
    @Qualifier("PDFReport")
    IReportGenerator pdfreportgen;

    @Autowired
    @Qualifier("XlsReport")
    IReportGenerator xlsreportgen;

    @Autowired
    @Qualifier("xmldocGen")
    IReportGenerator xmlreportgen;

    @Autowired
    private ObjectMapper objectMapper;

    public void yourMethod() {
        // Set MDC properties
        MDC.put("user", "your-user-id");
        MDC.put("api", "your-api-name");
        MDC.put("error_code", "your-error-code");
        MDC.put("error_message", "your-error-message");
        MDC.put("client_reqid", "your-client-reqid");
        MDC.put("req", "your-json-representation-of-request");

        // Log message
        logger.info("Your log message here");

        // Clear MDC properties to prevent memory leaks
        MDC.clear();
    }

    public byte[] GenerateReport(String type) {
        List<Employee> listEmployee = ListallEmployee();

        MDC.put("user", "your-user-id");
        MDC.put("api", "GenerateReport");
        MDC.put("error_code", "your-error-code");
        MDC.put("error_message", "your-error-message");
        MDC.put("client_reqid", "your-client-reqid");
        MDC.put("req", "your-json-representation-of-request");

        logger.info("Generte report");

        if (type.equals("xls")) {
            return xlsreportgen.GenerateReport(listEmployee);

        } else if (type.equals("pdf")) {

            return pdfreportgen.GenerateReport(listEmployee);

        } else if (type.equals("xml")) {
            return xmlreportgen.GenerateReport(listEmployee);

        } else {
            return null;
        }

    }

    public List<Employee> ListallEmployee() {
        return emprepo.findAll();
    }

    public List<Object[]> ListEmployee() {
        return emprepo.findAll().stream()
                .map(employee -> new Object[] { employee.getId(), employee.getFirstName() })
                .collect(Collectors.toList());
    }

    public List<Employee> ListEmployee(String Empname) {
        return emprepo.findByfullNameContaining(Empname);
    }

    public Employee addEmployee(Employee emp) {
        return emprepo.saveAndFlush(emp);
    }

    public Employee PartialUpdate(Employee empp) throws Exception {
        Employee entity = emprepo.findById(empp.getId())
                .orElseThrow(() -> new Exception("Entity not found with id: " + empp.getId()));

        EmployeeShadow empsh = objectMapper.convertValue(entity, EmployeeShadow.class);
        empshrepo.save(empsh);
        // Update only non-null properties of the entity
        BeanUtils.copyProperties(empp, entity, CommonUtils.getNullPropertyNames(empp));

        // Save the updated entity
        return emprepo.save(entity);
    }

    public Employee updateEmployee(Employee emp) throws Exception {
        Optional<Employee> existingEmp = emprepo.findById(emp.getId());
        existingEmp.orElseThrow(() -> new Exception("Employee not found to update"));
        EmployeeShadow empsh = objectMapper.convertValue(existingEmp, EmployeeShadow.class);
        empshrepo.save(empsh);

        return emprepo.save(emp);
    }

    public Optional<Employee> getEmployee(Long empid) {
        return emprepo.findById(empid);
    }

    public void deletemployee(Long empid) {
        emprepo.deleteById(empid);
    }

}
