package at.technikum.parkpalbackend.repository;

import at.technikum.parkpalbackend.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EventRepository extends JpaRepository<Event, String> {

    Optional<Event> findByEventId(String eventID);

}
