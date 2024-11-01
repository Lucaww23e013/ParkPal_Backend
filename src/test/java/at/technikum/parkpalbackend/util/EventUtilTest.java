package at.technikum.parkpalbackend.util;

import at.technikum.parkpalbackend.dto.eventdtos.CreateEventDto;
import at.technikum.parkpalbackend.dto.eventdtos.EventDto;
import at.technikum.parkpalbackend.mapper.EventMapper;
import at.technikum.parkpalbackend.model.*;
import at.technikum.parkpalbackend.persistence.EventRepository;
import at.technikum.parkpalbackend.persistence.ParkRepository;
import at.technikum.parkpalbackend.service.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static at.technikum.parkpalbackend.TestFixtures.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EventUtilTest {

    @Mock
    private EventRepository eventRepository;

    @Mock
    private EventService eventService;

    @Mock
    private UserService userService;

    @Mock
    private EventMapper eventMapper;

    @Mock
    private FileService fileService;

    @Mock
    private ParkService parkService;

    @Mock
    private ParkRepository parkRepository;

    @Mock
    private EventTagService eventTagService;

    @InjectMocks
    private EventUtil eventUtil;

    @Test
    void saveCreateEvent_whenValidDto_thenReturnCreatedEventDto() {
        // Arrange

        CreateEventDto createEventDto = testCreateEventDto;
        createEventDto.setId(UUID.randomUUID().toString());

        User creator = adminUser;
        Park park = parkAwesome;
        park.setId(UUID.randomUUID().toString());

        List<File> mediaFiles = Arrays.asList(file, file2);

        EventTag tag1 = familyEventTag;
        tag1.setId(UUID.randomUUID().toString());
        EventTag tag2 = gamesEventTag;
        tag2.setId(UUID.randomUUID().toString());
        Set<EventTag> eventTags = new HashSet<>(Arrays.asList(tag1, tag2));

        createEventDto.setCreatorUserId(creator.getId());
        createEventDto.setParkId(park.getId());
        createEventDto.setMediaFileExternalIds(fileIdList);
        createEventDto.setEventTagsIds(new HashSet<>(Arrays.asList(familyEventTag.getId(), gamesEventTag.getId())));

        Event event = testEvent;
        CreateEventDto expectedEventDto = testCreateEventDto;

        when(userService.findByUserId(createEventDto.getCreatorUserId())).thenReturn(creator);
        when(parkService.findParkById(createEventDto.getParkId())).thenReturn(park);
        when(fileService.getFileList(createEventDto.getMediaFileExternalIds())).thenReturn(mediaFiles);
        when(eventTagService.findTagsByIds(createEventDto.getEventTagsIds())).thenReturn(eventTags);
        when(eventMapper.toEntityCreateEvent(createEventDto, creator, park, mediaFiles, eventTags)).thenReturn(event);
        when(eventService.save(event)).thenReturn(event);
        when(eventMapper.toDtoCreateEvent(event)).thenReturn(expectedEventDto);

        // Act
        CreateEventDto result = eventUtil.saveCreateEvent(createEventDto);

        // Assert
        assertEquals(expectedEventDto, result);

        // Verify interactions
        Mockito.verify(userService, times(1)).findByUserId(createEventDto.getCreatorUserId());
        Mockito.verify(parkService, times(1)).findParkById(createEventDto.getParkId());
        Mockito.verify(fileService, times(1)).getFileList(createEventDto.getMediaFileExternalIds());
        Mockito.verify(eventTagService, times(1)).findTagsByIds(createEventDto.getEventTagsIds());
        Mockito.verify(eventMapper, times(1)).toEntityCreateEvent(createEventDto, creator, park, mediaFiles, eventTags);
        Mockito.verify(eventService, times(1)).save(event);
        Mockito.verify(eventMapper, times(1)).toDtoCreateEvent(event);
        Mockito.verify(fileService, times(1)).setEventMedia(event, mediaFiles);
        verifyNoMoreInteractions(userService, parkService, fileService, eventTagService, eventMapper, eventRepository);
    }

    @Test
    void updateEvent_whenValidDto_thenReturnUpdatedEvent() {
        // Arrange
        String eventId = UUID.randomUUID().toString();
        String parkId = UUID.randomUUID().toString();
        String parkId2 = UUID.randomUUID().toString();

        // Create an existing event (this would be returned by eventRepository.findById)
        Event existingEvent = grilling;
        Park park = parkAwesome;
        grilling.setPark(park);
        existingEvent.getPark().setId(parkId);
        existingEvent.setJoinedUsers(userList);
        existingEvent.setTags(new HashSet<>());

        EventDto eventDto = eventDtoWithoutMedia;
        eventDto.setParkId(parkId2);
        User newUser1 = adminUser;
        newUser1.setId(UUID.randomUUID().toString());
        User newUser2 = adminUser2;
        newUser2.setId(UUID.randomUUID().toString());
        List<String> newJoinedUserIds = Arrays.asList(newUser1.getId(), newUser2.getId());
        eventDto.setJoinedUserIds(newJoinedUserIds);

        // Existing users (to be cleared)
        User oldUser1 = userList.get(0);
        User oldUser2 = userList.get(1);


        existingEvent.setJoinedUsers(alternateUserList);

        Park newPark = parkCool;  // Create a mock Park entity to represent the new Park
        newPark.setId(parkId2);

        // Mock parkRepository and userService
        when(userService.findByUserId(newUser1.getId())).thenReturn(newUser1);
        when(userService.findByUserId(newUser2.getId())).thenReturn(newUser2);
        when(parkRepository.findById(parkId2)).thenReturn(Optional.of(newPark));
        // Mock eventRepository to return the existing event when findById is called
        when(eventRepository.findById(eventId)).thenReturn(Optional.of(existingEvent));
        // Mock save to return the updated event
        when(eventService.save(existingEvent)).thenReturn(existingEvent);

        // Mock new tags to be added
        EventTag familyTag = familyEventTag;
        familyTag.setId(UUID.randomUUID().toString());
        EventTag gamesTag = gamesEventTag;
        gamesTag.setId(UUID.randomUUID().toString());
        Set<String> newEventTagIds = new HashSet<>(Arrays.asList(familyTag.getId(), gamesTag.getId()));
        eventDto.setEventTagsIds(newEventTagIds);

        when(eventTagService.findTagById(familyTag.getId())).thenReturn(familyTag);
        when(eventTagService.findTagById(gamesTag.getId())).thenReturn(gamesTag);

        // Mock new media files
        File newMediaFile1 = file;
        File newMediaFile2 = file2;
        List<String> newMediaFileExternalIds = Arrays.asList(newMediaFile1.getExternalId(), newMediaFile2.getExternalId());
        eventDto.setMediaFileExternalIds(newMediaFileExternalIds);

        when(fileService.findFileByExternalId(newMediaFile1.getExternalId())).thenReturn(newMediaFile1);
        when(fileService.findFileByExternalId(newMediaFile2.getExternalId())).thenReturn(newMediaFile2);

        // Act
        Event updatedEvent = eventUtil.updateEvent(eventId, eventDto);

        // Assert: Check that the basic details were updated
        assertEquals(eventDto.getTitle(), updatedEvent.getTitle());
        assertEquals(eventDto.getDescription(), updatedEvent.getDescription());
        assertEquals(eventDto.getStartTS(), updatedEvent.getStartTS());
        assertEquals(eventDto.getEndTS(), updatedEvent.getEndTS());
        assertEquals(eventDto.getParkId(), updatedEvent.getPark().getId());

        // Assert: Check that the users were updated
        assertEquals(2, updatedEvent.getJoinedUsers().size());
        assertTrue(updatedEvent.getJoinedUsers().contains(newUser1));
        assertTrue(updatedEvent.getJoinedUsers().contains(newUser2));

        // Ensure the old users no longer have the event in their joined events list
        assertFalse(oldUser1.getJoinedEvents().contains(existingEvent));
        assertFalse(oldUser2.getJoinedEvents().contains(existingEvent));

        // Ensure the new users now have the event in their joined events list
        assertTrue(newUser1.getJoinedEvents().contains(updatedEvent));
        assertTrue(newUser2.getJoinedEvents().contains(updatedEvent));

        // Assert: Check that the tags were updated
        assertEquals(2, updatedEvent.getTags().size());
        assertTrue(updatedEvent.getTags().contains(familyTag));
        assertTrue(updatedEvent.getTags().contains(gamesTag));

        // Ensure the bidirectional relationship is maintained for the tags
        assertTrue(familyTag.getEvents().contains(updatedEvent));
        assertTrue(gamesTag.getEvents().contains(updatedEvent));

        // Ensure the repository save method was called with the updated event
        Mockito.verify(eventService).save(existingEvent);

        // Verify that the findById method was called to retrieve the existing event and the new park
        Mockito.verify(eventRepository).findById(eventId);
        Mockito.verify(parkRepository).findById(parkId2);

        // Verify that userService was called to retrieve the new users
        Mockito.verify(userService).findByUserId(newUser1.getId());
        Mockito.verify(userService).findByUserId(newUser2.getId());
    }

    @Test
    void addEventToTags_whenTagsProvided_eventShouldBeAddedToEachTag() {
        // Arrange
        Event event = testEvent; // Assume Event class has default constructor
        EventTag familyTag = familyEventTag; // Fixture
        EventTag gamesTag = gamesEventTag; // Fixture

        Set<EventTag> tags = new HashSet<>(Arrays.asList(familyTag, gamesTag));

        // Act
        eventUtil.addEventToTags(tags, event);

        // Assert
        assertTrue(familyTag.getEvents().contains(event), "Event should be added to family tag");
        assertTrue(gamesTag.getEvents().contains(event), "Event should be added to games tag");
    }

    @Test
    void addEventToTags_whenNoTagsProvided_eventShouldNotBeAddedToAnyTag() {
        // Arrange
        Event event = testEvent;
        Set<EventTag> tags = new HashSet<>(); // Empty set

        // Act
        eventUtil.addEventToTags(tags, event);

        // Assert: Check that tags are still empty
        assertTrue(tags.isEmpty(), "Tag set should remain empty");
    }
}