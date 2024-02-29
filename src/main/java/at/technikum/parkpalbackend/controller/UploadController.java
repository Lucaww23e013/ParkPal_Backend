package at.technikum.parkpalbackend.controller;

import at.technikum.parkpalbackend.service.UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.HttpStatus;

import java.io.IOException;

@RestController
@RequestMapping("/upload")
@CrossOrigin
public class UploadController {

    @Autowired
    private UploadService uploadService;

    @PostMapping("/upload")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<String> fileUpload(@RequestParam("file") MultipartFile file)
            throws IOException {
        return uploadService.processAndSaveFile(file);
    }
}


