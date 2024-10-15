package at.technikum.parkpalbackend.service;

import at.technikum.parkpalbackend.TestFixtures;
import at.technikum.parkpalbackend.exception.EntityNotFoundException;
import at.technikum.parkpalbackend.model.Event;
import at.technikum.parkpalbackend.model.File;
import at.technikum.parkpalbackend.model.User;
import at.technikum.parkpalbackend.persistence.EventRepository;
import at.technikum.parkpalbackend.persistence.FileRepository;
import at.technikum.parkpalbackend.persistence.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static at.technikum.parkpalbackend.TestFixtures.grilling;
import static at.technikum.parkpalbackend.TestFixtures.pingPongGame;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
class EventServiceTest {

    @Mock
    private EventRepository eventRepository;

    private UserRepository userRepository;

    @Mock
    private FileRepository fileRepository;

    @Mock
    private UserService userService;

    @InjectMocks
    private EventService eventService;

    @Test
    void saveEvent_SuccessfullySaved_thenReturnSavedEvent() {
        // Arrange
        Event event = TestFixtures.grilling;
        event.setId(UUID.randomUUID().toString());
        when(eventRepository.save(event)).thenReturn(event);
        // Act
        Event result = eventService.save(event);
        // Assert
        assertNotNull(result);
        assertEquals(event, result);
        verify(eventRepository).save(event);
    }

    @Test
    void findAllEvents_whenEventsExist_thenReturnEvents() {
        // Arrange
        Event event1 = grilling;
        Event event2 = pingPongGame;
        List<Event> expectedEvents = new ArrayList<>();
        expectedEvents.add(event1);
        expectedEvents.add(event2);

        when(eventRepository.findAll()).thenReturn(expectedEvents);
        // Act
        List<Event> foundEvents = eventService.findAllEvents();
        // Assert
        assertNotNull(foundEvents);
        assertEquals(expectedEvents, foundEvents);
        verify(eventRepository).findAll();
    }


    @Test
    void findAllEvents_whenNoEventsExist_thenReturnEmptyList() {
        // Arrange
        when(eventRepository.findAll()).thenReturn(new ArrayList<>());
        // Act
        List<Event> foundEvents = eventService.findAllEvents();
        // Assert
        assertEquals(0, foundEvents.size());
        verify(eventRepository).findAll();
    }

    @Test
    void findByEventId_whenEventDoesNotExist_thenThrowEntityNotFoundException() {
        // Arrange
        String eventId = UUID.randomUUID().toString();
        when(eventRepository.findById(eventId)).thenReturn(Optional.empty());
        // Act + Assert
        assertThrows(EntityNotFoundException.class, () -> eventService.findByEventId(eventId));
        verify(eventRepository).findById(eventId);
    }

    @Test
    void findAllEventsCreatedByUser_whenUserExists_thenReturnAllEvents() {
        // Arrange
        String userId = UUID.randomUUID().toString();
        User user = TestFixtures.normalUser;
        user.setId(userId);
        // prepare expected events
        Event event1 = grilling;
        Event event2 = pingPongGame;
        List<Event> expectedEvents = new ArrayList<>();
        expectedEvents.add(event1);
        expectedEvents.add(event2);

        when(userService.findByUserId(userId)).thenReturn(user);
        when(eventRepository.findAllByCreatorId(userId)).thenReturn(expectedEvents);

        // Act
        List<Event> foundEvents = eventService.findAllEventsCreatedByUser(userId);
        // Assert
        assertNotNull(foundEvents);
        assertEquals(2, foundEvents.size());
        assertEquals(expectedEvents, foundEvents);
        verify(userService).findByUserId(userId);
        verify(eventRepository).findAllByCreatorId(userId);
    }

    @Test
    void findAllEventsCreatedByUser_whenUserDoesNotExist_thenThrowEntityNotFoundException() {
        // Arrange
        String userId = UUID.randomUUID().toString();
        userRepository = mock(UserRepository.class);
        userService = new UserService(userRepository);
        eventService = new EventService(eventRepository, fileRepository, userService);
        // Act + Assert
        assertThrows(EntityNotFoundException.class, () -> eventService.findAllEventsCreatedByUser(userId));
    }

    @Test
    void findAllEventsCreatedByUser_whenUnknownError_thenThrowRuntimeException() {
        // Arrange
        String userId = UUID.randomUUID().toString();
        User user = TestFixtures.normalUser;
        userRepository = mock(UserRepository.class);
        userService = new UserService(userRepository);
        eventService = new EventService(eventRepository, fileRepository, userService);
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(eventRepository.findAllByCreatorId(userId)).thenThrow(new RuntimeException());
        // Act + Assert
        assertThrows(RuntimeException.class, () -> eventService.findAllEventsCreatedByUser(userId));
    }

