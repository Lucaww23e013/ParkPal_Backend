package at.technikum.parkpalbackend.mapper;

import at.technikum.parkpalbackend.dto.EventTagDto;
import at.technikum.parkpalbackend.dto.eventdtos.EventDto;
import at.technikum.parkpalbackend.model.Event;
import at.technikum.parkpalbackend.model.EventTag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class EventTagMapper {

    private final EventMapper eventMapper;

    @Autowired
    public EventTagMapper(EventMapper eventMapper) {
        this.eventMapper = eventMapper;
    }


    public EventTagDto toDto(EventTag eventTag) {
        return EventTagDto.builder()
                .name(eventTag.getName())
                .eventDtoSet(toEventDtos(eventTag.getEvents()))
                .build();
    }

    public EventTag toEntity(EventTagDto eventTagDto) {
        return EventTag.builder()
                .name(eventTagDto.getName())
                .events(toEvents(eventTagDto.getEventDtoSet()))
                .build();
    }


    private Set<EventDto> toEventDtos(Set<Event> eventSet) {
        return eventSet.stream()
                .map(event -> eventMapper.toDto(event))
                .collect(Collectors.toSet());
    }

    private Set<Event> toEvents(Set<EventDto> eventDtoSet) {
        return eventDtoSet.stream()
                .map(eventDto -> eventMapper.toEntity(eventDto))
                .collect(Collectors.toSet());
    }

}
