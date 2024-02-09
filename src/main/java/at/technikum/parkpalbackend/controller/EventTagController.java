package at.technikum.parkpalbackend.controller;

import at.technikum.parkpalbackend.dto.EventTagDto;
import at.technikum.parkpalbackend.mapper.EventTagMapper;
import at.technikum.parkpalbackend.model.EventTag;
import at.technikum.parkpalbackend.service.EventService;
import at.technikum.parkpalbackend.service.EventTagService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/event-tags")
@CrossOrigin


public class EventTagController {

    private final EventTagService eventTagService;
    private final EventService eventService;

    private final EventTagMapper eventTagMapper;

    public EventTagController(EventTagService eventTagService, EventService eventService, EventTagMapper eventTagMapper) {
        this.eventTagService = eventTagService;
        this.eventService = eventService;
        this.eventTagMapper = eventTagMapper;
    }

    @GetMapping
    public List<EventTagDto> getAllTags() {
        List<EventTag> eventTags = eventTagService.findAllTags();
        return eventTags.stream()
                .map(eventTag -> eventTagMapper.toDto(eventTag))
                .toList();
    }

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public EventTagDto createEventTag(@RequestBody @Valid EventTagDto eventTagDto) {
        EventTag eventTag = eventTagMapper.toEntity(eventTagDto);
        eventTag = eventTagService.save(eventTag);
        return eventTagMapper.toDto(eventTag);
    }

    @GetMapping("/{eventTagId}")
    public EventTagDto getEventTagById(@RequestBody @Valid String eventTagId) {
        EventTag eventTag = eventTagService.findTagById(eventTagId);
        return eventTagMapper.toDto(eventTag);
    }
}