    @Test
    void deleteEventById_whenEventExists_thenDeleteEvent() {
        // Arrange
        String eventId = UUID.randomUUID().toString();
        Event eventToDelete = grilling;
        when(eventRepository.findById(eventId)).thenReturn(Optional.of(eventToDelete));
        // Act
        Event deletedEvent = eventService.deleteEventById(eventId);
        // Assert
        assertNotNull(deletedEvent);
        assertEquals(eventToDelete, deletedEvent);
        verify(eventRepository, times(1)).delete(eventToDelete);
    }

    @Test
    void deleteEventById_whenEventDoesNotExist_thenThrowEntityNotFoundException() {
        // Arrange
        String eventId = UUID.randomUUID().toString();
        Event eventToDelete = grilling;
        when(eventRepository.findById(eventId)).thenReturn(Optional.empty());
        // Act + Assert
        assertThrows(EntityNotFoundException.class, () -> eventService.deleteEventById(eventId));
        verify(eventRepository, times(0)).delete(eventToDelete);
    }

    @Test
    void deleteEventById_whenUnknownError_thenThrowRuntimeException() {
        // Arrange
        String eventId = UUID.randomUUID().toString();
        Event eventToDelete = grilling;
        eventToDelete.setId(eventId);
        when(eventRepository.findById(eventId)).thenReturn(Optional.of(eventToDelete));
        when(eventService.deleteEventById(eventId)).thenThrow(new RuntimeException());
        // Act + Assert
        assertThrows(RuntimeException.class, () -> eventService.deleteEventById(eventId));
        verify(eventRepository, times(1)).delete(eventToDelete);
    }

    @Test
    void updateEventById_whenEventExists_thenUpdateEvent() {
        // Arrange
        // prepare data
        String eventId = UUID.randomUUID().toString();
        Event oldEvent = grilling;
        Event eventToUpdate = grilling;
        eventToUpdate.setDescription("professional grilling");
        eventToUpdate.setStartTS(oldEvent.getStartTS().plusHours(3));
        eventToUpdate.setEndTS(oldEvent.getEndTS().plusHours(7));

        when(eventRepository.findById(eventId)).thenReturn(Optional.of(oldEvent));
        when(eventRepository.save(any(Event.class))).thenReturn(eventToUpdate);
        // Act
        Event updatedEvent = eventService.updateEvent(eventId, eventToUpdate);
        // Assert
        assertNotNull(updatedEvent);
        assertEquals(eventToUpdate, updatedEvent);
        verify(eventRepository).save(eventToUpdate);
    }

    @Test
    void updateEventById_whenEventDoesNotExist_thenThrowEntityNotFoundException() {
        // Arrange
        // prepare data
        String eventId = UUID.randomUUID().toString();
        Event eventToUpdate = grilling;

        when(eventRepository.findById(eventId)).thenReturn(Optional.empty());
        // Act + Assert
        assertThrows(EntityNotFoundException.class, () -> eventService.updateEvent(eventId, eventToUpdate));
        verify(eventRepository, times(0)).save(eventToUpdate);
    }

    @Test
    void updateEventById_whenUnknownError_thenThrowRuntimeException() {
        // Arrange
        // prepare data
        String eventId = UUID.randomUUID().toString();
        Event oldEvent = grilling;
        Event eventToUpdate = grilling;
        eventToUpdate.setDescription("professional grilling");
        eventToUpdate.setStartTS(oldEvent.getStartTS().plusHours(3));
        eventToUpdate.setEndTS(oldEvent.getEndTS().plusHours(7));

        when(eventRepository.findById(eventId)).thenReturn(Optional.of(oldEvent));
        when(eventRepository.save(any(Event.class))).thenThrow(new RuntimeException());
        // Act + Assert
        assertThrows(RuntimeException.class, () -> eventService.updateEvent(eventId, eventToUpdate));
        verify(eventRepository, times(1)).save(eventToUpdate);
    }

