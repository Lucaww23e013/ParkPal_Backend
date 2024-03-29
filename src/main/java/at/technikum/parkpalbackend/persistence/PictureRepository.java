package at.technikum.parkpalbackend.persistence;

import at.technikum.parkpalbackend.model.Picture;
import at.technikum.parkpalbackend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PictureRepository extends JpaRepository<Picture, String> {
    Optional<Picture> findPictureByPictureId(String pictureId);

    Optional<Picture> findPictureByUser(User user);

}
