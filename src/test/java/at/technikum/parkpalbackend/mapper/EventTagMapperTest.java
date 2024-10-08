package at.technikum.parkpalbackend.mapper;

import at.technikum.parkpalbackend.TestFixtures;
import at.technikum.parkpalbackend.dto.eventtagdtos.EventTagDto;
import at.technikum.parkpalbackend.model.EventTag;
import at.technikum.parkpalbackend.service.EventService;
import at.technikum.parkpalbackend.service.EventTagService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
@ExtendWith(MockitoExtension.class)
class EventTagMapperTest {

    @Mock
    private EventMapper eventMapper;

    @Mock
    private EventService eventService;

    @Mock
    private EventTagService eventTagService;

    @InjectMocks
    private EventTagMapper eventTagMapper;

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
        // Act
        EventTag eventTag = eventTagMapper.toEntity(eventTagDto);
        // Assert
        assertEquals(eventTagDto.getName(), eventTag.getName());
    }

//    @Test
//    @DisplayName("Should map Event set to EventDto set correctly")
//    void shouldMapEventSetToEventDtoSetCorrectly() {
//        // Arrange
//        Set<Event> eventSet = new HashSet<>();
//        Event event = TestFixtures.pingPongGame;
//        eventSet.add(event);
//
//        Set<EventDto> eventDtoSet = new HashSet<>();
//        EventDto eventDto = EventDto.builder().build();
//        eventDtoSet.add(eventDto);
//
//        when(eventMapper.toDto(event)).thenReturn(eventDto);
//        // Act
//        Set<EventDto> mappedEventDtoSet = eventTagMapper.toEventDtos(eventSet);
//        // Assert
//        assertEquals(eventDtoSet, mappedEventDtoSet);
//    }

//    @Test
//    @DisplayName("Should map EventDto set to Event set correctly")
//    @Disabled
//    void shouldMapEventDtoSetToEventSetCorrectly() {
//        // Arrange
//        Set<EventDto> eventDtoSet = new HashSet<>();
//        EventDto eventDto = EventDto.builder().build();
//        eventDto.setTitle("Test Event");
//        eventDtoSet.add(eventDto);
//
//        Set<Event> eventSet = new HashSet<>();
//        Event event = Event.builder().build();
//        event.setTitle("Test Event");
//        eventSet.add(event);
//
//        //when(eventMapper.toEntity(eventDto)).thenReturn(event);
//        // Act
//        Set<Event> mappedEventSet = eventTagMapper.toEvents(eventDtoSet);
//        // Assert
//        assertEquals(eventSet, mappedEventSet);
//    }
}