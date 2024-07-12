package at.technikum.parkpalbackend.service;

import at.technikum.parkpalbackend.mapper.FileMapper;
import at.technikum.parkpalbackend.model.File;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class UploadService {

    private static final List<String> ALLOWED_PICTURE_TYPES
            = new ArrayList<>(Arrays.asList("jpg", "jpeg", "png", "gif"));
    private static final List<String> ALLOWED_VIDEO_TYPES
            = new ArrayList<>(Arrays.asList("mp4", "avi", "mov"));


    private final FileMapper fileMapper;
    private final FileService fileService;

    public UploadService(FileMapper fileMapper, FileService fileService) {
        this.fileMapper = fileMapper;
        this.fileService = fileService;
    }

    public ResponseEntity<String> processAndSaveFile(MultipartFile file) throws IOException {
        String fileExtension = StringUtils.getFilenameExtension(file.getOriginalFilename());

        if (fileExtension != null) {
            if (ALLOWED_PICTURE_TYPES.contains(fileExtension.toLowerCase())
                    ||
                    ALLOWED_VIDEO_TYPES.contains(fileExtension.toLowerCase())) {
                File picture = fileMapper.fromMultipartFileToEntity(file);
                picture.setUploadDate(LocalDateTime.now());
                fileService.save(picture);

                return ResponseEntity.ok("File uploaded successfully");
            }
        }

        return ResponseEntity.badRequest().body("Invalid file type. Allowed picture types: "
                + String.join(", ", ALLOWED_PICTURE_TYPES) +
                ". Allowed video types: " + String.join(", ", ALLOWED_VIDEO_TYPES));
    }
    public byte[] transferToBytes(MultipartFile file) throws IOException {
        try {
            return file.getBytes();
        } catch (IOException e) {
            throw new IOException("Failed to read bytes from MultipartFile", e);
        }
    }
}
