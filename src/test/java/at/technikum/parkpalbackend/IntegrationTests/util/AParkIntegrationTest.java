package at.technikum.parkpalbackend.IntegrationTests.util;

import at.technikum.parkpalbackend.dto.parkdtos.ParkDto;
import at.technikum.parkpalbackend.exception.EntityNotFoundException;
import at.technikum.parkpalbackend.model.*;
import at.technikum.parkpalbackend.persistence.*;
import at.technikum.parkpalbackend.service.*;
import at.technikum.parkpalbackend.util.ParkUtil;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;

import static at.technikum.parkpalbackend.TestFixtures.*;
import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@ActiveProfiles("test")
@Transactional
class AParkIntegrationTest {

    @MockBean
    private MinioService minioService;

    @Autowired
    private ParkService parkService;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ParkRepository parkRepository;

    @Autowired
    private EventService eventService;

    @Autowired
    private FileService fileService;

    @Autowired
    private ParkUtil parkUtil;

    @Autowired
    private FileRepository fileRepository;

    @Autowired
    private CountryRepository countryRepository;

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private UserService userService;

    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Park park;
    private Country country;
    private ParkDto sampleParkDto;

    @BeforeEach
    void setUp() {
        entityManager.flush();
        entityManager.clear();
        parkRepository.flush();
        userRepository.flush();
        eventRepository.flush();
        fileRepository.flush();
        countryRepository.flush();
        parkRepository.deleteAll();
        userRepository.deleteAll();
        eventRepository.deleteAll();
        fileRepository.deleteAll();
        countryRepository.deleteAll();

        park = parkService.save(alternatePark);
        country = countryRepository.save(germany);
        startTime = LocalDateTime.now().plusHours(1).truncatedTo(ChronoUnit.MINUTES);
        endTime = startTime.plusMinutes(31);

        sampleParkDto = ParkDto.builder()
            .name("Sample Park")
            .description("A description for the sample park.")
            .build();
    }

    @Test
    public void testUpdatePark_UpdateMediaFiles_ShouldReplaceOldMedia() {
        //Arrange
        User user2 = userService.save(simpleUser);

        Address address = parkAddress;
        address.setCountry(country);

        File parkFile = file;
        parkFile.setUser(user2);
        parkFile = fileService.save(parkFile);

        File alternateParkFile = file2;
        alternateParkFile.setUser(user2);
        alternateParkFile = fileService.save(alternateParkFile);

        // Update with new media
        ParkDto initialDto = sampleParkDto;
        initialDto.setName("Unique Park Name 1"); // Ensure unique name
        initialDto.setAddress(address);
        initialDto.setMediaFileExternalIds(List.of(alternateParkFile.getExternalId(), parkFile.getExternalId()));

        // Act: Apply the update
        Park finalUpdatedPark = parkUtil.updatePark(park.getId(), initialDto);

        // Assert
        assertEquals(2, finalUpdatedPark.getMedia().size());
        assertTrue(finalUpdatedPark.getMedia().contains(alternateParkFile));
        assertTrue(finalUpdatedPark.getMedia().contains(parkFile));
    }

    @Test
    void testUpdatePark_AddEvents_ShouldAssociateEvents() {
        // Arrange
        Address address = parkAddress;
        address.setCountry(country);

        Event event1 = eventService.save(grilling);
        event1.setStartTS(startTime);
        event1.setEndTS(endTime);
        Event event2 = eventService.save(pingPongGame);
        event2.setStartTS(startTime);
        event2.setEndTS(endTime);

        ParkDto parkDto = sampleParkDto;
        parkDto.setAddress(address);
        parkDto.setEventIds(List.of(event1.getId(), event2.getId()));

        // Act
        Park updatedPark = parkUtil.updatePark(park.getId(), parkDto);

        // Assert
        assertEquals(2, updatedPark.getEvents().size());
        assertTrue(updatedPark.getEvents().contains(event1));
        assertTrue(updatedPark.getEvents().contains(event2));
    }

    @Test
    void testUpdatePark_UpdateEvents_ShouldReplaceOldEvents() {
        // Arrange
        Event event1 = grilling;
        event1.setStartTS(startTime);
        event1.setEndTS(endTime);
        Event event3 = eventService.save(event1);

        Event event2 = pingPongGame;
        event2.setStartTS(startTime);
        event2.setEndTS(endTime);
        Event event4 = eventService.save(event2);

        Address address = parkAddress;
        address.setCountry(country);

        ParkDto initialDto = sampleParkDto;
        initialDto.setAddress(address);

        initialDto.setEventIds(List.of(event3.getId()));
        parkUtil.updatePark(park.getId(), initialDto);
        ParkDto newParkDto = sampleParkDto;
        newParkDto.setAddress(address);
        newParkDto.setEventIds(List.of(event4.getId()));

        // Act
        Park updatedPark = parkUtil.updatePark(park.getId(), newParkDto);

        // Assert: Only the new event should be associated
        assertEquals(1, updatedPark.getEvents().size());
        assertTrue(updatedPark.getEvents().contains(event4));
        assertFalse(updatedPark.getEvents().contains(event3));
    }


    @Test
    void testUpdatePark_DuplicateParkName_ShouldThrowEntityAlreadyExistsException() {
        // Arrange
        Address address = parkAddress;
        address.setCountry(country);

        Park orignalPark = Park.builder()
            .name("Existing Park Name")
            .description("Existing description")
            .address(address)
            .build();
        parkService.save(orignalPark);

        Park otherPark = Park.builder()
            .name("Park Name")
            .description("Description")
            .address(address)
            .build();
        parkService.save(otherPark);

        // Update a otherPark with the parkDto with "Existing Park Name" to set up the conflict
        ParkDto existingParkDto = sampleParkDto;
        existingParkDto.setName("Existing Park Name");
        existingParkDto.setAddress(address);

        // Act
        parkUtil.updatePark(otherPark.getId(), existingParkDto);  // Perform the update

        // Assert
        assertThrows(DataIntegrityViolationException.class, () -> {
            parkRepository.flush(); // This should trigger the exception if there's a duplicate
        });
    }

    @Test
    void testUpdatePark_NonExistentParkId_ShouldThrowEntityNotFoundException() {
        // Arrange
        String nonExistentId = UUID.randomUUID().toString();
        ParkDto parkDto = parkDtoAwesome;

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> parkUtil.updatePark(nonExistentId, parkDto));
    }
}