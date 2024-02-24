package at.technikum.parkpalbackend.controller;

import at.technikum.parkpalbackend.dto.eventdtos.CreateEventDto;
import at.technikum.parkpalbackend.dto.eventdtos.DeleteEventDto;
import at.technikum.parkpalbackend.dto.eventdtos.EventDto;
import at.technikum.parkpalbackend.mapper.EventMapper;
import at.technikum.parkpalbackend.model.Event;
import at.technikum.parkpalbackend.model.Park;
import at.technikum.parkpalbackend.model.User;
import at.technikum.parkpalbackend.service.EventService;
import at.technikum.parkpalbackend.service.ParkService;
import at.technikum.parkpalbackend.service.UserService;
import at.technikum.parkpalbackend.service.interfaces.IEventService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/events")
@CrossOrigin
public class EventController {

    private final IEventService iEventService;

    private final ParkService parkService;

    private final UserService userService;

    private final EventMapper eventMapper;


    public EventController(EventService eventService, ParkService parkService, UserService userService, EventMapper eventMapper) {
        this.iEventService = eventService;
        this.parkService = parkService;
        this.userService = userService;
        this.eventMapper = eventMapper;
    }


    @GetMapping
    public List<EventDto> getAllEvents() {
        List<Event> events = iEventService.findAllEvents();
        return events.stream()
                .map(getEventDto -> eventMapper.toDto(getEventDto))
                .toList();
    }

    @GetMapping("/{parkId}")
    public List<EventDto> getAllEventsByParkId(@PathVariable String parkId) {
        List<Event> events = iEventService.findAllEventsByPark(parkId);
        return events.stream()
                .map(event -> eventMapper.toDto(event))
                .toList();
    }

    @GetMapping("/{userId}")
    public List<EventDto> getAllEventsByUserId(@PathVariable String userId) {
        List<Event> events = iEventService.findAllEventsByUser(userId);
        return events.stream()
                .map(event -> eventMapper.toDto(event))
                .toList();
    }

    @GetMapping("/{eventId}")
    public EventDto getEventByID(@PathVariable String eventId) {
        return eventMapper.toDto(iEventService.findByEventId(eventId));
    }



    //@PostMapping ????
    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public CreateEventDto createEvent(@RequestBody @Valid CreateEventDto createEventDto) {
        Event event = eventMapper.toEntityCreateEvent(createEventDto);
        event = iEventService.save(event);
        return eventMapper.toDtoCreateEvent(event);
    }

    @DeleteMapping("/{eventID}")
    @ResponseStatus(HttpStatus.OK)
    public DeleteEventDto deleteEventDto(@PathVariable @Valid String eventID) {
        Event event = iEventService.deleteEventById(eventID);
        return null;
    }

}
