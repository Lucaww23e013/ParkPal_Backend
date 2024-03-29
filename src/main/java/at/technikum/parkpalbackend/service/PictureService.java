package at.technikum.parkpalbackend.service;

import at.technikum.parkpalbackend.model.Picture;
import at.technikum.parkpalbackend.model.User;
import at.technikum.parkpalbackend.persistence.PictureRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PictureService {

    private final PictureRepository pictureRepository;


    public PictureService(PictureRepository pictureRepository) {
        this.pictureRepository = pictureRepository;
    }

    public List<Picture> findAllPictures() {
        return pictureRepository.findAll();
    }

    public Picture findPictureByPictureId(String pictureId) {
        return pictureRepository.findPictureByPictureId(pictureId).orElseThrow();
    }

    public List<Picture> getPictureByUserId(User user) {
        return pictureRepository.findPictureByUser(user).stream().toList();
    }

    public Picture save(Picture createdPicture) {
        return pictureRepository.save(createdPicture);
    }

    public Picture updatePicture(String pictureId, Picture updatedPicture) {
        Picture picture = pictureRepository.findPictureByPictureId(pictureId).orElseThrow();

        picture.setPictureId(updatedPicture.getPictureId());
        picture.setUser(updatedPicture.getUser());
        return pictureRepository.save(picture);
    }

    public Picture deletePictureByPictureId(String pictureId) {
        Picture picture = pictureRepository.findPictureByPictureId(pictureId).orElseThrow();
        pictureRepository.delete(picture);
        return null;
    }

    public LocalDateTime getUploadDate() { //change with FileController
        return LocalDateTime.now();
    }
}
