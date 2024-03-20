package tech.remiges.workshop.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.val;
import tech.remiges.workshop.Constants;
import tech.remiges.workshop.Entity.Config;
import tech.remiges.workshop.Entity.Employee;
import tech.remiges.workshop.Repository.ConfigRepository;
import tech.remiges.workshop.Request.WorkshopRequest;
import tech.remiges.workshop.Service.ConfigService;
import tech.remiges.workshop.Service.EmployeeService;
import tech.remiges.workshop.Service.RedisService;
import tech.remiges.workshop.Utils.CommonUtils;
import tech.remiges.workshop.Utils.ErrorCodes;
import tech.remiges.workshop.Utils.JwtUtils;
import tech.remiges.workshop.WorkshopResponse.WorkshopResponse;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.FileOutputStream;
import java.io.IOException;
import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/myhr/employee")
public class EmployeeController {

    private RedisService redisService;

    private ConfigService configSvc;

    @Value("${filenamepre}")
    private String filepre;

    private static final Logger logger = LoggerFactory.getLogger(EmployeeController.class);

    private EmployeeService empService;

    public EmployeeController(RedisService redisService, ConfigService configSvc, EmployeeService empService) {
        this.redisService = redisService;
        this.configSvc = configSvc;
        this.empService = empService;
    }

    @PostMapping("/add")
    public WorkshopResponse addEmployee(@RequestBody Employee request) {
        // Object objnewemp = request.getData().get("employee");
        Employee newemp = (Employee) request;
        newemp.setEmpId(UUID.randomUUID().toString());
        empService.addEmployee(newemp);
        WorkshopResponse response = new WorkshopResponse(CommonUtils.SUCCESS, "", "", Map.of("", ""),
                "");

        return response;
    }

