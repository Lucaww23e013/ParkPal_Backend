package at.technikum.parkpalbackend.persistence;

import at.technikum.parkpalbackend.model.File;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileMetadataRepository extends JpaRepository<File, String> {
}