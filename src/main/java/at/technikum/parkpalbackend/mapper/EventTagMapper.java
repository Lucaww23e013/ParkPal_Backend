package at.technikum.parkpalbackend.mapper;

import at.technikum.parkpalbackend.dto.EventTagDto;
import at.technikum.parkpalbackend.model.EventTag;
import org.springframework.stereotype.Component;

@Component
public class EventTagMapper {

    public EventTagDto toDto(EventTag eventTag) {
        return EventTagDto.builder()
                .name(eventTag.getName())
                .build();
    }

    public EventTag toEntity(EventTagDto eventTagDto) {
        return EventTag.builder()
                .name(eventTagDto.getName())
                .build();
    }

}
