package at.technikum.parkpalbackend.service;

import at.technikum.parkpalbackend.exception.EntityNotFoundException;
import at.technikum.parkpalbackend.model.Event;
import at.technikum.parkpalbackend.persistence.EventRepository;
import lombok.extern.slf4j.Slf4j;
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
        if (event == null) {
            log.error("Invalid Event in save(). Event is null.");
            throw new IllegalArgumentException("The event Cannot be null.");
        }
        return eventRepository.save(event);
    }

    public List<Event> findAllEvents() {
        return eventRepository.findAll();
    }

    public Event findByEventId(String eventId) {
        return eventRepository.findById(eventId)
                .orElseThrow(() -> new EntityNotFoundException("Failed to find event with ID: %s."
                        .formatted(eventId)));
    }

    public List<Event> findAllEventsCreatedByUser(String userId) {
        if (userService.findByUserId(userId) != null) {
            return eventRepository.findAllByCreatorId(userId);
        }
        return Collections.emptyList();
    }

    public Event deleteEventById(String eventID) {
        Event eventToDelete = findByEventId(eventID);
        eventRepository.delete(eventToDelete);
        return eventToDelete;
    }

    public Event updateEvent(String id, Event updatedEvent) {
        if (id == null) {
            log.error("Invalid Event ID in updateEvent(). Event ID is null.");
            throw new IllegalArgumentException("The event ID Cannot be null");
        }
        if (updatedEvent == null) {
            log.error("Invalid updated Event in updateEvent(). Updated Event is null.");
            throw new IllegalArgumentException("The updated event Cannot be null.");
        }
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

    }
}
