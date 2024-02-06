package at.technikum.parkpalbackend.controller;

import at.technikum.parkpalbackend.dto.event.CreateEventDto;
import at.technikum.parkpalbackend.dto.event.EventDto;
import at.technikum.parkpalbackend.mapper.EventMapper;
import at.technikum.parkpalbackend.model.Event;
import at.technikum.parkpalbackend.service.EventService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/events")
@CrossOrigin
public class EventController {

    private final EventService eventService;

    private final EventMapper eventMapper;


    public EventController(EventService eventService, EventMapper eventMapper) {
        this.eventService = eventService;
        this.eventMapper = eventMapper;
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

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CreateEventDto createEvent(@RequestBody @Valid CreateEventDto createEventDto) {
        Event event = eventMapper.toEntityCreateEvent(createEventDto);
        event = eventService.save(event);

        return eventMapper.toDtoCreateEvent(event);
    }




}
