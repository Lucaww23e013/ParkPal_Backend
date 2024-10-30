package at.technikum.parkpalbackend.componentTests.controller;

import at.technikum.parkpalbackend.TestFixtures;
import at.technikum.parkpalbackend.dto.eventdtos.CreateEventDto;
import at.technikum.parkpalbackend.dto.eventdtos.EventDto;
import at.technikum.parkpalbackend.model.Event;
import at.technikum.parkpalbackend.model.Park;
import at.technikum.parkpalbackend.model.User;
import at.technikum.parkpalbackend.persistence.CountryRepository;
import at.technikum.parkpalbackend.persistence.EventRepository;
import at.technikum.parkpalbackend.persistence.ParkRepository;
import at.technikum.parkpalbackend.persistence.UserRepository;
import at.technikum.parkpalbackend.security.filter.JwtAuthenticationFilter;
import at.technikum.parkpalbackend.security.principal.UserPrincipal;
import at.technikum.parkpalbackend.security.principal.UserPrincipalAuthenticationToken;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc(addFilters = false)
public class EventControllerComponentTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private ParkRepository parkRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @MockBean
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private CountryRepository countryRepository;

    @BeforeEach
    void setUp() {
        eventRepository.deleteAll();
        parkRepository.deleteAll();
        userRepository.deleteAll();
        countryRepository.deleteAll();
    }

    @Test
    void testCreateEvent_ShouldReturnCreatedEvent() throws Exception {
        Park park = parkRepository.save(TestFixtures.parkAwesome);
        User user = userRepository.save(TestFixtures.adminUser);
        CreateEventDto createEventDto = CreateEventDto.builder()
                .title("New Event")
                .description("This is a new event")
                .startTS(LocalDateTime.now().plusHours(1)) // Ensure start time is in the future
                .endTS(LocalDateTime.now().plusHours(2)) // Ensure end time is after start time
                .parkId(park.getId()) // Ensure parkId is not blank
                .creatorUserId(user.getId()) // Ensure creatorUserId is valid
                .build();

        mockMvc.perform(post("/events")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createEventDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value(createEventDto.getTitle()))
                .andExpect(jsonPath("$.description").value(createEventDto.getDescription()));
    }

    @Test
    void testGetAllEvents_ShouldReturnAllEvents() throws Exception {
        Event event1 = Event.builder()
                .title("Event 1")
                .description("Description 1")
                .build();
        eventRepository.save(event1);

        Event event2 = Event.builder()
                .title("Event 2")
                .description("Description 2")
                .build();
        eventRepository.save(event2);

        mockMvc.perform(get("/events"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].title").value("Event 1"))
                .andExpect(jsonPath("$[1].title").value("Event 2"));
    }

    @Test
    void testGetEventByID_ShouldReturnEvent() throws Exception {
        Event event = Event.builder()
                .title("Event")
                .description("Description")
                .build();
        Event savedEvent = eventRepository.save(event);

        mockMvc.perform(get("/events/" + savedEvent.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Event"))
                .andExpect(jsonPath("$.description").value("Description"));
    }

    @Test
    void testUpdateEvent_ShouldReturnUpdatedEvent() throws Exception {
        // Arrange: Create and save a park and a user
        Park park = parkRepository.save(TestFixtures.parkAwesome);
        User user = userRepository.save(TestFixtures.adminUser);

        // Arrange: Create and save an event
        Event event = Event.builder()
                .title("Old Event")
                .description("Old Description")
                .startTS(LocalDateTime.now().plusHours(1).withNano(0))
                .endTS(LocalDateTime.now().plusHours(2).withNano(0))
                .park(park)
                .creator(user)
                .build();
        Event savedEvent = eventRepository.save(event);

        // Arrange: Create an EventDto with updated details
        EventDto eventDto = EventDto.builder()
                .title("Updated Event")
                .description("Updated Description")
                .startTS(LocalDateTime.now().plusHours(3).withNano(0))
                .endTS(LocalDateTime.now().plusHours(4).withNano(0))
                .parkId(park.getId())
                .creatorUserId(user.getId())
                .build();

        // Act & Assert: Perform the update and verify the response
        mockMvc.perform(put("/events/" + savedEvent.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(eventDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Updated Event"))
                .andExpect(jsonPath("$.description").value("Updated Description"))
                .andExpect(jsonPath("$.startTS").value(eventDto.getStartTS().toString()))
                .andExpect(jsonPath("$.endTS").value(eventDto.getEndTS().toString()));
    }

    @Test
    void testDeleteEvent_ShouldReturnNoContent() throws Exception {
        Event event = Event.builder()
                .title("Event to be deleted")
                .description("Description")
                .build();
        Event savedEvent = eventRepository.save(event);

        mockMvc.perform(delete("/events/" + savedEvent.getId()))
                .andExpect(status().isNoContent());
    }

    @Test
    void testManageEventParticipation_ShouldAddUserToEvent() throws Exception {
        // Arrange: Create and save an event and a user
        Event event = eventRepository.save(TestFixtures.eventAwesome);
        User user = userRepository.save(TestFixtures.adminUser);

        // Create UserPrincipal
        UserPrincipal userPrincipal = new UserPrincipal(
                user.getId(),
                user.getUserName(),
                user.getPassword(),
                user.isLocked(),
                List.of() // Assuming no authorities for simplicity
        );

        // Create UserPrincipalAuthenticationToken
        UserPrincipalAuthenticationToken authenticationToken = new UserPrincipalAuthenticationToken(userPrincipal);

        // Set the Authentication object in the SecurityContext
        SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
        securityContext.setAuthentication(authenticationToken);
        SecurityContextHolder.setContext(securityContext);

        // Act & Assert: Perform the participation request and verify the response
        mockMvc.perform(post("/events/" + event.getId() + "/participation")
                        .param("isJoining", "true")
                        .principal(authenticationToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0]").value(user.getUserName()));
    }

    @Test
    void testGetAllEventsByUserId_ShouldReturnEvents() throws Exception {
        // Arrange: Create and save a user and events
        User user = userRepository.save(TestFixtures.adminUser);
        Event event1 = eventRepository.save(Event.builder()
                .title("Event 1")
                .description("Description 1")
                .creator(user)
                .build());
        Event event2 = eventRepository.save(Event.builder()
                .title("Event 2")
                .description("Description 2")
                .creator(user)
                .build());

        // Act & Assert: Perform the request and verify the response
        mockMvc.perform(get("/events").param("userId", user.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].title").value("Event 1"))
                .andExpect(jsonPath("$[1].title").value("Event 2"));
    }

    @Test
    void testGetAllEventsByParkId_ShouldReturnEvents() throws Exception {
        // Arrange: Create and save a park and events
        Park park = parkRepository.save(TestFixtures.parkAwesome);
        Event event1 = eventRepository.save(Event.builder()
                .title("Event 1")
                .description("Description 1")
                .park(park)
                .build());
        Event event2 = eventRepository.save(Event.builder()
                .title("Event 2")
                .description("Description 2")
                .park(park)
                .build());

        // Act & Assert: Perform the request and verify the response
        mockMvc.perform(get("/events").param("parkId", park.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].title").value("Event 1"))
                .andExpect(jsonPath("$[1].title").value("Event 2"));
    }

    @Test
    void testGetAllEventsJoinedByUser_ShouldReturnEvents() throws Exception {
        // Arrange: Create and save a user and events
        User user = userRepository.save(TestFixtures.adminUser);
        Event event1 = eventRepository.save(Event.builder()
                .title("Event 1")
                .description("Description 1")
                .joinedUsers(List.of(user))
                .build());
        Event event2 = eventRepository.save(Event.builder()
                .title("Event 2")
                .description("Description 2")
                .joinedUsers(List.of(user))
                .build());

        // Act & Assert: Perform the request and verify the response
        mockMvc.perform(get("/events").param("joinedUserId", user.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].title").value("Event 1"))
                .andExpect(jsonPath("$[1].title").value("Event 2"));
    }
}