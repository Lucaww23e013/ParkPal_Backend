package at.technikum.parkpalbackend.util;

import at.technikum.parkpalbackend.dto.parkdtos.CreateParkDto;
import at.technikum.parkpalbackend.dto.parkdtos.ParkDto;
import at.technikum.parkpalbackend.exception.EntityNotFoundException;
import at.technikum.parkpalbackend.mapper.ParkMapper;
import at.technikum.parkpalbackend.model.Event;
import at.technikum.parkpalbackend.model.File;
import at.technikum.parkpalbackend.model.Park;
import at.technikum.parkpalbackend.persistence.ParkRepository;
import at.technikum.parkpalbackend.service.EventService;
import at.technikum.parkpalbackend.service.FileService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static at.technikum.parkpalbackend.TestFixtures.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ParkUtilTest {

    @Mock
    private ParkRepository parkRepository;

    @Mock
    private EventService eventService;
    @Mock
    private FileService fileService;
    @Mock
    private ParkMapper parkMapper;

    @InjectMocks
    private ParkUtil parkUtil;

    @Test
    void updatePark_whenValidDto_thenReturnUpdatedPark() {
        // Arrange
        String parkId = parkAwesome.getId(); // Existing park ID
        Park existingPark = parkAwesome; // Existing park fixture

        // Create a ParkDto for the update
        ParkDto parkDto = parkDtoAwesome;

        // Create new event fixtures to update the park with
        Event newEvent1 = grilling;
        newEvent1.setId(UUID.randomUUID().toString());
        Event newEvent2 = pingPongGame;
        newEvent2.setId(UUID.randomUUID().toString());

        // Add the new event IDs to the ParkDto
        List<String> newEventIds = Arrays.asList(newEvent1.getId(), newEvent2.getId());
        parkDto.setEventIds(newEventIds);

        // Mock parkRepository to return the existing park
        when(parkRepository.findById(parkId)).thenReturn(Optional.of(existingPark));

        // Mock eventService to return the new events
        when(eventService.findByEventId(newEvent1.getId())).thenReturn(newEvent1);
        when(eventService.findByEventId(newEvent2.getId())).thenReturn(newEvent2);

        // Mock file service for media files (if necessary)
        File mediaFile1 = file;
        File mediaFile2 = file2;

        List<String> mediaFileExternalIds = Arrays.asList(mediaFile1.getExternalId(), mediaFile2.getExternalId());
        parkDto.setMediaFileExternalIds(mediaFileExternalIds);


        when(fileService.findFileByExternalId(mediaFile1.getExternalId())).thenReturn(mediaFile1);
        when(fileService.findFileByExternalId(mediaFile2.getExternalId())).thenReturn(mediaFile2);
        when(parkRepository.save(existingPark)).thenReturn(existingPark);

        // Act
        Park updatedPark = parkUtil.updatePark(parkId, parkDto);

        // Assert: Check that the basic details were updated
        assertEquals(parkDto.getName(), updatedPark.getName());
        assertEquals(parkDto.getDescription(), updatedPark.getDescription());
        assertEquals(parkDto.getAddress(), updatedPark.getAddress());

        // Assert: Check that the events were updated
        assertEquals(2, updatedPark.getEvents().size());
        assertTrue(updatedPark.getEvents().contains(newEvent1));
        assertTrue(updatedPark.getEvents().contains(newEvent2));

        // Ensure the bidirectional relationship is maintained for the events
        assertEquals(updatedPark, newEvent1.getPark());
        assertEquals(updatedPark, newEvent2.getPark());

        // Assert: Check that the media files were updated
        assertEquals(2, updatedPark.getMedia().size());
        assertTrue(updatedPark.getMedia().contains(mediaFile1));
        assertTrue(updatedPark.getMedia().contains(mediaFile2));

        // Ensure the bidirectional relationship is maintained for the media
        assertEquals(updatedPark, mediaFile1.getPark());
        assertEquals(updatedPark, mediaFile2.getPark());

        // Ensure the repository save method was called with the updated park
        verify(parkRepository).save(existingPark);

        // Verify that the findById method was called to retrieve the existing park
        verify(parkRepository).findById(parkId);

        // Verify that eventService was called to retrieve the new events
        verify(eventService).findByEventId(newEvent1.getId());
        verify(eventService).findByEventId(newEvent2.getId());

        // Verify that fileService was called to retrieve the new media files
        verify(fileService).findFileByExternalId(mediaFile1.getExternalId());
        verify(fileService).findFileByExternalId(mediaFile2.getExternalId());
    }

    @Test
    void updatePark_whenParkNotFound_thenThrowEntityNotFoundException() {
        // Arrange
        String parkId = UUID.randomUUID().toString(); // Non-existing park ID
        ParkDto parkDto = parkDtoAwesome; // Any valid DTO

        // Mock parkRepository to return empty Optional
        when(parkRepository.findById(parkId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> parkUtil.updatePark(parkId, parkDto));
    }

    @Test
    void updatePark_whenNoMediaFiles_thenNoMediaFilesUpdated() {
        // Arrange
        String parkId = parkAwesome.getId();
        Park existingPark = parkAwesome;

        // Create a ParkDto without media files
        ParkDto parkDto = parkDtoAwesome;
        parkDto.setMediaFileExternalIds(null); // No media files

        // Mock parkRepository to return the existing park
        when(parkRepository.findById(parkId)).thenReturn(Optional.of(existingPark));
        when(parkRepository.save(existingPark)).thenReturn(existingPark);

        // Act
        Park updatedPark = parkUtil.updatePark(parkId, parkDto);

        // Assert: Check that media files remain the same
        assertEquals(existingPark.getMedia(), updatedPark.getMedia());
    }

    @Test
    void updatePark_whenDuplicateEventIds_thenOnlyUniqueEventsUpdated() {
        // Arrange
        String parkId = parkAwesome.getId();
        Park existingPark = parkAwesome;

        // Create a ParkDto with duplicate event IDs
        Event existingEvent = grilling; // Assume this is a valid existing event
        ParkDto parkDto = parkDtoAwesome;
        parkDto.setEventIds(Arrays.asList(existingEvent.getId(), existingEvent.getId())); // Duplicate IDs

        // Mock parkRepository to return the existing park
        when(parkRepository.findById(parkId)).thenReturn(Optional.of(existingPark));
        when(eventService.findByEventId(existingEvent.getId())).thenReturn(existingEvent);
        when(parkRepository.save(existingPark)).thenReturn(existingPark);

        // Act
        Park updatedPark = parkUtil.updatePark(parkId, parkDto);

        // Assert: Check that events contain the existing event only once
        assertEquals(1, updatedPark.getEvents().size());
        assertTrue(updatedPark.getEvents().contains(existingEvent));
    }

    @Test
    void testSaveCreatePark_Success() {
        // Arrange
        CreateParkDto createParkDto = CreateParkCoolDto;

        CreateParkDto ExpectedCreateParkDto = CreateExpectedParkCoolDto;


        List<File> mediaFiles = new ArrayList<>();

        when(fileService.getFileList(createParkDto.getMediaFileExternalIds())).thenReturn(mediaFiles);

        Park park = parkCool;
        when(parkMapper.createParkDtoToEntity(createParkDto, mediaFiles)).thenReturn(park);
        when(parkRepository.save(park)).thenReturn(park);
        when(parkMapper.toCreateParkDto(park)).thenReturn(ExpectedCreateParkDto);

        // Act
        CreateParkDto savedParkDto = parkUtil.saveCreatePark(createParkDto);

        // Assert
        assertNotNull(savedParkDto);
        assertEquals(createParkDto.getName(), savedParkDto.getName());
        assertEquals(createParkDto.getDescription(), savedParkDto.getDescription());
        assertEquals(createParkDto.getAddress(), savedParkDto.getAddress());
        verify(parkRepository).save(park);
    }

    @Test
    void testSaveCreatePark_ExistingName() {
        // Arrange
        CreateParkDto createParkDto = CreateParkCoolDto;

        when(parkRepository.existsByName(createParkDto.getName())).thenReturn(true);

        // Act & Assert
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            parkUtil.saveCreatePark(createParkDto);
        });

        // Verify exception message
        assertEquals("A park with the name 'Cool Park' already exists.", exception.getMessage());
        verify(parkRepository).existsByName(createParkDto.getName());
        verify(parkRepository, never()).save(any());
    }

    @Test
    void testUpdateParkMedia_WithNewMedia() {
        // Arrange
        Park park = parkCool;
        park.setMedia(new ArrayList<>());

        ParkDto parkDto = parkCoolDto;
        parkDto.setMediaFileExternalIds(List.of("file-ext-1", "file-ext-2")); // New media IDs

        File mediaFile1 = file;
        File mediaFile2 = file2;

        when(fileService.findFileByExternalId("file-ext-1")).thenReturn(mediaFile1);
        when(fileService.findFileByExternalId("file-ext-2")).thenReturn(mediaFile2);

        // Act
        parkUtil.updateParkMedia(park, parkDto);

        // Verify new media was added and relationships set
        assertEquals(2, park.getMedia().size());
        assertTrue(park.getMedia().contains(mediaFile1));
        assertTrue(park.getMedia().contains(mediaFile2));

        // Verify that fileService was called for each media file
        verify(fileService).findFileByExternalId("file-ext-1");
        verify(fileService).findFileByExternalId("file-ext-2");
    }

    /*
    @Test
    void testUpdateParkEvents_WithNonNullEventIds() {
        // Arrange
        Park park = parkWithEvents;

        Event oldEvent1 = grilling;
        Event oldEvent2 = pingPongGame;

        park.setEvents(new ArrayList<>(List.of(oldEvent1, oldEvent2)));

        Event newEvent1 = chessMaster;
        Event newEvent2 = pickNickWithYourFamily;

        ParkDto parkDto = parkDtoWithMedia;
        parkDto.setEventIds(Arrays.asList("eventId1", "eventId2"));  // Non-null event IDs

        // Mocking eventService to return specific events based on eventId
        when(eventService.findByEventId("eventId1")).thenReturn(newEvent1);
        when(eventService.findByEventId("eventId2")).thenReturn(newEvent2);

        // Act
        parkUtil.updateParkEvents(park, parkDto); // Assuming parkUtil contains updateParkEvents

        // Assert
        assertEquals(2, park.getEvents().size());  // Park should have 2 new events
        assertTrue(park.getEvents().contains(newEvent1));  // Ensure the new events are added
        assertTrue(park.getEvents().contains(newEvent2));
        assertNull(oldEvent1.getPark());  // Ensure the old events no longer have the park associated
        assertNull(oldEvent2.getPark());

        // Verify that eventService was called with the correct event IDs
        verify(eventService).findByEventId("eventId1");
        verify(eventService).findByEventId("eventId2");
    }
    */
    @Test
    void testUpdateParkEvents_WithNullEventIds() {
        // Arrange
        Park park = parkWithEvents;

        Event oldEvent1 = grilling;
        Event oldEvent2 = pingPongGame;
        park.setEvents(new ArrayList<>(List.of(oldEvent1, oldEvent2))); // Old events are set

        ParkDto parkDto = parkDtoWithMedia;
        parkDto.setEventIds(null);  // Null event IDs

        // Act
        parkUtil.updateParkEvents(park, parkDto);

        // Assert
        assertEquals(2, park.getEvents().size());  // The old events should still be there
        assertTrue(park.getEvents().contains(oldEvent1));  // Ensure old events are retained
        assertTrue(park.getEvents().contains(oldEvent2));

        // Verify that the eventService was not called
        verifyNoInteractions(eventService);
    }

    @Test
    void testUpdateParkEvents_WithEmptyEventIds() {
        // Arrange
        Park park = parkWithEvents;

        Event oldEvent1 = grilling;
        Event oldEvent2 = pingPongGame;
        park.setEvents(new ArrayList<>(List.of(oldEvent1, oldEvent2))); // Old events are set

        ParkDto parkDto = parkDtoWithMedia;
        parkDto.setEventIds(new ArrayList<>());  // Empty event IDs

        // Act
        parkUtil.updateParkEvents(park, parkDto);

        // Assert
        assertTrue(park.getEvents().isEmpty());  // All events should be cleared
        assertNull(oldEvent1.getPark());  // Ensure old events no longer have the park associated
        assertNull(oldEvent2.getPark());

        // Verify that no interactions with eventService happened since the list is empty
        verifyNoInteractions(eventService);
    }

    @Test
    void testUpdateParkEvents_WhenEventNotFound() {
        // Arrange
        Park park = parkWithEvents;

        Event oldEvent1 = grilling;
        Event oldEvent2 = pingPongGame;
        park.setEvents(new ArrayList<>(List.of(oldEvent1, oldEvent2))); // Old events are set

        ParkDto parkDto = parkDtoWithMedia;
        parkDto.setEventIds(Arrays.asList("eventId1", "eventId2"));  // Non-null event IDs

        Event newEvent1 = chessMaster;

        // Mocking eventService to return one event and one null (not found)
        when(eventService.findByEventId("eventId1")).thenReturn(newEvent1);
        when(eventService.findByEventId("eventId2")).thenReturn(null);  // Event not found

        // Act
        parkUtil.updateParkEvents(park, parkDto);

        // Assert
        assertEquals(1, park.getEvents().size());  // Only one valid event should be added
        assertTrue(park.getEvents().contains(newEvent1));  // Ensure the valid event is added
        assertNull(oldEvent1.getPark());  // Ensure old events are removed
        assertNull(oldEvent2.getPark());

        // Verify that eventService was called with the correct event IDs
        verify(eventService).findByEventId("eventId1");
        verify(eventService).findByEventId("eventId2");
    }
}