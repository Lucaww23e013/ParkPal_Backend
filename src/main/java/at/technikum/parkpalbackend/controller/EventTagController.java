package at.technikum.parkpalbackend.controller;

import at.technikum.parkpalbackend.dto.EventTagDto;
import at.technikum.parkpalbackend.mapper.EventTagMapper;
import at.technikum.parkpalbackend.model.EventTag;
import at.technikum.parkpalbackend.service.EventService;
import at.technikum.parkpalbackend.service.EventTagService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/event-tags")
@CrossOrigin


public class EventTagController {

    private final EventTagService eventTagService;
    private final EventService eventService;

    private final EventTagMapper eventTagMapper;

    public EventTagController(EventTagService eventTagService, EventService eventService,
                              EventTagMapper eventTagMapper) {
        this.eventTagService = eventTagService;
        this.eventService = eventService;
        this.eventTagMapper = eventTagMapper;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EventTagDto createEventTag(@RequestBody @Valid EventTagDto eventTagDto) {
        EventTag eventTag = eventTagMapper.toEntity(eventTagDto);
        eventTag = eventTagService.save(eventTag);
        return eventTagMapper.toDto(eventTag);
    }

    @GetMapping
    public Set<EventTagDto> getAllTags(
            @RequestParam(required = false)
            String eventId) {
        Set<EventTag> eventTags;
        if (eventId != null) {
            eventTags = eventTagService.findTagsByEventId(eventId);
        } else {
            eventTags = eventTagService.findAllEventTagSet();
        }
        return eventTags.stream()
                .map(eventTag -> eventTagMapper.toDto(eventTag))
                .collect(Collectors.toSet());
    }

    @GetMapping("/{eventTagId}")
    public ResponseEntity<EventTagDto> getEventTagById(@PathVariable @Valid String eventTagId) {
        EventTag eventTag = eventTagService.findTagById(eventTagId);
        return ResponseEntity.ok(eventTagMapper.toDto(eventTag));
    }

    // TODO: Implement updateEventTag method correctly
    @PutMapping("/{eventTagId}")
    public ResponseEntity<EventTagDto> updateEventTag(@PathVariable String eventTagId,
                                                      @RequestBody @Valid EventTagDto eventTagDto) {
        EventTag updatedEventTag = eventTagMapper.toEntity(eventTagDto);
        updatedEventTag = eventTagService.updateTag(eventTagId, updatedEventTag);
        return ResponseEntity.ok(eventTagMapper.toDto(updatedEventTag));
    }

    @DeleteMapping("/{eventTagId}")
    public ResponseEntity<Void> deleteEventTagById(@PathVariable @Valid String eventTagId) {
        eventTagService.deleteTagById(eventTagId);
        return ResponseEntity.noContent().build();
    }
}
