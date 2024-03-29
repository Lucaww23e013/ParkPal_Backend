package at.technikum.parkpalbackend.service;

import at.technikum.parkpalbackend.exception.EntityNotFoundException;
import at.technikum.parkpalbackend.model.Event;
import at.technikum.parkpalbackend.persistence.EventRepository;
import at.technikum.parkpalbackend.service.interfaces.IEventService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventService implements IEventService {

    private final EventRepository eventRepository;

    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public Event save(Event event) {
        return eventRepository.save(event);
    }

    public List<Event> findAllEvents() {
        return eventRepository.findAll();
    }

    public Event findByEventId(String eventId) {
        return eventRepository.findByEventId(eventId)
                .orElseThrow(EntityNotFoundException::new);
    }

    public List<Event> findAllEventsByPark(String parkId) {
        return eventRepository.findAllByParkParkId(parkId);
    }

    public List<Event> findAllEventsByUser(String userId) {
        List<Event> events = eventRepository.findAllByCreatorUserId(userId);
        // check if even the user exists
        // if the User has not created any Event ??!!
        return events;
    }

    public Event deleteEventById(String eventID) {
        Event eventToDelete = eventRepository.findByEventId(eventID)
                .orElseThrow(EntityNotFoundException::new);
        eventRepository.delete(eventToDelete);
        return eventToDelete;
    }

    public Event updateEvent(String id, Event updatedEvent) {
        try {
            Event existingEvent = findByEventId(id);
            existingEvent.setTitle(updatedEvent.getTitle());
            existingEvent.setDescription(updatedEvent.getDescription());
            existingEvent.setStartTS(updatedEvent.getStartTS());
            existingEvent.setEndTS(updatedEvent.getEndTS());
            existingEvent.setPark(updatedEvent.getPark());
            //existingEvent.setCreator(updatedEvent.getCreator());
            existingEvent.setJoinedUsers(updatedEvent.getJoinedUsers());
            existingEvent.setEventTags(updatedEvent.getEventTags());
            existingEvent.setEventMedia(updatedEvent.getEventMedia());
            return eventRepository.save(existingEvent);
        } catch (Exception e) {
            throw new RuntimeException("Failed to update Event: %s".formatted(e.getMessage()));
        }
    }
}
