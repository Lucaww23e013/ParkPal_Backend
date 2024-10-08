package at.technikum.parkpalbackend.mapper;

import at.technikum.parkpalbackend.dto.eventtagdtos.CreateEventTagDto;
import at.technikum.parkpalbackend.dto.eventtagdtos.EventTagDto;
import at.technikum.parkpalbackend.dto.eventtagdtos.UpdateEventTagDto;
import at.technikum.parkpalbackend.model.Event;
import at.technikum.parkpalbackend.model.EventTag;
import at.technikum.parkpalbackend.service.EventService;
import at.technikum.parkpalbackend.service.EventTagService;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class EventTagMapper {

    private final EventMapper eventMapper;
    private final EventService eventService;
    private final EventTagService eventTagService;

    @Autowired
    public EventTagMapper(EventMapper eventMapper,
                          EventService eventService,
                          EventTagService eventTagService) {
        this.eventMapper = eventMapper;
        this.eventService = eventService;
        this.eventTagService = eventTagService;
    }


    public EventTagDto toDto(EventTag eventTag) {
        return EventTagDto.builder()
                .id(eventTag.getId())
                .name(eventTag.getName())
                .eventIds(getEventIds(eventTag))
                .build();
    }

    @NotNull
    private static Set<String> getEventIds(EventTag eventTag) {
        if (eventTag.getEvents() == null) {
            return new HashSet<>(1);
        }
        return eventTag.getEvents().stream()
                .map(Event::getId)
                .collect(Collectors.toSet());
    }


    public EventTag toEntity(EventTagDto eventTagDto) {
        return EventTag.builder()
                .id(eventTagDto.getId())
                .name(eventTagDto.getName())
                .events(eventService.findEventsByIds(eventTagDto.getEventIds()))
                .build();
    }

    public EventTag toEntity(CreateEventTagDto createEventTagDto) {
        Set<Event> events = eventService.findEventsByIds(createEventTagDto.getEventIds());
        EventTag eventTag = EventTag.builder()
                .name(createEventTagDto.getName())
                .events(events)
                .build();
        // Maintain bidirectional relationship
        events.forEach(event -> event.addEventTags(eventTag));
        return eventTag;
    }

    public EventTag toEntity(UpdateEventTagDto updateEventTagDto,
                             String eventTagId) {
        Set<Event> newEvents = updateEventTagDto.getEventIds().stream()
                .map(eventId -> eventService.findByEventId(eventId))
                .collect(Collectors.toSet());

        EventTag eventTag = eventTagService.findTagById(eventTagId);
        Set<Event> currentEvents = eventTag.getEvents();

        // Remove associations for events that are no longer linked
        removeOldEventAssociations(currentEvents, newEvents, eventTag);

        // Add associations for new events
        addNewEventAssociations(newEvents, currentEvents, eventTag);

        eventTag.setName(updateEventTagDto.getName());
        eventTag.setEvents(newEvents);

        return eventTag;
    }


//    public Set<EventDto> toEventDtos(Set<Event> eventSet) {
//        return eventSet.stream()
//                .map(event -> eventMapper.toDto(event))
//                .collect(Collectors.toSet());
//    }

//    public Set<Event> toEvents(Set<EventDto> eventDtoSet) {
//        return eventDtoSet.stream()
//                .map(eventDto -> eventMapper.toEntity(eventDto))
//                .collect(Collectors.toSet());
//    }

    private static void removeOldEventAssociations(Set<Event> currentEvents,
                                                   Set<Event> newEvents,
                                                   EventTag eventTag) {
        currentEvents.stream()
                .filter(event -> !newEvents.contains(event))
                .forEach(event -> event.removeEventTags(eventTag));
    }

    private static void addNewEventAssociations(Set<Event> newEvents,
                                                Set<Event> currentEvents,
                                                EventTag eventTag) {
        newEvents.stream()
                .filter(event -> !currentEvents.contains(event))
                .forEach(event -> event.addEventTags(eventTag));
    }

}
