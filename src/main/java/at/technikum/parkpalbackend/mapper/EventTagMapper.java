package at.technikum.parkpalbackend.mapper;

import at.technikum.parkpalbackend.dto.EventTagDto;
import at.technikum.parkpalbackend.model.Event;
import at.technikum.parkpalbackend.model.EventTag;
import org.springframework.stereotype.Component;

@Component
public class EventTagMapper {

    public EventTagDto toDto(EventTag eventTag) {
        return EventTagDto.builder()
                .eventTagId(eventTag.getEventTagId())
                .tagName(eventTag.getTagName())
                .build();
    }

    public EventTag toEntity(EventTagDto eventTagDto) {
        return EventTag.builder()
                .tagName(eventTagDto.getTagName())
                .event(Event.builder().eventId(eventTagDto.getEventID()).build()) //replace with better code?
                .build();
    }

}
