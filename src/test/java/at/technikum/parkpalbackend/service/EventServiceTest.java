package at.technikum.parkpalbackend.service;

import at.technikum.parkpalbackend.TestFixtures;
import at.technikum.parkpalbackend.exception.EntityNotFoundException;
import at.technikum.parkpalbackend.model.Event;
import at.technikum.parkpalbackend.model.User;
import at.technikum.parkpalbackend.persistence.EventRepository;
import at.technikum.parkpalbackend.persistence.FileRepository;
import at.technikum.parkpalbackend.persistence.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

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
    /*
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
    */
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
}