package at.technikum.parkpalbackend.persistence;

import at.technikum.parkpalbackend.model.Picture;
import at.technikum.parkpalbackend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PictureRepository extends JpaRepository<Picture, String> {
    List<Picture> getPicturesByUser(User user);
}
