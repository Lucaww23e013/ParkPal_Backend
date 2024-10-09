package at.technikum.parkpalbackend.controller;

import at.technikum.parkpalbackend.dto.eventdtos.CreateEventDto;
import at.technikum.parkpalbackend.dto.eventdtos.EventDto;
import at.technikum.parkpalbackend.mapper.EventMapper;
import at.technikum.parkpalbackend.model.Event;
import at.technikum.parkpalbackend.service.EventService;
import at.technikum.parkpalbackend.service.ParkService;
import at.technikum.parkpalbackend.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/events")
@CrossOrigin
public class EventController {

    private final EventService eventService;

    private final EventMapper eventMapper;


    public EventController(EventService eventService, ParkService parkService,
                           UserService userService, EventMapper eventMapper) {
        this.eventService = eventService;
        this.eventMapper = eventMapper;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CreateEventDto createEvent(@RequestBody @Valid CreateEventDto createEventDto) {
        Event event = eventMapper.toEntityCreateEvent(createEventDto);
        event = eventService.save(event);
        return eventMapper.toDtoCreateEvent(event);
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

    @PutMapping("/{eventID}")
    public ResponseEntity<EventDto> updateEventDto(@RequestBody @Valid EventDto newEventDto,
                                                   @PathVariable String eventId) {

        Event mappedEntity = eventMapper.toEntity(newEventDto, Optional.of(eventId));
        Event updatedEvent = eventService.updateEvent(eventId, mappedEntity);
        return ResponseEntity.ok(eventMapper.toDtoAllArgs(updatedEvent));
    }

    @DeleteMapping("/{eventID}")
    public ResponseEntity<Void> deleteEventDto(@PathVariable @Valid String eventID) {
        eventService.deleteEventById(eventID);
        return ResponseEntity.noContent().build();
    }

}
