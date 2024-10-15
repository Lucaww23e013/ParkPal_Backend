package at.technikum.parkpalbackend.service;

import at.technikum.parkpalbackend.TestFixtures;
import at.technikum.parkpalbackend.exception.EntityNotFoundException;
import at.technikum.parkpalbackend.model.Event;
import at.technikum.parkpalbackend.model.File;
import at.technikum.parkpalbackend.model.Park;
import at.technikum.parkpalbackend.persistence.FileRepository;
import at.technikum.parkpalbackend.persistence.ParkRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
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

    @Mock
    private FileRepository fileRepository;

    @Mock
    private EventService eventService;

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

    @Test
    void updateParkById_whenParkExists_thenUpdatePark() {
        // Arrange
        // prepare data
        String parkId = UUID.randomUUID().toString();
        Park oldPark = parkAwesome;
        Park updatedPark = parkAwesome;
        updatedPark.setId(parkId);
        updatedPark.setName(parkLuca.getName());
        updatedPark.setAddress(parkWithEvents.getAddress());
        updatedPark.setDescription(parkLuca.getDescription());
        //updatedPark.setParkFiles(fileList());

        when(parkRepository.findById(parkId)).thenReturn(Optional.of(oldPark));
        when(parkRepository.save(any(Park.class))).thenReturn(updatedPark);
        // Act
        Park newPark = parkService.updatePark(parkId, updatedPark);
        // Assert
        assertNotNull(newPark);
        assertEquals(updatedPark, newPark);
        verify(parkRepository).save(updatedPark);
    }

    @Test
    void updateParkById_whenParkDoesNotExist_thenThrowEntityNotFoundException() {
        // Arrange
        // prepare data
        String parkId = UUID.randomUUID().toString();
        Park updatedPark = parkWithEvents;

        when(parkRepository.findById(parkId)).thenReturn(Optional.empty());
        // Act + Assert
        assertThrows(EntityNotFoundException.class, () -> parkService
                .updatePark(parkId, updatedPark));
        verify(parkRepository, times(0)).save(updatedPark);
    }

    @Test
    void updateParkById_whenUnknownError_thenThrowRuntimeException() {
        // Arrange
        // prepare data
        String parkId = UUID.randomUUID().toString();
        Park oldPark = parkAwesome;
        Park updatedPark = parkAwesome;
        updatedPark.setId(parkId);
        updatedPark.setName(parkLuca.getName());
        updatedPark.setAddress(parkWithEvents.getAddress());
        updatedPark.setDescription(parkLuca.getDescription());
        //updatedPark.setParkFiles(fileList());

        when(parkRepository.findById(parkId)).thenReturn(Optional.of(oldPark));
        when(parkRepository.save(any(Park.class))).thenThrow(new RuntimeException());
        // Act + Assert
        assertThrows(RuntimeException.class, () -> parkService.updatePark(parkId, updatedPark));
        verify(parkRepository, times(1)).save(updatedPark);
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
    void updateParkById_whenParkFilesExist_thenUpdateFiles() {
        // Arrange
        String parkId = UUID.randomUUID().toString();
        Park oldPark = TestFixtures.parkWithFiles();  // Park with some existing files
        Park updatedPark = TestFixtures.parkWithUpdatedFiles();  // Park with updated files

        when(parkRepository.findById(parkId)).thenReturn(Optional.of(oldPark));
        when(parkRepository.save(any(Park.class))).thenReturn(updatedPark);

        // Act
        Park result = parkService.updatePark(parkId, updatedPark);

        // Assert
        assertNotNull(result);
        assertEquals(updatedPark.getMedia(), result.getMedia());  // Files should be updated

        // Capture the argument passed to parkRepository.save()
        ArgumentCaptor<Park> parkCaptor = ArgumentCaptor.forClass(Park.class);
        verify(parkRepository).save(parkCaptor.capture());

        // Verify that the captured Park object has the updated media files
        Park capturedPark = parkCaptor.getValue();
        assertEquals(updatedPark.getMedia(), capturedPark.getMedia());  // Ensure media files are updated
        assertEquals(updatedPark.getName(), capturedPark.getName());    // You can add more fields to verify
    }

    @Test
    void updatePark_whenEventsExist_thenUpdateEvents() {
        // Arrange
        String parkId = UUID.randomUUID().toString();
        Park existingPark = TestFixtures.parkWithEvents;
        Park updatedPark = TestFixtures.parkWithEvents;

        when(parkRepository.findById(parkId)).thenReturn(Optional.of(existingPark));
        when(parkRepository.save(any(Park.class))).thenReturn(updatedPark);

        when(eventService.save(any(Event.class))).thenReturn(null); // or return a specific mock Event if needed

        // Act
        Park result = parkService.updatePark(parkId, updatedPark);

        // Assert
        assertNotNull(result);
        assertEquals(updatedPark.getEvents(), result.getEvents());
        verify(eventService, times(4)).save(any(Event.class));
        verify(parkRepository).save(existingPark);
    }

    @Test
    void updatePark_whenFilesExist_thenUpdateFiles() {
        // Arrange
        String parkId = UUID.randomUUID().toString();
        Park existingPark = TestFixtures.parkWithFiles();
        Park updatedPark = TestFixtures.parkWithUpdatedFiles();
        when(parkRepository.findById(parkId)).thenReturn(Optional.of(existingPark));
        when(parkRepository.save(any(Park.class))).thenReturn(updatedPark);

        when(fileRepository.save(any(File.class))).thenReturn(null);

        // Act
        Park result = parkService.updatePark(parkId, updatedPark);

        // Assert
        assertNotNull(result);
        assertEquals(updatedPark.getMedia(), result.getMedia());
        verify(fileRepository, times(4)).save(any(File.class));
        verify(parkRepository).save(existingPark);
    }
}