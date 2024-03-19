package tech.remiges.workshop.Component;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.hibernate.annotations.Comment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import tech.remiges.workshop.Entity.Employee;
import tech.remiges.workshop.Service.IDateLoader;
import tech.remiges.workshop.Service.IReportGenerator;
import tech.remiges.workshop.controller.EmployeeController;

@Component("XlsReport")
public class XlsReportGenerator implements IReportGenerator {

    private static final Logger logger = LoggerFactory.getLogger(XlsReportGenerator.class);

    @Autowired
    ReportFileFolderUtils reportfileFolder;

    @Override
    public byte[] GenerateReport(List<Employee> datalist) {

        String filePath = "";
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Sheet1");

            reportfileFolder.checkPathExist();

            // Create header row
            Row headerRow = sheet.createRow(0);

            List<String> headers = datalist.get(0).GetHeaders();

            for (int ii = 0; ii < headers.size(); ii++) {
                Cell headerCell = headerRow.createCell(ii);
                headerCell.setCellValue(headers.get(ii).toString());

            }

            for (int jj = 0; jj < datalist.size(); jj++) {
                Row row = sheet.createRow(jj + 1);
                List<String> data = datalist.get(jj).GetData();

                // Create data rows
                for (int i = 0; i < data.size(); i++) {
                    Cell cell = row.createCell(i);
                    cell.setCellValue(data.get(i));
                }

            }

            filePath = reportfileFolder.getFilepath() + "/" + reportfileFolder.getFilepre() + "output.xlsx";

            // Write workbook to file
            try (FileOutputStream outputStream = new FileOutputStream(filePath)) {
                workbook.write(outputStream);
            }

            File file = new File(filePath);

            Path path = Paths.get(file.getAbsolutePath());
            byte[] data = Files.readAllBytes(path);
            System.out.println("Excel file created successfully!");
            return data;

        } catch (IOException e) {
            // e.printStackTrace();
            logger.error(e.toString());
        }

        return null;
    }

}
