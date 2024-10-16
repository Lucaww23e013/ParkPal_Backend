package at.technikum.parkpalbackend.service;

import at.technikum.parkpalbackend.TestFixtures;
import at.technikum.parkpalbackend.dto.parkdtos.ParkDto;
import at.technikum.parkpalbackend.exception.EntityNotFoundException;
import at.technikum.parkpalbackend.model.Event;
import at.technikum.parkpalbackend.model.File;
import at.technikum.parkpalbackend.model.Park;
import at.technikum.parkpalbackend.persistence.FileRepository;
import at.technikum.parkpalbackend.persistence.ParkRepository;
import at.technikum.parkpalbackend.util.ParkUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static at.technikum.parkpalbackend.TestFixtures.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
class ParkServiceTest {


    @Mock
    private ParkRepository parkRepository;

    @Mock
    private FileRepository fileRepository;

    @Mock
    private FileService fileService;

    @Mock
    private EventService eventService;

    @InjectMocks
    private ParkService parkService;

    @InjectMocks
    private ParkUtil parkUtil;

    @Test
    void savePark_WhenValidParkProvided_ReturnsSavedPark() {
        // Arrange
        Park park = parkWithEvents;
        String parkId = UUID.randomUUID().toString();
        park.setId(parkId);

        when(parkRepository.save(park)).thenReturn(park);

        // Act
        Park result = parkService.save(park);

        // Assert
        assertNotNull(result, "The saved park should not be null");
        assertEquals(park, result, "The result should be the same as the park passed to the save method");

        // Verify that the save method was called exactly once
        verify(parkRepository, times(1)).save(park);
    }

    @Test
    void savePark_WhenParkIsNull_ShouldThrowIllegalArgumentException() {
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> parkService.save(null),
                "Expected save() to throw IllegalArgumentException when park is null");
    }

    @Test
    void findAllParks_whenParksExist_thenReturnParks() {
        // Arrange
        Park park1 = parkWithEvents;
        Park park2 = alternateParkWithEvents;
        List<Park> expectedParks = new ArrayList<>();
        expectedParks.add(park1);
        expectedParks.add(park2);

        when(parkRepository.findAll()).thenReturn(expectedParks);
        // Act
        List<Park> foundParks = parkService.findAllParks();
        // Assert
        assertNotNull(foundParks);
        assertEquals(expectedParks, foundParks);
        verify(parkRepository).findAll();
    }

    @Test
    void findAllParks_whenNoParkExist_thenReturnEmptyList() {
        // Arrange
        when(parkRepository.findAll()).thenReturn(new ArrayList<>());
        // Act
        List<Park> foundParks = parkService.findAllParks();
        // Assert
        assertEquals(0, foundParks.size());
        verify(parkRepository).findAll();
    }

    @Test
    void findByParkId_whenParkDoesNotExist_thenThrowEntityNotFoundException() {
        // Arrange
        String parkId = UUID.randomUUID().toString();
        when(parkRepository.findById(parkId)).thenReturn(Optional.empty());
        // Act + Assert
        assertThrows(EntityNotFoundException.class, () -> parkService.findParkById(parkId));
        verify(parkRepository).findById(parkId);
    }

    @Test
    void findByParkId_whenParkExists_thenReturnPark() {
        // Arrange
        String parkId = UUID.randomUUID().toString();
        Park expectedPark = parkWithEvents;
        when(parkRepository.findById(parkId)).thenReturn(Optional.of(expectedPark));
        // Act
        Park foundPark = parkService.findParkById(parkId);
        // Assert
        assertNotNull(foundPark);
        assertEquals(expectedPark, foundPark);
        verify(parkRepository).findById(parkId);
    }

    @Test
    void deleteParkById_whenParkExists_thenDeletePark() {
        // Arrange
        String parkId = UUID.randomUUID().toString();
        Park parkToDelete = parkAwesome;
        when(parkRepository.findById(parkId)).thenReturn(Optional.of(parkToDelete));
        // Act
        Park deletedPark = parkService.deleteParkByParkId(parkId);
        // Assert
        assertNotNull(deletedPark);
        assertEquals(parkToDelete, deletedPark);
        verify(parkRepository, times(1)).delete(parkToDelete);
    }

    @Test
    void deleteParkById_whenParkDoesNotExist_thenThrowEntityNotFoundException() {
        // Arrange
        String parkId = UUID.randomUUID().toString();
        Park parkToDelete = parkAwesome;
        when(parkRepository.findById(parkId)).thenReturn(Optional.empty());
        // Act + Assert
        assertThrows(EntityNotFoundException.class, () -> parkService.deleteParkByParkId(parkId));
        verify(parkRepository, times(0)).delete(parkToDelete);
    }

    @Test
    void deleteParkById_whenUnknownError_thenThrowRuntimeException() {
        // Arrange
        String parkId = UUID.randomUUID().toString();
        Park parkToDelete = parkAwesome;
        parkToDelete.setId(parkId);
        when(parkRepository.findById(parkId)).thenReturn(Optional.of(parkToDelete));
        when(parkService.deleteParkByParkId(parkId)).thenThrow(new RuntimeException());
        // Act + Assert
        assertThrows(RuntimeException.class, () -> parkService.deleteParkByParkId(parkId));
        verify(parkRepository, times(1)).delete(parkToDelete);
    }

    @Test
    void updatePark_SuccessfullyUpdatedDetails() {
        // Arrange
        String parkId = parkWithEventsAndMedia.getId();  // Get the ID of the test park
        ParkDto parkDto = ParkDto.builder()
                .name("Updated Park Name")
                .description("Updated Park Description")
                .address("Updated Address")
                .eventIds(Arrays.asList("eventId1", "eventId2"))  // Example event IDs
                .filesExternalIds(Arrays.asList("fileId1", "fileId2"))  // Example file IDs
                .build();

        when(parkRepository.findById(parkId)).thenReturn(Optional.of(parkWithEventsAndMedia));
        when(parkRepository.save(any(Park.class))).thenReturn(parkWithEventsAndMedia);

        // Act
        Park updatedPark = parkUtil.updatePark(parkId, parkDto);

        // Assert
        assertEquals("Updated Park Name", updatedPark.getName());
        assertEquals("Updated Park Description", updatedPark.getDescription());
        assertEquals("Updated Address", updatedPark.getAddress());
        verify(parkRepository).save(parkWithEventsAndMedia);
    }

}