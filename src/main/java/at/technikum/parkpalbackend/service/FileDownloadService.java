package at.technikum.parkpalbackend.service;

import at.technikum.parkpalbackend.exception.FileNotFoundException;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.InputStream;

@Service
public class FileDownloadService {

    private final MinioService minioService;

    public FileDownloadService(MinioService minioService) {
        this.minioService = minioService;
    }

    public ResponseEntity<?> downloadFile(String fileName, String folderName) {
        try {
            String objectName = folderName + "/" + fileName;
            InputStream inputStream = minioService.getFile(objectName);
            String contentType = minioService.getContentType(objectName);

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION,
                            "attachment; filename=\"" + fileName + "\"")
                    .contentType(MediaType.parseMediaType(contentType))
                    .body(new InputStreamResource(inputStream));
        } catch (FileNotFoundException e) {
            return ResponseEntity.status(404).body("File not found: " + fileName);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Internal server error");
        }
    }
}
