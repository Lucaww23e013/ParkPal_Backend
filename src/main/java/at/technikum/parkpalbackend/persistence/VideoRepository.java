package at.technikum.parkpalbackend.persistence;

import at.technikum.parkpalbackend.model.User;
import at.technikum.parkpalbackend.model.Video;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VideoRepository extends JpaRepository<Video, String> {
    List<Video> findVideosByUser(User user);
}