    @Test
    void findAllEventsJoinedByUser_whenUserExistsAndJoinedEvents_thenReturnAllJoinedEvents() {
        // Arrange
        String userId = UUID.randomUUID().toString();
        User user = TestFixtures.normalUser;
        user.setId(userId);
        Event event1 = grilling;
        Event event2 = pingPongGame;
        List<Event> joinedEvents = List.of(event1, event2);

        when(userService.findByUserId(userId)).thenReturn(user);
        when(eventRepository.findAllByJoinedUsersId(userId)).thenReturn(joinedEvents);

        // Act
        List<Event> result = eventService.findAllEventsJoinedByUser(userId);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(joinedEvents, result);
        verify(eventRepository).findAllByJoinedUsersId(userId);
    }

    @Test
    void findEventCreatorUserId_whenEventExists_thenReturnCreatorUserId() {
        // Arrange
        String eventId = UUID.randomUUID().toString();
        Event event = grilling;
        User creator = TestFixtures.normalUser;
        creator.setId("user123");
        event.setCreator(creator);

        when(eventRepository.findById(eventId)).thenReturn(Optional.of(event));

        // Act
        String result = eventService.findEventCreatorUserId(eventId);

        // Assert
        assertNotNull(result);
        assertEquals("user123", result);
        verify(eventRepository).findById(eventId);
    }

    @Test
    void findEventCreatorName_whenEventExists_thenReturnCreatorName() {
        // Arrange
        String eventId = UUID.randomUUID().toString();
        Event event = grilling;
        User creator = TestFixtures.normalUser;
        creator.setUserName("John Doe");
        event.setCreator(creator);

        when(eventRepository.findById(eventId)).thenReturn(Optional.of(event));

        // Act
        String result = eventService.findEventCreatorName(eventId);

        // Assert
        assertNotNull(result);
        assertEquals("John Doe", result);
        verify(eventRepository).findById(eventId);
    }

    @Test
    void findAllEventsByUserIdAndParkId_whenEventsExist_thenReturnEvents() {
        // Arrange
        String userId = UUID.randomUUID().toString();
        String parkId = UUID.randomUUID().toString();
        List<Event> events = List.of(grilling, pingPongGame);

        when(eventRepository.findAllByCreatorIdAndParkId(userId, parkId)).thenReturn(events);

        // Act
        List<Event> result = eventService.findAllEventsByUserIdAndParkId(userId, parkId);

        // Assert
        assertNotNull(result);
        assertEquals(events, result);
        verify(eventRepository).findAllByCreatorIdAndParkId(userId, parkId);
    }

    @Test
    void findEventsByIds_whenEventsExist_thenReturnEvents() {
        // Arrange
        Set<String> eventIds = Set.of(UUID.randomUUID().toString(), UUID.randomUUID().toString());
        List<Event> events = List.of(grilling, pingPongGame);

        when(eventRepository.findAllById(eventIds)).thenReturn(events);

        // Act
        Set<Event> result = eventService.findEventsByIds(eventIds);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertTrue(result.containsAll(events));
        verify(eventRepository).findAllById(eventIds);
    }

    @Test
    void saveEvent_whenEventIsNull_thenThrowIllegalArgumentException() {
        // Act + Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> eventService.save(null));
        assertEquals("The event Cannot be null.", exception.getMessage());
        verify(eventRepository, never()).save(any(Event.class));
    }

