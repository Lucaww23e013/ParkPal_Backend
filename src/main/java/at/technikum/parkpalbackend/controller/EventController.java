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
import org.springframework.http.ResponseEntity;
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
        Park park = parkService.findParkByParkId(createEventDto.getParkId());
        User user = userService.findByUserId(createEventDto.getCreatorUserId());
        event.setPark(park);
        event.setCreator(user);

        event = iEventService.save(event);

        return eventMapper.toDtoCreateEvent(event);
    }



    @DeleteMapping("/{eventID}")
    @ResponseStatus(HttpStatus.OK)
    public DeleteEventDto deleteEventDto(@PathVariable @Valid String eventID) {
        Event event = iEventService.deleteEventById(eventID);
        return null;
    }

    // PATCH /events/{eventID}		(change elements of the Event with a given EventId)
    @PutMapping("/{eventID}")
    public ResponseEntity<EventDto> updateEventDto(@RequestBody EventDto newEventDto, @PathVariable @Valid String eventID) {
        Event mappedEntity = eventMapper.toEntity(newEventDto);
        Event updatedEvent = iEventService.updateEvent(eventID, mappedEntity);
        return ResponseEntity.ok(eventMapper.toDtoAllArgs(updatedEvent));
    }


//    PATCH /events/{eventID}		(change elements of the Event with a given EventId)
//
//    DELETE /events/{eventID}		(User delete an event with a given eventId)
//
//    PATCH /events/join			(join event with an given EventId from a given 						UserId)
//
//    PATCH /events/unjoin 			(Unjoin event with an EventId from a given UserId)
//
//    PUT /events /{eventId}			(User can change Event Details)
//
//    GET /media/{eventId}			(Gets all Media for a certain Event with a eventId)





}
