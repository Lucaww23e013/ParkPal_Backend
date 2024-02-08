package at.technikum.parkpalbackend.persistence;

import at.technikum.parkpalbackend.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EventRepository extends JpaRepository<Event, String> {

    Optional<Event> findByEventId(String eventId);
    List<Event> findAllByPark_ParkId(String parkId);
    List<Event> findAllByCreator_UserId(String creator_userId);

}
