package at.technikum.parkpalbackend.persistence;

import at.technikum.parkpalbackend.model.EventTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventTagRepository extends JpaRepository<EventTag, String> {

}
