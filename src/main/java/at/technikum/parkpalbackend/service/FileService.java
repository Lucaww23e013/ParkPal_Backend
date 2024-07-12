package at.technikum.parkpalbackend.service;

import at.technikum.parkpalbackend.exception.EntityNotFoundException;
import at.technikum.parkpalbackend.model.File;
import at.technikum.parkpalbackend.model.User;
import at.technikum.parkpalbackend.persistence.FileRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FileService {

    private final FileRepository fileRepository;


    public FileService(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }

    public List<File> findAllPictures() {
        return fileRepository.findAll();
    }

    public File findPictureByPictureId(String pictureId) {
        return fileRepository.findById(pictureId).orElseThrow(EntityNotFoundException::new);
    }

    public List<File> findPicturesByUser(User user) {
        return fileRepository.getPicturesByUser(user).stream().toList();
    }

    public File save(File createdFile) {
        return fileRepository.save(createdFile);
    }

    public File updatePicture(String pictureId, File updatedFile) {
        File file = fileRepository
                .findById(pictureId)
                .orElseThrow(EntityNotFoundException::new);

        file.setId(updatedFile.getId());
        file.setUser(updatedFile.getUser());
        return fileRepository.save(file);
    }

    public File deletePictureByPictureId(String pictureId) {
        File file = fileRepository
                .findById(pictureId)
                .orElseThrow(EntityNotFoundException::new);
        fileRepository.delete(file);
        return file;
    }
}
