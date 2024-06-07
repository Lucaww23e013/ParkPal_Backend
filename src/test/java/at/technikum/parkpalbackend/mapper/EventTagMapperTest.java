package at.technikum.parkpalbackend.mapper;

import at.technikum.parkpalbackend.TestFixtures;
import at.technikum.parkpalbackend.dto.EventTagDto;
import at.technikum.parkpalbackend.dto.eventdtos.EventDto;
import at.technikum.parkpalbackend.model.Event;
import at.technikum.parkpalbackend.model.EventTag;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class EventTagMapperTest {

    @Mock
    private EventMapper eventMapper;

    private EventTagMapper eventTagMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        eventTagMapper = new EventTagMapper(eventMapper);
    }

    @Test
    @DisplayName("Should map EventTag to EventTagDto correctly")
    void shouldMapEventTagToEventTagDtoCorrectly() {
        // Arrange
        EventTag eventTag = TestFixtures.familyEventTag;
        // Act
        EventTagDto eventTagDto = eventTagMapper.toDto(eventTag);
        // Assert
        assertEquals(eventTag.getName(), eventTagDto.getName());
    }

    @Test
    @DisplayName("Should map EventTagDto to EventTag correctly")
    void shouldMapEventTagDtoToEventTagCorrectly() {
        // Arrange
        EventTagDto eventTagDto = EventTagDto.builder().build();
        eventTagDto.setName("Test Event Tag");
        HashSet<EventDto> eventDtoSet = new HashSet<>();
        eventDtoSet.add(EventDto.builder().build());
        eventTagDto.setEventDtoSet(eventDtoSet);
        // Act
        EventTag eventTag = eventTagMapper.toEntity(eventTagDto);
        // Assert
        assertEquals(eventTagDto.getName(), eventTag.getName());
    }

    @Test
    @DisplayName("Should map Event set to EventDto set correctly")
    void shouldMapEventSetToEventDtoSetCorrectly() {
        // Arrange
        Set<Event> eventSet = new HashSet<>();
        Event event = TestFixtures.pingPongGame;
        eventSet.add(event);

        Set<EventDto> eventDtoSet = new HashSet<>();
        EventDto eventDto = EventDto.builder().build();
        eventDtoSet.add(eventDto);

        when(eventMapper.toDto(event)).thenReturn(eventDto);
        // Act
        Set<EventDto> mappedEventDtoSet = eventTagMapper.toEventDtos(eventSet);
        // Assert
        assertEquals(eventDtoSet, mappedEventDtoSet);
    }

    @Test
    @DisplayName("Should map EventDto set to Event set correctly")
    void shouldMapEventDtoSetToEventSetCorrectly() {
        // Arrange
        Set<EventDto> eventDtoSet = new HashSet<>();
        EventDto eventDto = EventDto.builder().build();
        eventDto.setTitle("Test Event");
        eventDtoSet.add(eventDto);

        Set<Event> eventSet = new HashSet<>();
        Event event = Event.builder().build();
        event.setTitle("Test Event");
        eventSet.add(event);

        when(eventMapper.toEntity(eventDto)).thenReturn(event);
        // Act
        Set<Event> mappedEventSet = eventTagMapper.toEvents(eventDtoSet);
        // Assert
        assertEquals(eventSet, mappedEventSet);
    }
}