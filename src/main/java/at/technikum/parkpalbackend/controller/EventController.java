package at.technikum.parkpalbackend.controller;

import at.technikum.parkpalbackend.dto.eventdtos.CreateEventDto;
import at.technikum.parkpalbackend.dto.eventdtos.DeleteEventDto;
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

@RestController
@RequestMapping("/events")
@CrossOrigin
public class EventController {

    private final EventService eventService;

    private final ParkService parkService;

    private final UserService userService;

    private final EventMapper eventMapper;


    public EventController(EventService eventService, ParkService parkService,
                           UserService userService, EventMapper eventMapper) {
        this.eventService = eventService;
        this.parkService = parkService;
        this.userService = userService;
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
    public List<EventDto> getAllEvents() {
        List<Event> events = eventService.findAllEvents();
        return events.stream()
                .map(getEventDto -> eventMapper.toDto(getEventDto))
                .toList();
    }
    @GetMapping("/{eventId}")
    public EventDto getEventByID(@PathVariable String eventId) {
        return eventMapper.toDto(eventService.findByEventId(eventId));
    }

    @PutMapping("/{eventID}")
    public ResponseEntity<EventDto> updateEventDto(@RequestBody @Valid EventDto newEventDto,
                                                   @PathVariable String eventID) {
        Event mappedEntity = eventMapper.toEntity(newEventDto);
        Event updatedEvent = eventService.updateEvent(eventID, mappedEntity);
        return ResponseEntity.ok(eventMapper.toDtoAllArgs(updatedEvent));
    }
    @DeleteMapping("/{eventID}")
    @ResponseStatus(HttpStatus.OK)
    public DeleteEventDto deleteEventDto(@PathVariable @Valid String eventID) {
        eventService.deleteEventById(eventID);
        return null;
    }

}
