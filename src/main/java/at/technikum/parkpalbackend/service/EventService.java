package at.technikum.parkpalbackend.service;

import at.technikum.parkpalbackend.exception.EntityNotFoundException;
import at.technikum.parkpalbackend.model.Event;
import at.technikum.parkpalbackend.model.File;
import at.technikum.parkpalbackend.persistence.EventRepository;
import at.technikum.parkpalbackend.persistence.FileRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
public class EventService {

    private final EventRepository eventRepository;
    private final FileRepository fileRepository;
    private final UserService userService;

    public EventService(EventRepository eventRepository,
                        FileRepository fileRepository,
                        UserService userService) {
        this.eventRepository = eventRepository;
        this.fileRepository = fileRepository;
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
        if (eventId == null) {
            log.error("Invalid Event ID in findByEventId(). Event ID is null.");
            throw new EntityNotFoundException("The event ID Cannot be null.");
        }
        return eventRepository.findById(eventId)
                .orElseThrow(() -> new EntityNotFoundException("Failed to find event with ID: %s."
                        .formatted(eventId)));
    }

    public List<Event> findAllEventsCreatedByUser(String userId) {
        if (userService.findByUserId(userId) != null) {
            return eventRepository.findAllByCreatorId(userId);
        }
        return new ArrayList<>();
    }

    public List<Event> findAllEventsJoinedByUser(String userId) {
        if (userService.findByUserId(userId) != null) {
            return eventRepository.findAllByJoinedUsersId(userId);
        }
        return new ArrayList<>();
    }

    public String findEventCreatorUserId(String eventId) {
        Event event = findByEventId(eventId);
        if (event != null) {
            return event.getCreator().getId();
        }
        return null;
    }

    public List<Event> getEventList(List<String> eventIds) {
        if (eventIds == null) {
            return new ArrayList<>();
        }

        return eventIds.stream()
                .map(this::findByEventId)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public String findEventCreatorName(String eventId) {
        Event event = findByEventId(eventId);
        if (event != null) {
            return event.getCreator().getUserName();
        }
        return null;
    }

    public Event deleteEventById(String eventID) {
        Event eventToDelete = findByEventId(eventID);
        eventRepository.delete(eventToDelete);
        return eventToDelete;
    }

    // TODO add Files
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
        existingEvent.setCreator(updatedEvent.getCreator());
        existingEvent.setJoinedUsers(updatedEvent.getJoinedUsers());
        existingEvent.setTags(updatedEvent.getTags());
        updateEventFiles(existingEvent, updatedEvent);
        return eventRepository.save(existingEvent);

    }

    public List<Event> findAllEventsByUserIdAndParkId(String userId, String parkId) {
        return eventRepository.findAllByCreatorIdAndParkId(userId, parkId);
    }

    public List<Event> findAllEventsByParkId(String parkId) {
        return eventRepository.findAllByParkId(parkId);
    }


    public Set<Event> findEventsByIds(Set<String> eventIds) {
        return new HashSet<>(eventRepository.findAllById(eventIds));
    }

    private void updateEventFiles(Event event, Event updatedEvent) {
        // Disassociate existing files from the event
        List<File> eventMedia = new ArrayList<>(event.getMedia());
        if (!eventMedia.isEmpty()) {
            for (File file : eventMedia) {
                file.setEvent(null);
                fileRepository.save(file);
            }
            eventMedia.clear();
        }

        // Associate new files with the event
        List<File> updatedEventMedia = new ArrayList<>(updatedEvent.getMedia());
        if (!updatedEventMedia.isEmpty()) {
            for (File file : updatedEventMedia) {
                file.setEvent(event);
                fileRepository.save(file);
                eventMedia.add(file);
            }
        }

        event.setMedia(eventMedia);
    }
}
