package at.technikum.parkpalbackend.service;

import at.technikum.parkpalbackend.exception.FileNotFoundException;
import at.technikum.parkpalbackend.model.File;
import at.technikum.parkpalbackend.persistence.FileRepository;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.*;

@Service
public class FileService {

    private final MinioService minioService;
    private final FileRepository fileRepository;
    private static final String[] VALID_PICTURE_EXTENSIONS = {"jpg", "jpeg", "png", "gif"};
    private static final String[] VALID_VIDEO_EXTENSIONS = {"mp4", "avi", "mov", "mkv", "webm"};

    @Value("${file.upload.max-size-mb}")
    private long maxFileSizeMb;

    public FileService(MinioService minioService,
                       FileRepository fileRepository) {
        this.minioService = minioService;
        this.fileRepository = fileRepository;
    }

    public ResponseEntity<String> uploadFile(MultipartFile file) {
        try {
            String fileName = getFileName(file);
            if (fileName == null) return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Invalid file");

            String folderName = getFolderName(fileName);
            if (folderName == null) return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Invalid file extension");

            ResponseEntity<String> badRequest = checkMaxFileSizeAndSendResponse(file);
            if (badRequest != null) return badRequest;

            String uuid = UUID.randomUUID().toString();
            String objectName = uploadToMinio(file, folderName, uuid);
            saveFileDetails(fileName, objectName, uuid);

            return ResponseEntity.status(HttpStatus.OK)
                    .body("File uploaded successfully. FileID: %s"
                    .formatted(objectName.split("/")[1]));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("File upload failed.");
        }
    }

    public ResponseEntity<?> downloadFile(String externalId) {
        try {
            File fileDetails = fileRepository.findByExternalId(externalId)
                    .orElseThrow(() -> new FileNotFoundException("File not found: " + externalId));

            String objectName = fileDetails.getPath();
            InputStream inputStream = minioService.getFile(objectName);
            String contentType = minioService.getContentType(objectName);

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\""
                            + fileDetails.getFilename() + "\"")
                    .contentType(MediaType.parseMediaType(contentType))
                    .body(new InputStreamResource(inputStream));
        } catch (FileNotFoundException e) {
            return ResponseEntity.status(404).body("File not found: " + externalId);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Internal server error");
        }
    }

    public List<File> findAllFilesByIds(List<String> fileIds) {
        if (fileIds == null || fileIds.isEmpty()) {
            return new ArrayList<>();
        }
        return fileRepository.findAllById(fileIds);
    }


    public List<File> findFilesByIds(List<String> fileIds) {
        if (fileIds == null || fileIds.isEmpty()) {
            return Collections.emptyList();
        }
        return fileRepository.findAllById(fileIds);
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

    /**
     * Sanitize the file name to prevent path traversal attacks
     * Replace all characters that are not letters, numbers, dots, or hyphens with underscores
     * @param fileName  The file name to sanitize
     * @return The sanitized file name
     */
    private String sanitizeFileName(String fileName) {
        return fileName.replaceAll("[^a-zA-Z0-9.\\-]", "_");
    }

    private String getFolderName(String fileName) {
        String fileExtension = fileName.substring(fileName.lastIndexOf('.') + 1).toLowerCase();

        if (Arrays.asList(VALID_PICTURE_EXTENSIONS).contains(fileExtension)) {
            return "pictures";
        } else if (Arrays.asList(VALID_VIDEO_EXTENSIONS).contains(fileExtension)) {
            return "videos";
        }
        return null;
    }

    private String getFileName(MultipartFile file) {
        String fileName = file.getOriginalFilename();
        return fileName != null ? sanitizeFileName(fileName) : null;
    }

    private String uploadToMinio(MultipartFile file, String folderName, String uuid)
            throws Exception {
        String objectName = folderName + "/" + uuid;
        minioService.uploadFile(objectName,
                file.getInputStream(), file.getContentType());
        return objectName;
    }

    private void saveFileDetails(String fileName, String objectName, String uuid) {
        File fileDetails = File.builder()
                .path(objectName)
                .assigned(false)
                .externalId(uuid)
                .filename(fileName)
                .build();
        fileRepository.save(fileDetails);
    }

    public ResponseEntity<String> deleteFileByExternalId(String externalId) {
        try {
            File fileDetails = fileRepository.findByExternalId(externalId)
                    .orElseThrow(() -> new FileNotFoundException(
                            "File not found: " + externalId));

            // The file from minio will be automatically deleted by the FileEntityListener
            // when the file is removed from the database
            fileRepository.delete(fileDetails);

            return ResponseEntity.status(HttpStatus.OK)
                    .body("File deleted successfully.");
        } catch (FileNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("File deletion failed.");
        }
    }

    public File save(File file) {
        return fileRepository.save(file);
    }
}