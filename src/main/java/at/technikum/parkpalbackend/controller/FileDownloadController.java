package at.technikum.parkpalbackend.controller;

import at.technikum.parkpalbackend.service.FileDownloadService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/files")
public class FileDownloadController {

    private final FileDownloadService fileDownloadService;

    public FileDownloadController(FileDownloadService fileDownloadService) {
        this.fileDownloadService = fileDownloadService;
    }

    @GetMapping("/download/picture")
    public ResponseEntity<?> downloadPicture(
            @RequestParam("fileName") String fileName) {
        return fileDownloadService.downloadFile(fileName, "pictures");
    }

    @GetMapping("/download/video")
    public ResponseEntity<?> downloadVideo(
            @RequestParam("fileName") String fileName) {
        return fileDownloadService.downloadFile(fileName, "videos");
    }

}