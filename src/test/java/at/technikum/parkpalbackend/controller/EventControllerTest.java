package at.technikum.parkpalbackend.controller;

import at.technikum.parkpalbackend.TestFixtures;
import at.technikum.parkpalbackend.dto.eventdtos.CreateEventDto;
import at.technikum.parkpalbackend.dto.eventdtos.EventDto;
import at.technikum.parkpalbackend.exception.EntityNotFoundException;
import at.technikum.parkpalbackend.mapper.EventMapper;
import at.technikum.parkpalbackend.model.Event;
import at.technikum.parkpalbackend.security.filter.JwtAuthenticationFilter;
import at.technikum.parkpalbackend.service.EventService;
import at.technikum.parkpalbackend.service.ParkService;
import at.technikum.parkpalbackend.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.ArrayList;
import java.util.List;


import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(EventController.class)
@AutoConfigureMockMvc(addFilters = false)
public class EventControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EventService eventService;

    @MockBean
    private ParkService parkService;

    @MockBean
    private UserService userService;

    @MockBean
    private EventMapper eventMapper;

    @MockBean
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testCreateEvent() throws Exception {
        // Arrange
        CreateEventDto createEventDto = TestFixtures.testCreateEventDto;
        createEventDto.setTitle("Sample Event");

        Event event = TestFixtures.grilling;
        event.setTitle("Sample Event");

        when(eventMapper.toEntityCreateEvent(any(CreateEventDto.class))).thenReturn(event);
        when(eventService.save(any(Event.class))).thenReturn(event);
        when(eventMapper.toDtoCreateEvent(any(Event.class))).thenReturn(createEventDto);

        /*Act & Assert
        mockMvc.perform(post("/events")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createEventDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value("Sample Event"));*/
    }

    @Test
    public void testGetAllEvents() throws Exception {
        // Arrange
        List<Event> events = new ArrayList<>();
        Event event1 = TestFixtures.grilling;
        Event event2 = TestFixtures.pingPongGame;
        events.add(event1);
        events.add(event2);

        when(eventService.findAllEvents()).thenReturn(events);
        when(eventMapper.toDto(any(Event.class))).thenAnswer(invocation -> {
            Event event = invocation.getArgument(0);
            EventDto eventDto = EventDto.builder().build();
            eventDto.setId(event.getId());
            eventDto.setTitle(event.getTitle());
            return eventDto;
        });

        // Act & Assert
        mockMvc.perform(get("/events")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value(event1.getTitle()))
                .andExpect(jsonPath("$[1].title").value(event2.getTitle()));
    }

    @Test
    public void testGetEventById() throws Exception {
        // Arrange
        String eventId = "1";
        Event event = TestFixtures.grilling;
        event.setId(eventId);
        event.setTitle("Sample Event");

        EventDto eventDto = TestFixtures.testEventDto;
        eventDto.setId(eventId);
        eventDto.setTitle("Sample Event");

        when(eventService.findByEventId(anyString())).thenReturn(event);
        when(eventMapper.toDto(any(Event.class))).thenReturn(eventDto);

        // Act & Assert
        mockMvc.perform(get("/events/" + eventId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"id\":\"1\",\"title\":\"Sample Event\"}"));
    }

    @Test
    public void testGetEventByIdNotFound() throws Exception {
        // Arrange
        when(eventService.findByEventId(eq("1"))).thenThrow(new EntityNotFoundException("Event not found"));

        // Act & Assert
        mockMvc.perform(get("/events/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testUpdateEventDto() throws Exception {
        // Mock data
        String eventId = "1";
        EventDto updatedEventDto = EventDto.builder().build();
        updatedEventDto.setId(eventId);
        updatedEventDto.setTitle("Updated Event");
        // Set other required fields with valid values

        Event updatedEvent = Event.builder().build();
        updatedEvent.setId(eventId);
        updatedEvent.setTitle("Updated Event");
        // Set other fields accordingly

        // Mock the behavior of eventMapper
        when(eventMapper.toEntity(updatedEventDto)).thenReturn(updatedEvent);

        // Mock the behavior of eventService
        when(eventService.updateEvent(eq(eventId), any(Event.class))).thenReturn(updatedEvent);

        // Mock the behavior of eventMapper
        when(eventMapper.toDtoAllArgs(updatedEvent)).thenReturn(updatedEventDto);

        /*// Act & Assert
        MvcResult result = mockMvc.perform(put("/events/update/{eventId}", eventId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedEventDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Updated Event"))
                .andReturn();*/
    }
    @Test
    public void testDeleteEventById() throws Exception {
        // Arrange
        String eventId = "1";

        // Act & Assert
        mockMvc.perform(delete("/events/" + eventId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        Mockito.verify(eventService, Mockito.times(1)).deleteEventById(eq(eventId));
    }
}
