package at.technikum.parkpalbackend.mapper;

import at.technikum.parkpalbackend.TestFixtures;
import at.technikum.parkpalbackend.dto.eventdtos.CreateEventDto;
import at.technikum.parkpalbackend.dto.eventdtos.EventDto;
import at.technikum.parkpalbackend.model.Event;
import at.technikum.parkpalbackend.service.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EventMapperTest {

    @Mock
    private ParkService parkService;

    @Mock
    private EventService eventService;

    @Mock
    private EventTagService eventTagService;

    @Mock
    private EventMapper eventMapper;

    @Mock
    private UserService userService;

    @Mock
    private FileService fileService;
    //TODO Check me
    /*
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        eventMapper = new EventMapper(parkService, userService, eventService, eventTagService, fileService);
    }
    */
    /*
    @Test
    @DisplayName("Should map Event to EventDto correctly")
    void shouldMapEventToEventDtoCorrectly() {
        Event event = TestFixtures.grilling;

        EventDto eventDto = eventMapper.toDto(event);

        assertEquals(event.getTitle(), eventDto.getTitle());
    }


    @Test
    @DisplayName("Should map Event to All EventDto correctly")
    @Disabled
    void shouldMapEventToDtoAllArgsCorrectly() {
        Event event = TestFixtures.grilling;

        EventDto eventDto = eventMapper.toDto(event);

        assertEquals(event.getTitle(), eventDto.getTitle());
    }

//    @Test
//    @Disabled
//    @DisplayName("Should map EventDto to Event correctly")
//    void shouldMapEventDtoToEventCorrectly() {
//        EventDto eventDto = EventDto.builder().build();
//        eventDto.setTitle("Test Event");
//
//        Event event = eventMapper.toEntity(eventDto);
//
//        assertEquals(eventDto.getTitle(), event.getTitle());
//    }

    @Test
    @DisplayName("Should map Event to CreateEventDto correctly")
    void shouldMapEventToCreateEventDtoCorrectly() {
        Event event = TestFixtures.pingPongGame;

        CreateEventDto createEventDto = eventMapper.toDtoCreateEvent(event);

        assertEquals(event.getTitle(), createEventDto.getTitle());
    }
    /*
    @Test
    @DisplayName("Should map CreateEventDto to Event correctly")
    void shouldMapCreateEventDtoToEventCorrectly() {
        CreateEventDto createEventDto = CreateEventDto.builder().build();
        createEventDto.setTitle("Test Event");

        Event event = eventMapper.toEntityCreateEvent(createEventDto);

        assertEquals(createEventDto.getTitle(), event.getTitle());
    }
*/
//    @Test
//    @Disabled
//    @DisplayName("Should map CreateEventDto to Alle Event correctly")
//    void shouldMapCreateEventDtoToAllEventArgsCorrectly() {
//        EventDto eventDto = EventDto.builder().build();
//        eventDto.setTitle("Test Event");
//
//        Event event = eventMapper.toEntityAllArgs(eventDto, "1");
//
//        assertEquals(eventDto.getTitle(), event.getTitle());
//    }


}