package at.technikum.parkpalbackend.persistence;

import at.technikum.parkpalbackend.model.File;
import at.technikum.parkpalbackend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FileRepository extends JpaRepository<File, String> {
    List<File> getPicturesByUser(User user);
}
