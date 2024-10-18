package at.technikum.parkpalbackend.controller;

import at.technikum.parkpalbackend.dto.eventdtos.CreateEventDto;
import at.technikum.parkpalbackend.dto.eventdtos.EventDto;
import at.technikum.parkpalbackend.mapper.EventMapper;
import at.technikum.parkpalbackend.model.Event;
import at.technikum.parkpalbackend.security.principal.UserPrincipal;
import at.technikum.parkpalbackend.service.EventService;
import at.technikum.parkpalbackend.util.EventUtil;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/events")
@CrossOrigin
public class EventController {

    private final EventService eventService;
    private final EventUtil eventUtil;
    private final EventMapper eventMapper;


    public EventController(EventService eventService,
                           EventUtil eventUtil,
                           EventMapper eventMapper) {
        this.eventService = eventService;
        this.eventUtil = eventUtil;
        this.eventMapper = eventMapper;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<CreateEventDto> createEvent(
            @RequestBody @Valid CreateEventDto createEventDto) {
        // Use the service method to save the event and return the CreateEventDto
        CreateEventDto createdEventDto = eventUtil.saveCreateEvent(createEventDto);

        // Return the created EventDto in the response body
        return ResponseEntity.status(HttpStatus.CREATED).body(createdEventDto);
    }

    @GetMapping
    public List<EventDto> getAllEvents(
            @RequestParam(required = false) String userId,
            @RequestParam(required = false) String parkId) {
        List<Event> events;
        if (userId != null && parkId != null) {
            events = eventService.findAllEventsByUserIdAndParkId(userId, parkId);
        } else if (userId != null) {
            events = eventService.findAllEventsCreatedByUser(userId);
        } else if (parkId != null) {
            events = eventService.findAllEventsByParkId(parkId);
        } else {
            events = eventService.findAllEvents();
        }
        return events.stream()
                .map(eventMapper::toDto)
                .toList();
    }

    @GetMapping("/{eventId}")
    public EventDto getEventByID(@PathVariable String eventId) {
        return eventMapper.toDto(eventService.findByEventId(eventId));
    }

    @PutMapping("/{eventId}")
    public ResponseEntity<EventDto> updateEvent(@RequestBody @Valid EventDto eventDto,
                                                @PathVariable String eventId) {
        Event updatedEvent = eventUtil.updateEvent(eventId, eventDto);
        EventDto updatedEventDto = eventMapper.toDto(updatedEvent);
        return ResponseEntity.ok(updatedEventDto);
    }

    @PostMapping("/{eventId}/participation")
    public ResponseEntity<List<String>> manageEventParticipation(
            @PathVariable String eventId,
            @RequestParam boolean isJoining,
            Authentication authentication) {
        String userId = ((UserPrincipal) authentication.getPrincipal()).getId();
        eventService.manageUserParticipation(eventId, userId, isJoining);
        List<String> joinedUsernames = eventService.getJoinedUsernames(eventId);
        return ResponseEntity.ok(joinedUsernames);
    }

    @DeleteMapping("/{eventId}")
    public ResponseEntity<Void> deleteEventDto(@PathVariable @Valid String eventId) {
        eventService.deleteEventById(eventId);
        return ResponseEntity.noContent().build();
    }

}
