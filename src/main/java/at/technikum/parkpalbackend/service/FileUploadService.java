package at.technikum.parkpalbackend.service;

import at.technikum.parkpalbackend.model.FileMetadata;
import at.technikum.parkpalbackend.persistence.FileMetadataRepository;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.UUID;

@Service
public class FileUploadService {

    private final MinioService minioService;
    private final FileMetadataRepository fileMetadataRepository;

    @Value("${file.upload.max-size-mb}")
    private long maxFileSizeMb;

    public FileUploadService(MinioService minioService,
                             FileMetadataRepository fileMetadataRepository) {
        this.minioService = minioService;
        this.fileMetadataRepository = fileMetadataRepository;
    }

    public ResponseEntity<String> uploadFile(MultipartFile file,String folderNa) {
        try {
            String fileNa = file.getOriginalFilename();
            if (fileNa == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid file");
            }
            fileNa = sanitizeFileOrFolderName(fileNa);

            ResponseEntity<String> badRequest = checkMaxFileSizeAndSendResponse(file);
            if (badRequest != null) return badRequest;
            InputStream inputStream = file.getInputStream();
            String contentType = file.getContentType();
            String uuid = UUID.randomUUID().toString();
            String objectName = folderNa + "/" + uuid;
            minioService.uploadFile(objectName, inputStream, contentType);
            // Save file metadata
            FileMetadata fileMetadata = FileMetadata.builder()
                    .path(objectName)
                    .assigned(false)
                    .filename(fileNa)
                    .build();
            fileMetadataRepository.save(fileMetadata);
            return ResponseEntity.status(HttpStatus.OK)
                    .body("File uploaded successfully.FileID:%s"
                            .formatted(uuid));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("File upload failed.");
        }
    }

    @Nullable
    private ResponseEntity<String> checkMaxFileSizeAndSendResponse(MultipartFile file) {
        long maxFileSize = maxFileSizeMb * 1024 * 1024; // Convert MB to bytes
        if (file.getSize() > maxFileSize) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("File size exceeds the maximum limit.");
        }
        return null;
    }

    // Replace all characters that are not letters, numbers, dots, or hyphens with underscores
    private String sanitizeFileOrFolderName(String fileName) {
        return fileName.replaceAll("[^a-zA-Z0-9.\\-]", "_");
    }
}