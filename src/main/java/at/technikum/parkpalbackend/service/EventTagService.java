package at.technikum.parkpalbackend.service;

import at.technikum.parkpalbackend.exception.EntityNotFoundException;
import at.technikum.parkpalbackend.model.EventTag;
import at.technikum.parkpalbackend.persistence.EventTagRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Slf4j
@Service
public class EventTagService {

    private final EventTagRepository eventTagRepository;

    public EventTagService(EventTagRepository eventTagRepository) {
        this.eventTagRepository = eventTagRepository;
    }

    public Set<EventTag> findAllEventTagSet() {
        return new HashSet<>(eventTagRepository.findAll());
    }

    public EventTag save(EventTag eventTag) {
        return eventTagRepository.save(eventTag);
    }

    public EventTag findTagById(String eventTagId) {
        try {
            return eventTagRepository.findById(eventTagId)
                    .orElseThrow(EntityNotFoundException::new);
        } catch (EntityNotFoundException e) {
            log.warn("Failed to find tag with ID: {}", eventTagId, e);
            throw new EntityNotFoundException("Failed to find tag with ID: %s"
                    .formatted(eventTagId));
        }
    }

    public EventTag deleteTagById(String eventTagId) {
        try {
            EventTag eventTag  = findTagById(eventTagId);
            eventTagRepository.delete(eventTag);
            return eventTag;
        } catch (EntityNotFoundException e) {
            log.warn("Failed to find tag with ID: {}", eventTagId, e);
            throw new EntityNotFoundException("Failed to find tag with ID: %s"
                    .formatted(eventTagId));
        } catch (DataAccessException e) {
            log.warn("DatabaseError:Failed to access tag with ID: {}", eventTagId, e);
            throw new RuntimeException("Failed to delete Tag: %s".formatted(e.getMessage()));
        } catch (Throwable e) {
            log.warn("Unknown Error:Failed to delete tag with ID: {}\n", eventTagId, e);
            throw new RuntimeException("Failed to delete Tag: %s".formatted(e.getMessage()));
        }
    }

    public EventTag updateTag(String eventTagId, EventTag updatedEventTag) {
        try {
            EventTag eventTag = findTagById(eventTagId);
            eventTag.setName(updatedEventTag.getName());
            eventTag.setEvents(updatedEventTag.getEvents());
            return save(eventTag);
        } catch (EntityNotFoundException e) {
            log.error("Failed to find tag with ID: {}", eventTagId, e);
            throw new EntityNotFoundException("Failed to find tag with ID: %s"
                    .formatted(eventTagId));
        } catch (DataAccessException e) {
            log.error("DatabaseError:Failed to access tag with ID: {}", eventTagId, e);
            throw new RuntimeException("Failed to update Tag: %s".formatted(e.getMessage()));
        } catch (Throwable e) {
            log.error("Unknown Error:Failed to update tag with ID: {}\n", eventTagId, e);
            throw new RuntimeException("Failed to update Tag: %s".formatted(e.getMessage()));
        }
    }
}
