package at.technikum.parkpalbackend.mapper;

import at.technikum.parkpalbackend.dto.EventTagDto;
import at.technikum.parkpalbackend.model.EventTag;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assumptions.assumeThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class EventTagMapperTest {

    @Autowired
    private EventTagMapper eventTagMapper;


    @BeforeEach
    void setUp() {
        assumeThat(eventTagMapper).isNotNull();
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void whenDtoValue_toEventTagValue() {
        // given
        String tagName = "Family";
        EventTag eventTag = EventTag.builder()
                .tagName(tagName)
                .build();
        // when
        EventTagDto eventTagDto = eventTagMapper.toDto(eventTag);
        // then
        assertEquals(tagName, eventTagDto.getTagName());
    }

    @Test
    void whenEventTagValue_toDtoValue() {
        // given
        String tagName = "Funky";
        EventTagDto eventTagDto = EventTagDto.builder()
                .tagName(tagName)
                .build();
        // when
        EventTag eventTag = eventTagMapper.toEntity(eventTagDto);
        // then
        assertEquals(tagName, eventTag.getTagName());
    }
}