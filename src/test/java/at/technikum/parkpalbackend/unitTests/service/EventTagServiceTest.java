package at.technikum.parkpalbackend.unitTests.service;

import at.technikum.parkpalbackend.exception.EntityNotFoundException;
import at.technikum.parkpalbackend.exception.ResourceAccessException;
import at.technikum.parkpalbackend.model.EventTag;
import at.technikum.parkpalbackend.persistence.EventTagRepository;
import at.technikum.parkpalbackend.service.EventTagService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataAccessResourceFailureException;

import java.util.*;

import static at.technikum.parkpalbackend.TestFixtures.familyEventTag;
import static at.technikum.parkpalbackend.TestFixtures.gamesEventTag;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EventTagServiceTest {

    @Mock
    private EventTagRepository eventTagRepository;

    @InjectMocks
    private EventTagService eventTagService;

    @Test
    void saveEventTag_successfullySaved_thenReturnSavedEventTag() {
        // Arrange
        EventTag eventTag = familyEventTag;
        eventTag.setId(UUID.randomUUID().toString());
        when(eventTagRepository.save(eventTag)).thenReturn(eventTag);
        // Act
        EventTag savedEventTag = eventTagService.save(eventTag);
        // Assert
        assertNotNull(savedEventTag);
        assertEquals(eventTag, savedEventTag);
        verify(eventTagRepository).save(eventTag);
    }

    @Test
    void findAllEventTagSet_successfullyReturned_thenReturnList() {
        // Arrange
        List<EventTag> eventTagList = new ArrayList<>();
        eventTagList.add(familyEventTag);
        eventTagList.add(gamesEventTag);
        when(eventTagRepository.findAll()).thenReturn(eventTagList);
        Set<EventTag> eventTagsSet = new HashSet<>(eventTagList);
        // Act
        Set<EventTag> savedEventTags = eventTagService.findAllEventTagSet();
        // Assert
        assertNotNull(savedEventTags);
        assertEquals(eventTagsSet, savedEventTags);
        verify(eventTagRepository).findAll();
    }

    @Test
    void findTagById_successfullyReturned_thenReturnEventTag() {
        // Arrange
        EventTag eventTag = familyEventTag;
        when(eventTagRepository.findById(eventTag.getId())).thenReturn(Optional.of(eventTag));
        // Act
        EventTag savedEventTag = eventTagService.findTagById(eventTag.getId());
        // Assert
        assertNotNull(savedEventTag);
        assertEquals(eventTag, savedEventTag);
        verify(eventTagRepository).findById(eventTag.getId());
    }

    @Test
    void findTagById_whenEventTagDoesNotExist_thenThrowEntityNotFoundException() {
        // Arrange
        String eventTagId = UUID.randomUUID().toString();
        when(eventTagRepository.findById(eventTagId)).thenReturn(Optional.empty());
        // Act + Assert
        assertThrows(EntityNotFoundException.class, () -> eventTagService.findTagById(eventTagId));
        verify(eventTagRepository).findById(eventTagId);
    }

    @Test
    void deleteTagById_successfullyDeleted_thenReturnDeletedEventTag() {
        // Arrange
        EventTag eventTag = familyEventTag;
        when(eventTagRepository.findById(eventTag.getId())).thenReturn(Optional.of(eventTag));
        // Act
        EventTag deletedEventTag = eventTagService.deleteTagById(eventTag.getId());
        // Assert
        assertNotNull(deletedEventTag);
        assertEquals(eventTag, deletedEventTag);
        verify(eventTagRepository).findById(eventTag.getId());
        verify(eventTagRepository).delete(eventTag);
    }

    @Test
    void deleteTagById_whenEventTagDoesNotExist_thenThrowEntityNotFoundException() {
        // Arrange
        String eventTagId = UUID.randomUUID().toString();
        when(eventTagRepository.findById(eventTagId)).thenReturn(Optional.empty());
        // Act + Assert
        assertThrows(EntityNotFoundException.class, () -> eventTagService.deleteTagById(eventTagId));
        verify(eventTagRepository).findById(eventTagId);
    }

    @Test
    void deleteTagById_whenDataAccessException_thenThrowRuntimeException() {
        // Arrange
        EventTag eventTag = familyEventTag;
        String eventTagId = UUID.randomUUID().toString();
        eventTag.setId(eventTagId);

        when(eventTagRepository.findById(eventTagId)).thenReturn(Optional.of(eventTag));
        doThrow(DataAccessResourceFailureException.class).when(eventTagRepository).delete(eventTag);
        // Act + Assert
        assertThrows(RuntimeException.class, () -> eventTagService.deleteTagById(eventTagId));
        verify(eventTagRepository, times(1)).findById(eventTagId);
        verify(eventTagRepository, times(1)).delete(eventTag);
    }

    @Test
    void deleteTagById_whenUnknownError_thenThrowRuntimeException() {
        // Arrange
        EventTag eventTag = familyEventTag;
        String eventTagId = UUID.randomUUID().toString();
        eventTag.setId(eventTagId);
        when(eventTagRepository.findById(eventTagId)).thenReturn(Optional.of(eventTag));
        when(eventTagService.deleteTagById(eventTagId)).thenThrow(RuntimeException.class);
        // Act + Assert
        assertThrows(RuntimeException.class, () -> eventTagService.deleteTagById(eventTagId));
        verify(eventTagRepository, times(2)).findById(eventTagId);
        verify(eventTagRepository, times(1)).delete(eventTag);
    }

    @Test
    void updateTag_successfullyUpdated_thenReturnUpdatedEventTag() {
        // Arrange
        EventTag eventTagToUpdate = familyEventTag;
        eventTagToUpdate.setName("grilling for men");
        String eventTagId = UUID.randomUUID().toString();
        eventTagToUpdate.setId(eventTagId);
        when(eventTagRepository.findById(eventTagId)).thenReturn(Optional.of(eventTagToUpdate));
        when(eventTagRepository.save(eventTagToUpdate)).thenReturn(eventTagToUpdate);
        // Act
        EventTag updatedEventTag = eventTagService.updateTag(eventTagId, eventTagToUpdate);
        // Assert
        assertNotNull(updatedEventTag);
        assertEquals(eventTagToUpdate, updatedEventTag);
        verify(eventTagRepository).findById(eventTagId);
        verify(eventTagRepository).save(eventTagToUpdate);
    }

    @Test
    void updateTag_whenEventTagDoesNotExist_thenThrowEntityNotFoundException() {
        // Arrange
        String eventTagId = UUID.randomUUID().toString();
        EventTag eventTagToUpdate = familyEventTag;
        when(eventTagRepository.findById(eventTagId)).thenReturn(Optional.empty());
        // Act + Assert
        assertThrows(EntityNotFoundException.class, () -> eventTagService.updateTag(eventTagId, eventTagToUpdate));
        verify(eventTagRepository).findById(eventTagId);
    }

    @Test
    void updateTag_whenDataAccessException_thenThrowRuntimeException() {
        // Arrange
        EventTag eventTagToUpdate = familyEventTag;
        String eventTagId = UUID.randomUUID().toString();
        eventTagToUpdate.setId(eventTagId);
        when(eventTagRepository.findById(eventTagId)).thenReturn(Optional.of(eventTagToUpdate));
        doThrow(DataAccessResourceFailureException.class).when(eventTagRepository).save(eventTagToUpdate);
        // Act + Assert
        assertThrows(RuntimeException.class, () -> eventTagService.updateTag(eventTagId, eventTagToUpdate));
        verify(eventTagRepository, times(1)).findById(eventTagId);
        verify(eventTagRepository, times(1)).save(eventTagToUpdate);
    }

    @Test
    void updateTag_whenUnknownError_thenThrowRuntimeException() {
        // Arrange
        EventTag eventTagToUpdate = familyEventTag;
        String eventTagId = UUID.randomUUID().toString();
        eventTagToUpdate.setId(eventTagId);
        when(eventTagRepository.findById(eventTagId)).thenReturn(Optional.of(eventTagToUpdate));
        when(eventTagService.updateTag(eventTagId, eventTagToUpdate)).thenThrow(RuntimeException.class);
        // Act + Assert
        assertThrows(RuntimeException.class, () -> eventTagService.updateTag(eventTagId, eventTagToUpdate));
        verify(eventTagRepository, times(2)).findById(eventTagId);
        verify(eventTagRepository, times(1)).save(eventTagToUpdate);
    }

    @Test
    void findTagsByIds_successfullyReturned_thenReturnEventTags() {
        // Arrange
        // Set up the EventTags with IDs
        familyEventTag.setId(UUID.randomUUID().toString());  // Manually set the ID for familyEventTag
        gamesEventTag.setId(UUID.randomUUID().toString());   // Manually set the ID for gamesEventTag

        Set<String> eventTagIds = Set.of(familyEventTag.getId(), gamesEventTag.getId());
        Set<EventTag> eventTags = Set.of(familyEventTag, gamesEventTag);

        // Mock the repository behavior
        when(eventTagRepository.findAllById(eventTagIds)).thenReturn(new ArrayList<>(eventTags));

        // Act
        Set<EventTag> result = eventTagService.findTagsByIds(eventTagIds);

        // Assert
        assertNotNull(result);
        assertEquals(eventTags, result);
        verify(eventTagRepository).findAllById(eventTagIds);
    }

    @Test
    void findTagsByIds_whenSomeTagsAreMissing_thenThrowEntityNotFoundException() {
        // Arrange
        Set<String> eventTagIds = Set.of(familyEventTag.getId(), UUID.randomUUID().toString());
        when(eventTagRepository.findAllById(eventTagIds)).thenReturn(Collections.singletonList(familyEventTag));

        // Act + Assert
        assertThrows(EntityNotFoundException.class, () -> eventTagService.findTagsByIds(eventTagIds));
        verify(eventTagRepository).findAllById(eventTagIds);
    }

    @Test
    void deleteTagById_whenDataAccessException_thenThrowResourceAccessException() {
        // Arrange
        String eventTagId = UUID.randomUUID().toString();
        EventTag eventTag = familyEventTag;
        eventTag.setId(eventTagId);

        when(eventTagRepository.findById(eventTagId)).thenReturn(Optional.of(eventTag));
        doThrow(DataAccessResourceFailureException.class).when(eventTagRepository).delete(eventTag);

        // Act + Assert
        assertThrows(ResourceAccessException.class, () -> eventTagService.deleteTagById(eventTagId));
        verify(eventTagRepository, times(1)).findById(eventTagId);
        verify(eventTagRepository, times(1)).delete(eventTag);
    }

    @Test
    void findTagsByEventId_successfullyReturned_thenReturnEventTags() {
        // Arrange
        String eventId = UUID.randomUUID().toString();
        Set<EventTag> eventTags = Set.of(familyEventTag, gamesEventTag);
        when(eventTagRepository.findTagsByEventId(eventId)).thenReturn(eventTags);

        // Act
        Set<EventTag> result = eventTagService.findTagsByEventId(eventId);

        // Assert
        assertNotNull(result);
        assertEquals(eventTags, result);
        verify(eventTagRepository).findTagsByEventId(eventId);
    }
}