    @GetMapping("/list")
    public ResponseEntity<?> getEmployeeList(@RequestParam String type) {

        // List<Object[]> listEmployee = empService.ListEmployee();

        byte[] content = empService.GenerateReport(type);

        if (content == null) {

            Map<String, Object> result = new HashMap<>();
            result.put("employeelist", "Employee List is empty");
            WorkshopResponse response = new WorkshopResponse(CommonUtils.SUCCESS, "", "", result,
                    "");
            response.set_serverTs(Instant.now());

            return ResponseEntity.ok().body(response);

        } else {
            String fileName = filepre;
            MediaType contentType = MediaType.APPLICATION_OCTET_STREAM;
            if (type.equals("xls")) {
                fileName += "output.xls";
                contentType = MediaType.APPLICATION_OCTET_STREAM;

            } else if (type.equals("pdf")) {
                contentType = MediaType.APPLICATION_PDF;
                fileName += "output.pdf";

            } else if (type.equals("xml")) {
                contentType = MediaType.APPLICATION_XML;
                fileName += "output.xml";
            }

            // Create response entity
            ByteArrayResource resource = new ByteArrayResource(content);
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName);
            return ResponseEntity.ok()
                    .headers(headers)
                    .contentType(contentType)
                    .body(resource);
        }

    }

    @GetMapping("/get")
    public WorkshopResponse getEmployee(@RequestBody WorkshopRequest request) {

        if (request.getData().size() < 1) {
            WorkshopResponse response = new WorkshopResponse(CommonUtils.FAIL, ErrorCodes.INSUFFICIENTARG.errorcode,
                    ErrorCodes.INSUFFICIENTARG.errorMsg, Map.of("", ""),
                    request.getReqId());
            response.set_serverTs(Instant.now());

            return response;

        }
        String empid = (String) request.getData().get("empid");
        Long lId = Long.valueOf(empid);

        Optional<Employee> employee = empService.getEmployee(lId);
        Map<String, Object> result = new HashMap<>();
        if (employee.isPresent() == false) {
            WorkshopResponse response = new WorkshopResponse(CommonUtils.SUCCESS, ErrorCodes.EMPLOYEENOTFOUND.errorcode,
                    ErrorCodes.EMPLOYEENOTFOUND.errorMsg, result,
                    "");
            response.set_serverTs(Instant.now());

            return response;
        }

        result.put("employee", employee.get());
        WorkshopResponse response = new WorkshopResponse(CommonUtils.SUCCESS, "", "", result,
                "");
        response.set_serverTs(Instant.now());

        return response;
    }

    @GetMapping("/listfilter")
    public WorkshopResponse getFilteredEmployeeList(@RequestParam(value = "filter", required = true) String filter) {

        List<Employee> emplist = empService.ListEmployee(filter);

        Map<String, Object> result = new HashMap<>();
        result.put("employeelist", emplist);
        WorkshopResponse response = new WorkshopResponse(CommonUtils.SUCCESS, "", "", result,
                "");
        response.set_serverTs(Instant.now());

        return response;
    }

    @GetMapping("/update")
    public WorkshopResponse updateEmployee(@RequestBody Employee updateemp) throws Exception {

        Employee updatedemp = empService.updateEmployee(updateemp);
        Map<String, Object> result = new HashMap<>();
        result.put("emp", updatedemp);
        WorkshopResponse response = new WorkshopResponse(CommonUtils.SUCCESS, "", "", result,
                "");
        response.set_serverTs(Instant.now());

        return response;
    }

    @PatchMapping("/{id}")
    public WorkshopResponse partialUpdateEntity(@RequestBody Employee partialEntity) throws Exception {

        Employee updatedemp = empService.PartialUpdate(partialEntity);
        Map<String, Object> result = new HashMap<>();
        result.put("emp", updatedemp);
        WorkshopResponse response = new WorkshopResponse(CommonUtils.SUCCESS, "", "", result,
                "");
        response.set_serverTs(Instant.now());

        return response;
    }

    @GetMapping("/delete")
    public WorkshopResponse deleteEmpployee(@RequestParam String id) {

        Long lId = Long.valueOf(id);
        empService.deletemployee(lId);

        WorkshopResponse response = new WorkshopResponse(CommonUtils.SUCCESS, "", "", Map.of("", "`"),
                "");
        response.set_serverTs(Instant.now());

        return response;
    }

    @GetMapping("getcount")
    public WorkshopResponse getEmployeeCount(@RequestParam String empName) {
        String value = redisService.getValue(Constants.USERCONST + empName);

        Map<String, Object> result = new HashMap<>();
        WorkshopResponse response = null;
        if (value == null) {
            response = new WorkshopResponse(CommonUtils.SUCCESS, "-1", "value for key " + empName + "not exist",
                    Map.of("", "`"),
                    "");

        } else {

            result.put("emp", value);

            response = new WorkshopResponse(CommonUtils.SUCCESS, "", "", Map.of("", "`"),
                    "");
        }
        response.set_serverTs(Instant.now());

        return response;

    }

    @PutMapping("/updateEmployeeContribution")
    public WorkshopResponse updateEmpContri(@RequestBody WorkshopRequest request) {
        if (request.getData().size() < 2) {
            WorkshopResponse response = new WorkshopResponse(CommonUtils.FAIL, ErrorCodes.INSUFFICIENTARG.errorcode,
                    ErrorCodes.INSUFFICIENTARG.errorMsg, Map.of("", ""),
                    request.getReqId());
            response.set_serverTs(Instant.now());

            return response;

        }
        String empid = (String) request.getData().get("empid");
        String deptname = (String) request.getData().get("deptname");
        String count = (String) request.getData().get("count");

        String skey = Constants.USERCONST + deptname + "." + empid;
        String value = redisService.getValue(skey);
        if (value == null) {

            logger.info("Read data from DB" + skey + "not found in  redis cache.");

            List<Config> byConfigName = configSvc.getConfig(skey);
            if (byConfigName != null && byConfigName.isEmpty() == false) {
                Config cvalue = byConfigName.get(0);
                value = cvalue.getConfigValue();
                logger.info("Read data from DB" + skey + "not found in redismy cache.");
                redisService.setValueWithTTL(skey, value, 180);
            } else {

                redisService.setValueWithTTL(skey, "1", 180);
                Config config = new Config();
                config.setConfigName(skey);
                config.setConfigValue("1");
                configSvc.UpdateConfig(config);
                logger.info("set default value for " + skey + " in cache and DB.");
            }

        } else {
            try {
                Integer ival = Integer.valueOf(count);
                redisService.incrementValue(skey, ival);
                redisService.setTTL(skey, 180);

                count = redisService.getValue(skey);

            } catch (Exception e) {
                count = redisService.getValue(skey);
            }

        }

        Map<String, Object> result = new HashMap<>();
        result.put("key", skey);
        result.put("count", count);

        WorkshopResponse response = new WorkshopResponse(CommonUtils.SUCCESS, "", "", result,
                "");

        response.set_serverTs(Instant.now());

        return response;
    }

    @GetMapping("/getContribution")
    public WorkshopResponse getMethodName(@RequestBody WorkshopRequest request) {
        if (request.getData().size() < 2) {
            WorkshopResponse response = new WorkshopResponse(CommonUtils.FAIL, ErrorCodes.INSUFFICIENTARG.errorcode,
                    ErrorCodes.INSUFFICIENTARG.errorMsg, Map.of("", ""),
                    request.getReqId());
            response.set_serverTs(Instant.now());

            return response;

        }

        String empid = (String) request.getData().get("empid");
        String deptname = (String) request.getData().get("deptname");

        String skey = Constants.USERCONST + deptname + "." + empid;
        String value = redisService.getValue(skey);

        if (value == null) {
            List<Config> byConfigName = configSvc.getConfig(skey);
            if (byConfigName != null && byConfigName.isEmpty() == false) {
                Config cvalue = byConfigName.get(0);
                value = cvalue.getConfigValue();
                logger.info("Read data from DB" + skey + "not found in cache.");
                redisService.setValueWithTTL(skey, value, 180);
            }

        }

        Map<String, Object> result = new HashMap<>();
        result.put("key", skey);
        result.put("latest count", value);

        WorkshopResponse response = new WorkshopResponse(CommonUtils.SUCCESS, "", "", result,
                "");

        response.set_serverTs(Instant.now());

        return response;
    }

}