    @Test
    void findByEventId_whenEventIdIsNull_thenThrowEntityNotFoundException() {
        // Act + Assert
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> eventService.findByEventId(null));
        assertEquals("The event ID Cannot be null.", exception.getMessage());
        verify(eventRepository, never()).findById(anyString());
    }

    @Test
    void updateEvent_whenEventIdIsNull_thenThrowIllegalArgumentException() {
        // Arrange
        Event eventToUpdate = grilling;

        // Act + Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> eventService.updateEvent(null, eventToUpdate));
        assertEquals("The event ID Cannot be null", exception.getMessage());
        verify(eventRepository, never()).save(any(Event.class));
    }

    @Test
    void updateEvent_whenUpdatedEventIsNull_thenThrowIllegalArgumentException() {
        // Arrange
        String eventId = UUID.randomUUID().toString();

        // Act + Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> eventService.updateEvent(eventId, null));
        assertEquals("The updated event Cannot be null.", exception.getMessage());
        verify(eventRepository, never()).save(any(Event.class));
    }

    @Test
    void updateEvent_whenEventFilesAreUpdated_thenUpdateFileAssociations() {
        // Arrange
        String eventId = UUID.randomUUID().toString();

        Event oldEvent = grilling;
        File oldFile = File.builder().build();
        oldFile.setId(UUID.randomUUID().toString());
        oldEvent.setMedia(List.of(oldFile));

        Event updatedEvent = grilling;
        File newFile = File.builder().build();
        newFile.setId(UUID.randomUUID().toString());
        updatedEvent.setMedia(List.of(newFile));

        when(eventRepository.findById(eventId)).thenReturn(Optional.of(oldEvent));
        when(eventRepository.save(any(Event.class))).thenReturn(updatedEvent);

        // Act
        Event result = eventService.updateEvent(eventId, updatedEvent);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getMedia().size());
        assertEquals(newFile, result.getMedia().get(0));
        assertNull(oldFile.getEvent());
        verify(fileRepository, times(2)).save(any(File.class));
        verify(eventRepository).save(any(Event.class));
    }

    @Test
    void updateEvent_whenNoMedia_thenNoFileOperations() {
        // Arrange
        String eventId = UUID.randomUUID().toString();

        Event oldEvent = grilling;
        oldEvent.setMedia(Collections.emptyList());

        Event updatedEvent = grilling;
        updatedEvent.setMedia(Collections.emptyList());

        when(eventRepository.findById(eventId)).thenReturn(Optional.of(oldEvent));
        when(eventRepository.save(any(Event.class))).thenReturn(updatedEvent);

        // Act
        Event result = eventService.updateEvent(eventId, updatedEvent);

        // Assert
        assertNotNull(result);
        assertEquals(0, result.getMedia().size());
        verify(fileRepository, never()).save(any(File.class));
        verify(eventRepository).save(any(Event.class));
    }

    @Test
    void updateEvent_whenFilesAreDisassociated_thenHandleCorrectly() {
        // Arrange
        String eventId = UUID.randomUUID().toString();

        Event oldEvent = grilling;
        File oldFile1 = File.builder().build();
        oldFile1.setId(UUID.randomUUID().toString());
        File oldFile2 = File.builder().build();
        oldFile2.setId(UUID.randomUUID().toString());
        oldEvent.setMedia(List.of(oldFile1, oldFile2));

        Event updatedEvent = grilling;
        File newFile = File.builder().build();
        newFile.setId(UUID.randomUUID().toString());
        updatedEvent.setMedia(List.of(newFile));

        when(eventRepository.findById(eventId)).thenReturn(Optional.of(oldEvent));
        when(eventRepository.save(any(Event.class))).thenReturn(updatedEvent);

        // Act
        Event result = eventService.updateEvent(eventId, updatedEvent);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getMedia().size());
        assertEquals(newFile, result.getMedia().get(0));
        assertNull(oldFile1.getEvent());
        assertNull(oldFile2.getEvent());
        verify(fileRepository, times(2)).save(any(File.class)); // Two disassociates, one associate
        verify(eventRepository).save(any(Event.class));
    }

    @Test
    void findAllEventsJoinedByUser_whenUserDoesNotExist_thenReturnEmptyList() {
        // Arrange
        String userId = UUID.randomUUID().toString();

        when(userService.findByUserId(userId)).thenReturn(null);

        // Act
        List<Event> result = eventService.findAllEventsJoinedByUser(userId);

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(userService).findByUserId(userId);
        verify(eventRepository, never()).findAllByJoinedUsersId(anyString());
    }

    @Test
    void findAllEventsByUserIdAndParkId_whenNoEventsExist_thenReturnEmptyList() {
        // Arrange
        String userId = UUID.randomUUID().toString();
        String parkId = UUID.randomUUID().toString();

        when(eventRepository.findAllByCreatorIdAndParkId(userId, parkId)).thenReturn(Collections.emptyList());

        // Act
        List<Event> result = eventService.findAllEventsByUserIdAndParkId(userId, parkId);

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(eventRepository).findAllByCreatorIdAndParkId(userId, parkId);
    }

    @Test
    void findAllEventsByParkId_whenNoEventsExist_thenReturnEmptyList() {
        // Arrange
        String parkId = UUID.randomUUID().toString();

        when(eventRepository.findAllByParkId(parkId)).thenReturn(Collections.emptyList());

        // Act
        List<Event> result = eventService.findAllEventsByParkId(parkId);

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(eventRepository).findAllByParkId(parkId);
    }

    @Test
    void findEventsByIds_whenGivenEmptySet_thenReturnEmptySet() {
        // Arrange
        Set<String> eventIds = new HashSet<>();

        when(eventRepository.findAllById(eventIds)).thenReturn(Collections.emptyList());

        // Act
        Set<Event> result = eventService.findEventsByIds(eventIds);

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(eventRepository).findAllById(eventIds);
    }
}