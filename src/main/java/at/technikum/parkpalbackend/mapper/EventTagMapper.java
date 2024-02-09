package at.technikum.parkpalbackend.mapper;

import at.technikum.parkpalbackend.dto.EventTagDto;
import at.technikum.parkpalbackend.model.EventTag;
import org.springframework.stereotype.Component;

@Component
public class EventTagMapper {

    public EventTagDto toDto(EventTag eventTag) {
        return EventTagDto.builder()
                .tagName(eventTag.getTagName())
                .build();
    }

    public EventTag toEntity(EventTagDto eventTagDto) {
        return EventTag.builder()
                .tagName(eventTagDto.getTagName())
                .build();
    }

}
