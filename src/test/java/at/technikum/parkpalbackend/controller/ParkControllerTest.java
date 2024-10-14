package at.technikum.parkpalbackend.controller;

import at.technikum.parkpalbackend.TestFixtures;
import at.technikum.parkpalbackend.dto.parkdtos.CreateParkDto;
import at.technikum.parkpalbackend.dto.parkdtos.ParkDto;
import at.technikum.parkpalbackend.mapper.ParkMapper;
import at.technikum.parkpalbackend.model.Park;
import at.technikum.parkpalbackend.security.filter.JwtAuthenticationFilter;
import at.technikum.parkpalbackend.security.jwt.JwtDecoder;
import at.technikum.parkpalbackend.service.EventService;
import at.technikum.parkpalbackend.service.ParkService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ParkController.class)
@AutoConfigureMockMvc(addFilters = false)
public class ParkControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ParkService parkService;

    @MockBean
    private EventService eventService;

    @MockBean
    private JwtDecoder jwtDecoder;

    @MockBean
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @MockBean
    private ParkMapper parkMapper;

    @MockBean
    private ParkDto parkDto;

    @MockBean
    private Park park;

    @Test
    public void testGetAllParks() throws Exception {
        // Arrange
        Park park = TestFixtures.parkWithEvents;
        park.setId("1");

        List<Park> parks = Collections.singletonList(park);
        when(parkService.findAllParks()).thenReturn(parks);
        when(parkMapper.toDto(any(Park.class))).thenReturn(ParkDto.builder().build());

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/parks")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetParkByParkId() throws Exception {
        // Arrange
        String parkId = "1";
        ParkDto parkDto = ParkDto.builder().build();
        parkDto.setId(parkId);
        parkDto.setName("Test Park");

        Park park = Park.builder().build();

        when(parkService.findParkById(eq(parkId))).thenReturn(park);
        when(parkMapper.toDto(any(Park.class))).thenReturn(parkDto);

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/parks/{parkId}", parkId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(parkId))
                .andExpect(jsonPath("$.name").value("Test Park"));
    }

//    @Disabled
//    @Test
//    public void testUpdatePark() throws Exception {
//        // Mock data
//        String parkId = "1";
//        ParkDto parkDto = ParkDto.builder().id(parkId).name("Updated Park").build();
//        Park park = Park.builder().id(parkId).name("Updated Park").build();
//
//        // Mock service and mapper behavior
//        when(parkService.updatePark(eq(parkId), any(Park.class))).thenReturn(park);
//        when(parkMapper.toEntity(any(ParkDto.class))).thenReturn(park);
//        when(parkMapper.toDto(any(Park.class))).thenReturn(parkDto);
//
//        // Act & Assert
//        mockMvc.perform(put("/parks/{parkId}", parkId)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(parkDto)))
//                .andExpect(status().isOk())
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                .andExpect(jsonPath("$.id").value(parkId))
//                .andExpect(jsonPath("$.name").value("Updated Park"));
//    }

    @Test
    public void testDeleteParkById() throws Exception {
        // Arrange
        String parkId = "1";

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.delete("/parks/{parkId}", parkId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }
}
