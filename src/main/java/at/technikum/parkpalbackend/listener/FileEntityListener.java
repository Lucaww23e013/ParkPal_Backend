package at.technikum.parkpalbackend.listener;

import at.technikum.parkpalbackend.model.File;
import at.technikum.parkpalbackend.service.MinioService;
import jakarta.persistence.PreRemove;
import org.springframework.stereotype.Component;

@Component
public class FileEntityListener {

    private final MinioService minioService;

    public FileEntityListener(MinioService minioService) {
        this.minioService = minioService;
    }

    @PreRemove
    public void preRemove(File file) {
        if (minioService.doesFileExist(file.getPath())) {
            minioService.deleteFile(file.getPath());
        }
    }
}
