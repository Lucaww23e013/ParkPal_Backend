package at.technikum.parkpalbackend.controller;

import at.technikum.parkpalbackend.TestFixtures;
import at.technikum.parkpalbackend.dto.eventtagdtos.CreateEventTagDto;
import at.technikum.parkpalbackend.dto.eventtagdtos.EventTagDto;
import at.technikum.parkpalbackend.exception.EntityNotFoundException;
import at.technikum.parkpalbackend.mapper.EventTagMapper;
import at.technikum.parkpalbackend.model.EventTag;
import at.technikum.parkpalbackend.security.filter.JwtAuthenticationFilter;
import at.technikum.parkpalbackend.service.EventService;
import at.technikum.parkpalbackend.service.EventTagService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(EventTagController.class)
@AutoConfigureMockMvc(addFilters = false)
public class EventTagControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EventTagService eventTagService;

    @MockBean
    private EventTagMapper eventTagMapper;

    @MockBean
    private EventService eventService;

    @MockBean
    private JwtAuthenticationFilter jwtAuthenticationFilter;


    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testCreateEventTag() throws Exception {
        // Arrange
        CreateEventTagDto createEventTagDto = TestFixtures.testCreateEventTagDto;
        createEventTagDto.setName("Music");
        EventTagDto eventTagDto = EventTagDto.builder()
                .name(createEventTagDto.getName())
                .build();

        EventTag eventTag = TestFixtures.familyEventTag;
        eventTag.setName("Music");

        Mockito.when(eventTagMapper.toEntity(any(CreateEventTagDto.class))).thenReturn(eventTag);
        Mockito.when(eventTagService.save(any(EventTag.class))).thenReturn(eventTag);
        Mockito.when(eventTagMapper.toDto(any(EventTag.class))).thenReturn(eventTagDto);

        // Act & Assert
        mockMvc.perform(post("/event-tags")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(eventTagDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Music"));
    }

    @Test
    public void testCreateEventTagWithInvalidData() throws Exception {
        // Arrange
        EventTagDto eventTagDto = EventTagDto.builder().build();

        // Act & Assert
        mockMvc.perform(post("/event-tags")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(eventTagDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testGetAllTags() throws Exception {
        // Arrange
        Set<EventTag> eventTags = new HashSet<>();
        EventTag eventTag = TestFixtures.familyEventTag;
        eventTag.setName("Music");
        eventTags.add(eventTag);

        EventTagDto eventTagDto = TestFixtures.testEventTagDto;
        eventTagDto.setId(UUID.randomUUID().toString());
        eventTagDto.setName("Music");

        Mockito.when(eventTagService.findAllEventTagSet()).thenReturn(eventTags);
        Mockito.when(eventTagMapper.toDto(any(EventTag.class))).thenReturn(eventTagDto);

        // Act & Assert
        mockMvc.perform(get("/event-tags")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Music"));
    }

    @Test
    public void testGetAllTagsWhenEmpty() throws Exception {
        // Arrange
        Mockito.when(eventTagService.findAllEventTagSet()).thenReturn(Collections.emptySet());

        // Act & Assert
        mockMvc.perform(get("/event-tags")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    public void testGetEventTagById() throws Exception {
        // Arrange
        String eventTagId = "1";
        EventTag eventTag = TestFixtures.familyEventTag;
        eventTag.setId(eventTagId);
        eventTag.setName("Sample Event Tag");

        EventTagDto eventTagDto = TestFixtures.testEventTagDto;
        eventTagDto.setId(eventTagId);
        eventTagDto.setName("Sample Event Tag");

        Mockito.when(eventTagService.findTagById(anyString())).thenReturn(eventTag);
        Mockito.when(eventTagMapper.toDto(any(EventTag.class))).thenReturn(eventTagDto);

        // Act & Assert
        mockMvc.perform(get("/event-tags/" + eventTagId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"id\":\"1\",\"name\":\"Sample Event Tag\"}"));
    }

@Test
    public void testGetEventTagByIdNotFound() throws Exception {
        // Arrange
        Mockito.when(eventTagService.findTagById(eq("1"))).thenThrow(new EntityNotFoundException("Tag not found"));

        // Act & Assert
        mockMvc.perform(get("/event-tags/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
