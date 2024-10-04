package at.technikum.parkpalbackend.persistence;

import at.technikum.parkpalbackend.model.EventTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface EventTagRepository extends JpaRepository<EventTag, String> {

    @Query("SELECT DISTINCT et " +
            "FROM EventTag et " +
            "JOIN FETCH et.events e " +
            "WHERE e.id = :eventId")
    Set<EventTag> findTagsByEventId(@Param("eventId") String eventId);
}
