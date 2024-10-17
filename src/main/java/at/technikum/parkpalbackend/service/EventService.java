package at.technikum.parkpalbackend.service;

import at.technikum.parkpalbackend.exception.EntityNotFoundException;
import at.technikum.parkpalbackend.exception.InvalidEventTimeException;
import at.technikum.parkpalbackend.model.Event;
import at.technikum.parkpalbackend.model.User;
import at.technikum.parkpalbackend.persistence.EventRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
@Service
public class EventService {

    private final EventRepository eventRepository;
    private final UserService userService;

    public EventService(EventRepository eventRepository,
                        UserService userService) {
        this.eventRepository = eventRepository;
        this.userService = userService;
    }

    @Transactional
    public Event save(Event event) {
        if (event == null) {
            log.error("Invalid Event in save(). Event is null.");
            throw new IllegalArgumentException("The event Cannot be null.");
        }
        validateEventTimes(event.getStartTS(), event.getEndTS());
        return eventRepository.save(event);
    }

    @Transactional(readOnly = true)
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

    @Transactional(readOnly = true)
    public List<Event> findAllEventsCreatedByUser(String userId) {
        if (userService.findByUserId(userId) != null) {
            return eventRepository.findAllByCreatorId(userId);
        }
        return new ArrayList<>();
    }

    @Transactional(readOnly = true)
    public List<Event> findAllEventsJoinedByUser(String userId) {
        if (userService.findByUserId(userId) != null) {
            return eventRepository.findAllByJoinedUsersId(userId);
        }
        return new ArrayList<>();
    }

    @Transactional(readOnly = true)
    public String findEventCreatorUserId(String eventId) {
        Event event = findByEventId(eventId);
        if (event != null) {
            return event.getCreator().getId();
        }
        return null;
    }

    @Transactional(readOnly = true)
    public String findEventCreatorName(String eventId) {
        Event event = findByEventId(eventId);
        if (event != null) {
            return event.getCreator().getUserName();
        }
        return null;
    }

    @Transactional
    public Event deleteEventById(String eventId) {
        Event event = findByEventId(eventId);
        try {
            eventRepository.delete(event);
        } catch (Exception e) {
            throw new RuntimeException("Unknown error occurred while deleting event with id: " +
                    eventId, e);
        }
        return event;
    }

    public List<Event> findAllEventsByUserIdAndParkId(String userId, String parkId) {
        return eventRepository.findAllByCreatorIdAndParkId(userId, parkId);
    }

    @Transactional(readOnly = true)
    public List<Event> findAllEventsByParkId(String parkId) {
        return eventRepository.findAllByParkId(parkId);
    }

    @Transactional(readOnly = true)
    public Set<Event> findEventsByIds(Set<String> eventIds) {
        return new HashSet<>(eventRepository.findAllById(eventIds));
    }

    public void manageUserParticipation(String eventId, String userId, boolean isJoining) {
        Event event = findByEventId(eventId);
        User user = userService.findByUserId(userId);
        if (isJoining) {
            // Check if the user is already joined
            if (event.getJoinedUsers().contains(user)) {
                // Ignore if the user is already part of the event
                return; // Simply return and do nothing
            } else {
                event.addJoinedUsers(user);
                eventRepository.save(event);
            }
        } else {

            if (!event.getJoinedUsers().contains(user)) {

                return;
            } else {
                event.removeJoinedUsers(user);
                eventRepository.save(event);
            }
        }
    }

    public List<String> getJoinedUsernames(String eventId) {
        Event event = findByEventId(eventId);
        return event.getJoinedUsers().stream()
                .map(User::getUserName)
                .toList();
    }

    public void validateEventTimes(LocalDateTime startTS, LocalDateTime endTS) {
        if (startTS.isAfter(endTS) || startTS.isEqual(endTS) ||
                java.time.Duration.between(startTS, endTS).toMinutes() < 30) {
            throw new InvalidEventTimeException("Event start time must be before end time " +
                    "and the duration must be at least 30 minutes.");
        }
    }
}
