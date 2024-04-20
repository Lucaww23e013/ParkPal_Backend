package at.technikum.parkpalbackend.persistence;

import at.technikum.parkpalbackend.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EventRepository extends JpaRepository<Event, String> {

    List<Event> findAllByCreatorId(String creatorUserId);
}
