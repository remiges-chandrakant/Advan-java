package tech.remiges.workshop.Component;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import tech.remiges.workshop.Entity.Employee;
import tech.remiges.workshop.Service.IReportGenerator;

@Component("xmldocGen")
public class xmlDocGenerator implements IReportGenerator {

    @Autowired
    ReportFileFolderUtils reportfileFolder;

    @Override
    public byte[] GenerateReport(List<Employee> datalist) {
        try {
            JAXBContext context = JAXBContext.newInstance(Employee.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            String filePath = reportfileFolder.getFilepath() + "/" + reportfileFolder.getFilepre() + "output.xlsx";

            File file = new File(filePath);
            Employee emp = datalist.get(0);
            marshaller.marshal(emp, file);

            Path path = Paths.get(file.getAbsolutePath());
            byte[] data = Files.readAllBytes(path);
            System.out.println("Excel file created successfully!");
            return data;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
