package tech.remiges.workshop.Component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.Getter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Component
@Getter
public class ReportFileFolderUtils {

    private static final Logger logger = LoggerFactory.getLogger(ReportFileFolderUtils.class);

    @Value("${user.home}")
    private String userHome;

    @Value("${filepath}")
    private String filepath;

    @Value("${filenamepre}")
    private String filepre;

    public String checkPathExist() {
        // Concatenate user's home directory with the desired path
        String folderPath = filepath;

        try {
            // Create the directory if it doesn't exist
            Path path = Paths.get(folderPath);
            Files.createDirectories(path);
        } catch (IOException e) {
            // e.printStackTrace();
            logger.error(e.toString());
            // Handle the exception as needed
        }

        return folderPath;
    }
}
