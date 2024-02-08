package at.technikum.parkpalbackend.mapper;

import at.technikum.parkpalbackend.dto.EventTagDto;
import at.technikum.parkpalbackend.model.Event;
import at.technikum.parkpalbackend.model.EventTag;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class EventTagMapperTest {

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void whenDtoValue_toEventTagValue() {
        EventTagMapper eventTagMapper = new EventTagMapper();
        String eventTagId = UUID.randomUUID().toString();
        //Event event = new Event(UUID.randomUUID().toString());
        Event event = Event.builder().build();
        String tagName = "TagName";

        EventTag eventTag = new EventTag(eventTagId, event, tagName);

        EventTagDto eventTagDto = eventTagMapper.toDto(eventTag);

        assertEquals(eventTagId, eventTagDto.getEventTagId());
        assertEquals(tagName, eventTagDto.getTagName());

        assertEquals(event.getEventId(), eventTagDto.getEventID());

    }

    @Test
    void whenEventTagValue_toDtoValue() {
        EventTagMapper eventTagMapper = new EventTagMapper();
        String eventId = UUID.randomUUID().toString();
        String tagName = "TagName";

        EventTagDto eventTagDto = EventTagDto.builder()
                .eventID(eventId)
                .tagName(tagName)
                .build();


        EventTag eventTag = eventTagMapper.toEntity(eventTagDto);

        assertEquals(eventTagDto.getEventTagId(), eventTag.getEventTagId());
        assertEquals(tagName, eventTag.getTagName());

        assertEquals(eventId,eventTag.getEvent().getEventId());

    }
}