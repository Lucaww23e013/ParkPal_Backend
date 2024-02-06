package at.technikum.parkpalbackend.repository;

import at.technikum.parkpalbackend.model.EventTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventTagRepository extends JpaRepository<EventTag, String> {
    // List<EventTag>
}
