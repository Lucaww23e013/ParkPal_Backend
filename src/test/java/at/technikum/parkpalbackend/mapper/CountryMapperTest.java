package at.technikum.parkpalbackend.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import at.technikum.parkpalbackend.TestFixtures;
import at.technikum.parkpalbackend.dto.CountryDto;
import at.technikum.parkpalbackend.model.Country;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;


import java.util.UUID;

@ExtendWith(MockitoExtension.class)
public class CountryMapperTest {

    @InjectMocks
    private CountryMapper countryMapper;

    @Test
    public void whenEntity_thenToDto() {
        // Arrange
        Country country = TestFixtures.austria;
        country.setId(UUID.randomUUID().toString());

        // Act
        CountryDto countryDto = countryMapper.toDto(country);

        // Assert
        assertEquals(country.getId(), countryDto.getId());
        assertEquals(country.getName(), countryDto.getName());
        assertEquals(country.getIso2Code(), countryDto.getIso2Code());
    }

    @Test
    public void whenEntityNull_toDto_thenThrowIllegalArgumentException() {
        // Arrange
        Country country = null;

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> countryMapper.toDto(country));
    }

    @Test
    public void whenDTO_thenToEntity() {
        // Arrange
        CountryDto countryDto = TestFixtures.austriaDTO;
        countryDto.setId(UUID.randomUUID().toString());

        // When
        Country country = countryMapper.toEntity(countryDto);

        // Then
        assertEquals(countryDto.getId(), country.getId());
        assertEquals(countryDto.getName(), country.getName());
        assertEquals(countryDto.getIso2Code(), country.getIso2Code());
    }
    @Test
    public void whenDTONull_toEntity_thenThrowIllegalArgumentException() {
        // Arrange
        CountryDto countryDto = null;

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> countryMapper.toEntity(countryDto));
    }
}
