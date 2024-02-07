package at.technikum.parkpalbackend.MapperTests;

import at.technikum.parkpalbackend.dto.CountryDto;
import at.technikum.parkpalbackend.mapper.CountryMapper;
import at.technikum.parkpalbackend.model.Country;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class CountryMapperTest {

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void whenDtoValue_thenCountryValue() {
        CountryMapper countryMapper = new CountryMapper();
        String countryId = UUID.randomUUID().toString();
        String name = "Test";
        String iso2Code = "007";

        Country country = new Country(countryId, name, iso2Code);

        CountryDto countryDto = countryMapper.toDto(country);

        assertEquals(countryId, countryDto.getCountryId());
        assertEquals(name, countryDto.getName());
        assertEquals(iso2Code, countryDto.getIso2Code());
    }

    @Test
    void whenCountryValue_thenDtoValue() {
        CountryMapper countryMapper = new CountryMapper();
        String countryId = UUID.randomUUID().toString();
        String name = "Test";
        String iso2Code = "007";

        CountryDto countryDto = new CountryDto(countryId, name, iso2Code);

        Country country = countryMapper.toEntity(countryDto);

        assertEquals(countryId, country.getCountryId());
        assertEquals(name, country.getName());
        assertEquals(iso2Code, country.getIso2Code());
    }
}