package at.technikum.parkpalbackend.service;

import at.technikum.parkpalbackend.exception.EntityNotFoundException;
import at.technikum.parkpalbackend.model.Park;
import at.technikum.parkpalbackend.persistence.ParkRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static at.technikum.parkpalbackend.TestFixtures.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
class ParkServiceTest {


    @Mock
    private ParkRepository parkRepository;

    @InjectMocks
    private ParkService parkService;

    @Test
    void savePark_SuccessfullySaved_thenReturnSavedPark() {
        // Arrange
        Park park = parkWithEvents;
        park.setId(UUID.randomUUID().toString());
        when(parkRepository.save(park)).thenReturn(park);
        // Act
        Park result = parkService.save(park);
        // Assert
        assertNotNull(result);
        assertEquals(park, result);
        verify(parkRepository).save(park);
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

}