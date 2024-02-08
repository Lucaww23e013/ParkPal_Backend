package at.technikum.parkpalbackend.persistence;

import at.technikum.parkpalbackend.model.Media;
import at.technikum.parkpalbackend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MediaRepository extends JpaRepository<Media, String> {
    Optional<Media> findMediaByMediaId(String mediaId);

    Optional<Media> findMediaByUser(User user);

}
