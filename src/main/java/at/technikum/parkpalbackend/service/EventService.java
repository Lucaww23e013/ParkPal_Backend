package at.technikum.parkpalbackend.service;

import at.technikum.parkpalbackend.exception.EntityNotFoundException;
import at.technikum.parkpalbackend.model.Event;
import at.technikum.parkpalbackend.persistence.EventRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
public class EventService {

    private final EventRepository eventRepository;
    private final UserService userService;

    public EventService(EventRepository eventRepository, UserService userService) {
        this.eventRepository = eventRepository;
        this.userService = userService;
    }

    public Event save(Event event) {
        return eventRepository.save(event);
    }

    public List<Event> findAllEvents() {
        return eventRepository.findAll();
    }

    public Event findByEventId(String eventId) {
        try {
            return eventRepository.findById(eventId)
                    .orElseThrow(EntityNotFoundException::new);
        } catch (EntityNotFoundException e) {
            log.warn("Failed to find event with ID: {}", eventId, e);
            throw new EntityNotFoundException("Failed to find event with ID: %s"
                    .formatted(eventId));
        }
    }

    public List<Event> findAllEventsCreatedByUser(String userId) {
        try {
            if (userService.findByUserId(userId) != null) {
                return eventRepository.findAllByCreatorId(userId);
            }
        } catch (EntityNotFoundException e) {
            log.error("Failed to find user with ID: {}", userId, e);
            throw new EntityNotFoundException("Failed to find events for user with ID: %s"
                    .formatted(userId));
        } catch (Throwable e) {
            log.error("Unknown Error:Failed to find events for user with ID: {}", userId, e);
            throw new EntityNotFoundException("Failed to find events for user with ID: %s"
                    .formatted(userId));
        }
        return Collections.emptyList();
    }

    public Event deleteEventById(String eventID) {
        try {
            Event eventToDelete = findByEventId(eventID);
            eventRepository.delete(eventToDelete);
            return eventToDelete;
        } catch (EntityNotFoundException e) {
            log.warn("Failed to delete event with ID {}: Event not found", eventID, e);
            throw new EntityNotFoundException("Failed to delete event with ID: %s Event not found"
                    .formatted(eventID), e);
        } catch (DataAccessException e) {
            log.error("Database error: Failed to delete event with ID: {}", eventID, e);
            throw new RuntimeException("Failed to delete event with ID: " + eventID, e);
        } catch (Throwable e) {
            log.error("Failed to delete event with ID: {}", eventID, e);
            throw new RuntimeException("Failed to delete event with ID: %s"
                    .formatted(eventID), e);
        }
    }

    public Event updateEvent(String id, Event updatedEvent) {
        try {
            Event existingEvent = findByEventId(id);
            // update the existing event
            existingEvent.setTitle(updatedEvent.getTitle());
            existingEvent.setDescription(updatedEvent.getDescription());
            existingEvent.setStartTS(updatedEvent.getStartTS());
            existingEvent.setEndTS(updatedEvent.getEndTS());
            existingEvent.setPark(updatedEvent.getPark());
            existingEvent.setJoinedUsers(updatedEvent.getJoinedUsers());
            existingEvent.setTags(updatedEvent.getTags());
            existingEvent.setEventPictures(updatedEvent.getEventPictures());
            existingEvent.setEventVideos(updatedEvent.getEventVideos());
            return eventRepository.save(existingEvent);
        } catch (EntityNotFoundException e) {
            log.error("Failed to find event with ID: {}", id, e);
            throw new EntityNotFoundException("Failed to find event with ID: %s".formatted(id));
        } catch (DataAccessException e) {
            log.error("DatabaseError:Failed to access event with ID: {}", id, e);
            throw new RuntimeException("Failed to update Event: %s".formatted(e.getMessage()));
        } catch (Throwable e) {
            log.error("Unknown Error:Failed to update event with ID: {}\n", id, e);
            throw new RuntimeException("Failed to update Event: %s".formatted(e.getMessage()));
        }
    }
}
