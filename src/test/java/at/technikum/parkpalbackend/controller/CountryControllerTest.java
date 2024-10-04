package at.technikum.parkpalbackend.controller;

import at.technikum.parkpalbackend.TestFixtures;
import at.technikum.parkpalbackend.dto.CountryDto;
import at.technikum.parkpalbackend.exception.EntityNotFoundException;
import at.technikum.parkpalbackend.mapper.CountryMapper;
import at.technikum.parkpalbackend.model.Country;
import at.technikum.parkpalbackend.security.filter.JwtAuthenticationFilter;
import at.technikum.parkpalbackend.service.CountryService;
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
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CountryController.class)
@AutoConfigureMockMvc(addFilters = false)
public class CountryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CountryService countryService;

    @MockBean
    private CountryMapper countryMapper;

    @MockBean
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testAddCountry() throws Exception {
        // Arrange
        CountryDto countryDto = TestFixtures.austriaDTO;
        countryDto.setId(UUID.randomUUID().toString());
        countryDto.setName("Sample Country");

        Country country = TestFixtures.austria;
        country.setName("Sample Country");

        Mockito.when(countryMapper.toEntity(any(CountryDto.class))).thenReturn(country);
        Mockito.when(countryService.save(any(Country.class))).thenReturn(country);
        Mockito.when(countryMapper.toDto(any(Country.class))).thenReturn(countryDto);

        // Act & Assert
        mockMvc.perform(post("/countries")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(countryDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Sample Country"));
    }

    @Test
    public void testGetAllCountries() throws Exception {
        // Arrange
        List<Country> countries = Collections.singletonList(TestFixtures.austria);
        CountryDto countryDto = TestFixtures.austriaDTO;
        countryDto.setId(UUID.randomUUID().toString());
        countryDto.setName("Sample Country");

        Mockito.when(countryService.findAllCountries()).thenReturn(countries);
        Mockito.when(countryMapper.toDto(any(Country.class))).thenReturn(countryDto);

        // Act & Assert
        mockMvc.perform(get("/countries")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Sample Country"));
    }

    @Test
    public void testGetCountryById() throws Exception {
        // Arrange
        String countryId = "1";
        Country country = TestFixtures.austria;
        country.setId(countryId);
        country.setName("Sample Country");

        CountryDto countryDto = TestFixtures.austriaDTO;
        countryDto.setId(countryId);
        countryDto.setName("Sample Country");

        Mockito.when(countryService.findCountryByCountryId(anyString())).thenReturn(country);
        Mockito.when(countryMapper.toDto(any(Country.class))).thenReturn(countryDto);

        // Act & Assert
        mockMvc.perform(get("/countries/" + countryId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"id\":\"1\",\"name\":\"Sample Country\"}"));
    }

    @Test
    public void testGetCountryByIdNotFound() throws Exception {
        // Arrange
        Mockito.when(countryService.findCountryByCountryId(eq("1"))).thenThrow(new EntityNotFoundException("Country not found"));

        // Act & Assert
        mockMvc.perform(get("/countries/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testUpdateCountry() throws Exception {
        // Arrange
        String countryId = "1";
        CountryDto updatedCountryDto = TestFixtures.austriaDTO;
        updatedCountryDto.setId(countryId);
        updatedCountryDto.setName("Updated Country");

        Country updatedCountry = TestFixtures.austria;
        updatedCountry.setId(countryId);
        updatedCountry.setName("Updated Country");

        Mockito.when(countryMapper.toEntity(any(CountryDto.class))).thenReturn(updatedCountry);
        Mockito.when(countryService.updateCountry(eq(countryId), any(Country.class))).thenReturn(updatedCountry);
        Mockito.when(countryMapper.toDto(any(Country.class))).thenReturn(updatedCountryDto);

        // Act & Assert
        mockMvc.perform(put("/countries/" + countryId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedCountryDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Updated Country"));
    }

    //@Test
    public void testDeleteCountryById() throws Exception {
        // Arrange
        String countryId = "1";

        // Act & Assert
        mockMvc.perform(delete("/countries/" + countryId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        Mockito.verify(countryService, Mockito.times(1)).deleteCountryByCountryId(eq(countryId));
    }
}
