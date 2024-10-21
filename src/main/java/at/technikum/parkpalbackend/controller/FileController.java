package at.technikum.parkpalbackend.controller;

import at.technikum.parkpalbackend.model.enums.FileType;
import at.technikum.parkpalbackend.service.FileService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/files")
public class FileController {

    private final FileService fileService;

    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @GetMapping
    public ResponseEntity<List<String>> listAllFiles(
            @RequestParam(required = false) String eventId,
            @RequestParam(required = false) String parkId,
            @RequestParam(required = false) String userId) {
        List<String> files = fileService.listAllFiles(eventId, parkId, userId);
        return ResponseEntity.ok(files);
    }

    @PostMapping
    public ResponseEntity<String> uploadPictureOrVideo(
            @RequestParam("file") MultipartFile file,
            @RequestParam(value = "fileType", required = false,
                    defaultValue = "OTHER") FileType fileType,
            @RequestParam(value = "userId", required = false) String userId
    ) {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("File is empty");
        }
        return fileService.uploadFile(file, fileType, userId);
    }

    @GetMapping("/{externalId}")
    public ResponseEntity<?> downloadPictureOrVideo(
            @PathVariable("externalId") String externalId) {
        return fileService.downloadFile(externalId);
    }

    @DeleteMapping("/{externalId}")
    public ResponseEntity<String> deletePictureOrVideo(
            @PathVariable("externalId") String externalId) {
        return fileService.deleteFileByExternalId(externalId);
    }

}
