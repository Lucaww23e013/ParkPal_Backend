package at.technikum.parkpalbackend.persistence;

import at.technikum.parkpalbackend.model.User;
import at.technikum.parkpalbackend.model.Video;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface VideoRepository extends JpaRepository<Video, String> {
    Optional<Video> findVideoByUser(User user);
}

