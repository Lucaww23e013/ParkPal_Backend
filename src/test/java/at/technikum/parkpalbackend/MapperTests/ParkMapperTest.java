package at.technikum.parkpalbackend.MapperTests;

import at.technikum.parkpalbackend.dto.ParkDto;
import at.technikum.parkpalbackend.mapper.ParkMapper;
import at.technikum.parkpalbackend.model.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class ParkMapperTest {

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void whenParkValue_thenDtoValue() {
        ParkMapper parkMapper = new ParkMapper();
        String parkId = UUID.randomUUID().toString();
        String parkName = "Park";
        String description = "Enter Description Here";
        Country test = new Country(UUID.randomUUID().toString(),"test", "testcode");
        List<Media> parkMedia = new ArrayList<>();
        List<Event> parkEvents = new ArrayList<>();
        Address parkAddress = new Address("Streetnumber", "1220", "Wien", test);

        Park park = new Park(parkId, parkName, description, parkAddress, parkEvents, parkMedia);

        ParkDto parkDto = parkMapper.toDto(park);

        assertEquals(parkId, parkDto.getParkId());
        assertEquals(parkName, parkDto.getParkName());
        assertEquals(description, parkDto.getDescription());
        assertEquals(parkAddress, parkDto.getParkAddress());
        assertEquals(parkEvents, parkDto.getParkEvents());
        assertEquals(parkMedia, parkDto.getParkMedia());
    }

    @Test
    void whenDtoValue_thenParkValue() {
        ParkMapper parkMapper = new ParkMapper();
        String parkId = UUID.randomUUID().toString();
        String parkName = "Park";
        String description = "Enter Description Here";
        Country test = new Country(UUID.randomUUID().toString(),"test", "testcode");
        List<Media> parkMedia = new ArrayList<>();
        List<Event> parkEvents = new ArrayList<>();
        Address parkAddress = new Address("Streetnumber", "1220", "Wien", test);

        ParkDto parkDto = new ParkDto(parkId, parkName, description, parkAddress, parkEvents, parkMedia);

        Park park = parkMapper.toEntity(parkDto);

        assertEquals(parkId, park.getParkId());
        assertEquals(parkName, park.getParkName());
        assertEquals(description, park.getDescription());
        assertEquals(parkAddress, park.getParkAddress());
        assertEquals(parkEvents, park.getParkEvents());
        assertEquals(parkMedia, park.getParkMedia());

    }
}