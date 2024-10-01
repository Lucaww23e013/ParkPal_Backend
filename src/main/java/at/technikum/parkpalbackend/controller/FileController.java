package at.technikum.parkpalbackend.controller;

import at.technikum.parkpalbackend.model.enums.FileType;
import at.technikum.parkpalbackend.service.FileService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/files")
public class FileController {

    private final FileService fileService;

    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @PostMapping
    public ResponseEntity<String> uploadPictureOrVideo(
            @RequestParam("file") MultipartFile file,
            @RequestParam(value = "fileType",
                    required = false, defaultValue = "OTHER") FileType fileType) {
        return fileService.uploadFile(file, fileType);
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
