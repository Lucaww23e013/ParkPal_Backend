/*
package at.technikum.parkpalbackend.componentTests.service;

import at.technikum.parkpalbackend.exception.EntityNotFoundException;
import at.technikum.parkpalbackend.model.Park;
import at.technikum.parkpalbackend.persistence.ParkRepository;
import at.technikum.parkpalbackend.service.MinioService;
import at.technikum.parkpalbackend.service.ParkService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

//@DataJpaTest
@SpringBootTest
@Transactional
@ActiveProfiles("test")
//@Import(ParkService.class)
class ParkServiceComponentTest {

    @Autowired
    private ParkService parkService;

    @MockBean
    private MinioService minioService;

    @Autowired
    private ParkRepository parkRepository;

    @BeforeEach
    void setUp() {
        parkRepository.deleteAll(); // Start with a clean slate for each test
    }

    @Test
    void testFindAllParks_EmptyDatabase_ShouldReturnEmptyList() {
        // Arrange: Database is empty

        // Act
        List<Park> parks = parkService.findAllParks();

        // Assert
        assertTrue(parks.isEmpty(), "Expected no parks in an empty database");
    }

    @Test
    void testFindAllParks_SinglePark_ShouldReturnOnePark() {
        // Arrange: Add one park to the database
        Park park = Park.builder()
                .name("Test Park")
                .description("A test park for component testing.")
                .build();
        parkRepository.save(park);

        // Act
        List<Park> parks = parkService.findAllParks();

        // Assert
        assertEquals(1, parks.size(), "Expected exactly one park in the database");
        assertEquals("Test Park", parks.get(0).getName());
    }

    @Test
    void testFindAllParks_MultipleParks_ShouldReturnAllParks() {
        // Arrange: Add multiple parks to the database
        Park park1 = Park.builder()
                .name("Park One")
                .description("First test park.")
                .build();
        parkRepository.save(park1);

        Park park2 = Park.builder()
                .name("Park Two")
                .description("Second test park.")
                .build();
        parkRepository.save(park2);

        // Act
        List<Park> parks = parkService.findAllParks();

        // Assert
        assertEquals(2, parks.size(), "Expected two parks in the database");
        assertTrue(parks.stream().anyMatch(p -> p.getName().equals("Park One")), "Park One should be present");
        assertTrue(parks.stream().anyMatch(p -> p.getName().equals("Park Two")), "Park Two should be present");
    }

    @Test
    void testFindAllParks_LargeNumberOfParks_ShouldReturnAllParks() {
        // Arrange: Add a large number of parks to the database
        int parkCount = 100;
        for (int i = 0; i < parkCount; i++) {
            Park park = Park.builder()
                    .name("Park " + i)
                    .description("Description for park " + i)
                    .build();
            parkRepository.save(park);
        }

        // Act
        List<Park> parks = parkService.findAllParks();

        // Assert
        assertEquals(parkCount, parks.size(), "Expected 100 parks in the database");
    }

    @Test
    void testFindParkById_ExistingId_ShouldReturnPark() {
        // Arrange: Add a park with a known ID to the database
        Park savedPark = parkRepository.save(Park.builder()
                .name("Sample Park")
                .description("A park to test retrieval by ID.")
                .build());

        // Act: Retrieve the park by its ID
        Park retrievedPark = parkService.findParkById(savedPark.getId());

        // Assert: Verify the retrieved park matches the expected data
        assertEquals(savedPark.getId(), retrievedPark.getId(), "Expected park ID to match the saved park's ID");
        assertEquals(savedPark.getName(), retrievedPark.getName(), "Expected park name to match the saved park's name");
    }

    @Test
    void testFindParkById_NonExistingId_ShouldThrowEntityNotFoundException() {
        // Arrange: Set up a non-existing park ID
        String nonExistingId = "non-existing-id";

        // Act & Assert: Attempt to retrieve a park with the non-existing ID, expecting an exception
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> parkService.findParkById(nonExistingId),
                "Expected EntityNotFoundException for a non-existing park ID");

        assertTrue(exception.getMessage().contains("Park with id " + nonExistingId + " not found"));
    }

    @Test
    void testFindParkById_NullId_ShouldThrowIllegalArgumentException() {
        // Arrange: Define a null ID for retrieval
        String nullId = null;

        // Act & Assert: Attempt to retrieve a park with a null ID, expecting an exception
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> parkService.findParkById(nullId),
                "Expected IllegalArgumentException for a null park ID");

        assertTrue(exception.getMessage().contains("The park ID cannot be null"));
    }

    @Test
    void testFindParkById_EmptyId_ShouldThrowEntityNotFoundException() {
        // Arrange: Define an empty ID for retrieval
        String emptyId = "";

        // Act & Assert: Attempt to retrieve a park with an empty ID, expecting an exception
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> parkService.findParkById(emptyId),
                "Expected EntityNotFoundException for an empty park ID");

        assertTrue(exception.getMessage().contains("Park with id  not found"));
    }

    @Test
    void testSave_NullPark_ShouldThrowIllegalArgumentException() {
        // Arrange
        Park nullPark = null;

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> parkService.save(nullPark),
                "Expected IllegalArgumentException for a null park");
        assertEquals("The park cannot be null.", exception.getMessage());
    }

    @Test
    void testSave_ValidPark_ShouldSaveAndReturnPark() {
        // Arrange
        Park park = Park.builder()
                .name("Test Park")
                .description("Description of Test Park")
                .build();

        // Act
        Park savedPark = parkService.save(park);

        // Assert
        assertNotNull(savedPark.getId(), "Saved park should have an ID assigned");
        assertEquals("Test Park", savedPark.getName(), "Saved park name should match");
    }

    @Test
    void testSave_EmptyFields_ShouldSaveParkWithMinimalDetails() {
        // Arrange
        Park park = Park.builder()
                .name("Minimal Park")
                .build();

        // Act
        Park savedPark = parkService.save(park);

        // Assert
        assertNotNull(savedPark.getId(), "Saved park should have an ID");
        assertEquals("Minimal Park", savedPark.getName(), "Saved park name should match");
    }

    @Test
    void testDeleteParkByParkId_NullId_ShouldThrowIllegalArgumentException() {
        // Arrange
        String nullId = null;

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> parkService.deleteParkByParkId(nullId),
                "Expected IllegalArgumentException for a null park ID");

        assertEquals("The park ID cannot be null", exception.getMessage());
    }

    @Test
    void testDeleteParkByParkId_NonExistentId_ShouldThrowEntityNotFoundException() {
        // Arrange
        String nonExistentId = "non-existent-id";

        // Act & Assert
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> parkService.deleteParkByParkId(nonExistentId),
                "Expected EntityNotFoundException for a non-existent park ID");

        assertTrue(exception.getMessage().contains("Park with id non-existent-id not found"));
    }

    @Test
    void testDeleteParkByParkId_ExistingId_ShouldDeletePark() {
        // Arrange
        Park park = Park.builder()
                .name("Park to Delete")
                .description("This park will be deleted.")
                .build();
        Park savedPark = parkService.save(park);

        // Act
        Park deletedPark = parkService.deleteParkByParkId(savedPark.getId());

        // Assert
        assertEquals(savedPark.getId(), deletedPark.getId(), "Deleted park ID should match");
        assertFalse(parkRepository.existsById(savedPark.getId()), "Deleted park should no longer exist in the repository");
    }
}


 */