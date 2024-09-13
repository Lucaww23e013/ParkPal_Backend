package at.technikum.parkpalbackend.mapper;

import at.technikum.parkpalbackend.TestFixtures;
import at.technikum.parkpalbackend.dto.parkdtos.CreateParkDto;
import at.technikum.parkpalbackend.dto.parkdtos.ParkDto;
import at.technikum.parkpalbackend.model.Park;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.Assert.*;

//@SpringBootTest
//@ExtendWith(MockitoExtension.class)
//public class ParkMapperTest {
//
//    @InjectMocks
//    private ParkMapper parkMapper;
//
//    @Test
//    public void whenEntity_thenToDto() {
//        // Arrange
//        Park park = TestFixtures.parkWithEvents;
//        park.setId(UUID.randomUUID().toString());
//
//        // Act
//        ParkDto parkDto = parkMapper.toDto(park);
//
//        // Assert
//        Assertions.assertEquals(park.getId(), parkDto.getId());
//        Assertions.assertEquals(park.getName(), parkDto.getName());
//        Assertions.assertEquals(park.getDescription(), parkDto.getDescription());
//       //Assertions.assertEquals(park.getParkEvents(), parkDto.getParkEvents());
//        //Assertions.assertEquals(park.getParkFiles(), parkDto.getParkFiles());
//    }
//
//
//    @Test
//    public void whenEntityNull_toDto_thenThrowIllegalArgumentException() {
//        // Arrange
//        Park park = null;
//
//        // Act & Assert
//        assertThrows(IllegalArgumentException.class, () -> parkMapper.toDto(park));
//    }
//
//    @Test
//    public void whenDTO_thenToEntity() {
//        // Arrange
//        ParkDto parkDto = TestFixtures.testParkDto;
//        parkDto.setId(UUID.randomUUID().toString());
//
//        // Act
//        Park park = parkMapper.toEntity(parkDto);
//
//        // Assert
//        Assertions.assertEquals(parkDto.getId(), park.getId());
//        Assertions.assertEquals(parkDto.getName(), park.getName());
//        Assertions.assertEquals(parkDto.getDescription(), park.getDescription());
//        //Assertions.assertEquals(parkDto.getParkEvents(), park.getParkEvents());
//        //Assertions.assertEquals(parkDto.getParkFiles(), park.getParkFiles());
//    }
//    @Test
//    public void whenDTONull_toEntity_thenThrowIllegalArgumentException() {
//        // Arrange
//        ParkDto parkDto = null;
//
//        // Act & Assert
//        assertThrows(IllegalArgumentException.class, () -> parkMapper.toEntity(parkDto));
//    }
//
//    @Test
//    public void whenParkEntity_thenCreateParkDto() {
//        // Arrange
//        Park park = TestFixtures.parkWithEvents;
//        park.setId(UUID.randomUUID().toString());
//
//        // Act
//        CreateParkDto createParkDto = parkMapper.toCreateParkDto(park);
//
//        // Assert
//        Assertions.assertEquals(park.getId(), createParkDto.getParkId());
//        Assertions.assertEquals(park.getName(), createParkDto.getName());
//        Assertions.assertEquals(park.getDescription(), createParkDto.getDescription());
//        Assertions.assertEquals(park.getAddress(), createParkDto.getAddress());
//
//    }
//
//
//    @Test
//    public void whenEntityNull_toCreateParkDto_thenThrowIllegalArgumentException() {
//        // Arrange
//        Park park = null;
//
//        // Act & Assert
//        assertThrows(IllegalArgumentException.class, () -> parkMapper.toCreateParkDto(park));
//    }
//
//    @Test
//    public void whenCreateParkDTO_thenToCreateParkEntity() {
//        // Arrange
//        CreateParkDto createParkDto = TestFixtures.testCreateParkDto;
//        createParkDto.setParkId(UUID.randomUUID().toString());
//
//        // Act
//        Park park = parkMapper.createParkDtoToEntity(createParkDto);
//
//
//        // Assert
//        Assertions.assertEquals(createParkDto.getParkId(), park.getId());
//        Assertions.assertEquals(createParkDto.getName(), park.getName());
//        Assertions.assertEquals(createParkDto.getDescription(), park.getDescription());
//        Assertions.assertEquals(createParkDto.getAddress(), park.getAddress());
//    }
//    @Test
//    public void whenCreateParkDTONull_toEntity_thenThrowIllegalArgumentException() {
//        // Arrange
//        CreateParkDto createParkDto = null;
//
//        // Act & Assert
//        assertThrows(IllegalArgumentException.class, () -> parkMapper.createParkDtoToEntity(createParkDto));
//    }
//}
