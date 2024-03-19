package tech.remiges.workshop.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.apache.fontbox.ttf.TrueTypeFont;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import tech.remiges.workshop.Entity.Employee;
import tech.remiges.workshop.Service.IReportGenerator;

@Component("PDFReport")
public class PDFGenerator implements IReportGenerator {

    @Autowired
    ReportFileFolderUtils reportfileFolder;

    @Override
    public byte[] GenerateReport(List<Employee> datalist) {
        String filePath = "";
        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage();
            document.addPage(page);

            reportfileFolder.checkPathExist();

            PDPageContentStream contentStream = new PDPageContentStream(document, page);
            // Load the TrueType font file
            ClassPathResource resource = new ClassPathResource("font/Ubuntu-Light.ttf");
            File fontFile = resource.getFile();
            // File fontFile = new File(""); // Path to your font file
            PDType0Font font = PDType0Font.load(document, fontFile);
            float margin = 50;
            float yStart = page.getMediaBox().getHeight() - margin;
            float tableWidth = page.getMediaBox().getWidth() - 2 * margin;

            // Draw the table headers
            float cellHeight = 70;
            float tableYPosition = yStart;
            float textXPosition = margin + 10;
            float textYPosition = tableYPosition - 15;
            contentStream.beginText();
            contentStream.setFont(font, 12);
            contentStream.newLineAtOffset(textXPosition, textYPosition);
            contentStream.showText("Employee Name");
            contentStream.newLineAtOffset(150, 0);
            contentStream.showText("Department");
            contentStream.endText();
            contentStream.moveTo(margin, tableYPosition - 15);
            contentStream.lineTo(margin + tableWidth, tableYPosition - 15);
            contentStream.stroke();

            contentStream.setFont(font, 12);
            for (Employee employee : datalist) {
                tableYPosition -= cellHeight;
                contentStream.beginText();
                contentStream.newLineAtOffset(textXPosition, tableYPosition);
                contentStream.showText(employee.getFirstName());
                contentStream.newLineAtOffset(150, 0);
                contentStream.showText(employee.getDepartment().getDepartmentName());
                contentStream.endText();

                contentStream.moveTo(margin, tableYPosition);
                contentStream.lineTo(margin + tableWidth, tableYPosition);
                contentStream.stroke();
            }

            contentStream.close();
            filePath = reportfileFolder.getFilepath() + "/" + reportfileFolder.getFilepre() + "output.pdf";
            document.save(filePath);

            File file = new File(filePath);

            Path path = Paths.get(file.getAbsolutePath());
            byte[] data = Files.readAllBytes(path);

            System.out.println("PDF file generated successfully!");
            return data;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
