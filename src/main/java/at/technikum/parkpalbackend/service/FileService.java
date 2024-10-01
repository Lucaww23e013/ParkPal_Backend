package at.technikum.parkpalbackend.service;

import at.technikum.parkpalbackend.exception.FileNotFoundException;
import at.technikum.parkpalbackend.model.File;
import at.technikum.parkpalbackend.model.User;
import at.technikum.parkpalbackend.model.enums.FileType;
import at.technikum.parkpalbackend.persistence.FileRepository;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.*;

@Service
public class FileService {

    private final MinioService minioService;
    private final EventService eventService;
    private final ParkService parkService;
    private final UserService userService;
    private final FileRepository fileRepository;
    private static final String[] VALID_PICTURE_EXTENSIONS = {"jpg", "jpeg", "png", "gif"};
    private static final String[] VALID_VIDEO_EXTENSIONS = {"mp4", "avi", "mov", "mkv", "webm"};

    @Value("${file.upload.max-size-mb}")
    private long maxFileSizeMb;

    public FileService(MinioService minioService,
                       EventService eventService,
                       ParkService parkService, UserService userService,
                       FileRepository fileRepository) {
        this.minioService = minioService;
        this.eventService = eventService;
        this.parkService = parkService;
        this.userService = userService;
        this.fileRepository = fileRepository;
    }

    @Transactional
    public ResponseEntity<String> uploadFile(MultipartFile file, FileType fileType) {
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
            saveFileDetails(fileName, objectName, uuid, fileType);

            return ResponseEntity.status(HttpStatus.OK)
                    .body(objectName.split("/")[1]);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("File upload failed.");
        }
    }

    @Transactional(readOnly = true)
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

    @Transactional(readOnly = true)
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

    @Transactional
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

    @Transactional
    public void assignProfilePicture(User user, String profilePictureId, boolean saveUser) {
        if (profilePictureId != null && !profilePictureId.isEmpty()) {
            // Remove existing profile picture if any
            List<File> media = user.getMedia();
            if (media != null) {
                media.removeIf(file -> file.getFileType() == FileType.PROFILE_PICTURE);
            } else {
                media = new ArrayList<>();
            }

            // Assign new profile picture
            File profilePicture = this
                    .retrieveAndAssignFileById(profilePictureId, user.getId(), null, null, true);
            profilePicture.setFileType(FileType.PROFILE_PICTURE);
            media.add(profilePicture);
            user.setMedia(media);
            if (saveUser) {
                userService.save(user);
            }
        }
    }

    @Transactional
    public File save(File file) {
        return fileRepository.save(file);
    }

    private File retrieveAndAssignFileById(String fileId,
                                           String userId,
                                           String eventId,
                                           String parkId,
                                           boolean throwException) {
        if (fileId == null || fileId.isEmpty()) {
            return null;
        }

        Optional<File> optionalFile = fileRepository.findByExternalId(fileId);
        if (optionalFile.isEmpty() && throwException) {
            throw new FileNotFoundException("File with id %s not found".formatted(fileId));
        }

        File file = optionalFile.orElse(null);
        if (file != null) {
            if (eventId != null) {
                file.setEvent(eventService.findByEventId(eventId));
            }
            if (parkId != null) {
                file.setPark(parkService.findParkByParkId(parkId));
            }
            if (userId != null) {
                file.setUser(userService.findByUserId(userId));
            }
        }

        return file;
    }

    private void saveFileDetails(String fileName,
                                 String objectName,
                                 String uuid,
                                 FileType fileType) {
        File fileDetails = File.builder()
                .path(objectName)
                .assigned(false)
                .externalId(uuid)
                .filename(fileName)
                .fileType(fileType != null ? fileType : FileType.OTHER)
                .build();
        fileRepository.save(fileDetails);
    }
}