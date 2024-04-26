package at.technikum.parkpalbackend.service;

import at.technikum.parkpalbackend.TestFixtures;
import at.technikum.parkpalbackend.exception.EntityNotFoundException;
import at.technikum.parkpalbackend.model.Picture;
import at.technikum.parkpalbackend.model.User;
import at.technikum.parkpalbackend.persistence.PictureRepository;
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

import static at.technikum.parkpalbackend.TestFixtures.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
class PictureServiceTest {

    @Mock
    private PictureRepository pictureRepository;

    private UserRepository userRepository;

    @Mock
    private UserService userService;

    @InjectMocks
    private PictureService pictureService;

    @Test
    void savePicture_SuccessfullySaved_thenReturnSavedPicture() {
        // Arrange
        Picture picture = TestFixtures.testPicture;
        picture.setId(UUID.randomUUID().toString());
        when(pictureRepository.save(picture)).thenReturn(picture);
        // Act
        Picture result = pictureService.save(picture);
        // Assert
        assertNotNull(result);
        assertEquals(picture, result);
        verify(pictureRepository).save(picture);
    }

    @Test
    void findAllPictures_whenPicturesExist_thenReturnPictures() {
        // Arrange
        Picture picture1 = testPicture;
        Picture picture2 = alternateTestPicture;
        List<Picture> expectedPictures = new ArrayList<>();
        expectedPictures.add(picture1);
        expectedPictures.add(picture2);

        when(pictureRepository.findAll()).thenReturn(expectedPictures);
        // Act
        List<Picture> foundPictures = pictureService.findAllPictures();
        // Assert
        assertNotNull(foundPictures);
        assertEquals(expectedPictures, foundPictures);
        verify(pictureRepository).findAll();
    }


    @Test
    void findAllPictures_whenNoPictureExist_thenReturnEmptyList() {
        // Arrange
        when(pictureRepository.findAll()).thenReturn(new ArrayList<>());
        // Act
        List<Picture> foundPictures = pictureService.findAllPictures();
        // Assert
        assertEquals(0, foundPictures.size());
        verify(pictureRepository).findAll();
    }

    @Test
    void findByPictureId_whenPictureDoesNotExist_thenThrowEntityNotFoundException() {
        // Arrange
        String pictureId = UUID.randomUUID().toString();
        when(pictureRepository.findById(pictureId)).thenReturn(Optional.empty());
        // Act + Assert
        assertThrows(EntityNotFoundException.class, () -> pictureService.findPictureByPictureId(pictureId));
        verify(pictureRepository).findById(pictureId);
    }

    @Test
    void findAllPicturesUploadedByUser_whenUserExists_thenReturnAllPictures() {
        // Arrange
        String userId = UUID.randomUUID().toString();
        User user = TestFixtures.normalUser;
        user.setId(userId);
        // prepare expected events
        Picture picture1 = testPicture;
        Picture picture2 = alternateTestPicture;
        List<Picture> expectedPictures = new ArrayList<>();
        expectedPictures.add(picture1);
        expectedPictures.add(picture2);

        when(pictureRepository.getPicturesByUser(user)).thenReturn(expectedPictures);

        // Act
        List<Picture> foundPictures = pictureService.findPicturesByUser(user);
        // Assert
        assertNotNull(foundPictures);
        assertEquals(2, foundPictures.size());
        assertEquals(expectedPictures, foundPictures);
        verify(pictureRepository).getPicturesByUser(user);
    }

    @Test
    void findAllPicturesUploadedByUser_whenUserDoesNotExist_thenThrowEntityNotFoundException() {
        // Arrange
        String userId = UUID.randomUUID().toString();
        userRepository = mock(UserRepository.class);
        userService = new UserService(userRepository);
        pictureService = new PictureService(pictureRepository);
        // Act + Assert
        assertThrows(EntityNotFoundException.class, () -> pictureService
                .findPicturesByUser(userService.findByUserId(userId)));
    }

    @Test
    void findAllPicturesUploadedByUser_whenUnknownError_thenThrowRuntimeException() {
        // Arrange
        String userId = UUID.randomUUID().toString();
        User user = TestFixtures.normalUser;
        userRepository = mock(UserRepository.class);
        userService = new UserService(userRepository);
        pictureService = new PictureService(pictureRepository);
        when(pictureService.findPicturesByUser(user)).thenThrow(new RuntimeException());
        // Act + Assert
        assertThrows(RuntimeException.class, () -> pictureService.findPicturesByUser(user));
    }

    @Test
    void deletePictureById_whenPictureExists_thenDeletePicture() {
        // Arrange
        String pictureId = UUID.randomUUID().toString();
        Picture pictureToDelete = testPicture;
        when(pictureRepository.findById(pictureId)).thenReturn(Optional.of(pictureToDelete));
        // Act
        Picture deletedPicture = pictureService.deletePictureByPictureId(pictureId);
        // Assert
        assertNotNull(deletedPicture);
        assertEquals(pictureToDelete, deletedPicture);
        verify(pictureRepository, times(1)).delete(pictureToDelete);
    }

    @Test
    void deletePictureById_whenPictureDoesNotExist_thenThrowEntityNotFoundException() {
        // Arrange
        String pictureId = UUID.randomUUID().toString();
        Picture pictureToDelete = testPicture;
        when(pictureRepository.findById(pictureId)).thenReturn(Optional.empty());
        // Act + Assert
        assertThrows(EntityNotFoundException.class, () -> pictureService.deletePictureByPictureId(pictureId));
        verify(pictureRepository, times(0)).delete(pictureToDelete);
    }

    @Test
    void deletePictureById_whenUnknownError_thenThrowRuntimeException() {
        // Arrange
        String pictureId = UUID.randomUUID().toString();
        Picture pictureToDelete = testPicture;
        pictureToDelete.setId(pictureId);
        when(pictureRepository.findById(pictureId)).thenReturn(Optional.of(pictureToDelete));
        when(pictureService.deletePictureByPictureId(pictureId)).thenThrow(new RuntimeException());
        // Act + Assert
        assertThrows(RuntimeException.class, () -> pictureService.deletePictureByPictureId(pictureId));
        verify(pictureRepository, times(1)).delete(pictureToDelete);
    }

    @Test
    void updatePictureById_whenPictureExists_thenUpdatePicture() {
        // Arrange
        // prepare data
        String pictureId = UUID.randomUUID().toString();
        Picture oldPicture = testPicture;
        Picture updatedPicture = testPicture;
        User newUser = adminUser;
        updatedPicture.setId(pictureId);
        updatedPicture.setUploadDate(testPicture.getUploadDate().plusHours(3));
        updatedPicture.setUser(newUser);

        when(pictureRepository.findById(pictureId)).thenReturn(Optional.of(oldPicture));
        when(pictureRepository.save(any(Picture.class))).thenReturn(updatedPicture);
        // Act
        Picture newPicture = pictureService.updatePicture(pictureId, updatedPicture);
        // Assert
        assertNotNull(newPicture);
        assertEquals(updatedPicture, newPicture);
        verify(pictureRepository).save(updatedPicture);
    }

    @Test
    void updatePictureById_whenPictureDoesNotExist_thenThrowEntityNotFoundException() {
        // Arrange
        // prepare data
        String pictureId = UUID.randomUUID().toString();
        Picture updatedPicture = testPicture;

        when(pictureRepository.findById(pictureId)).thenReturn(Optional.empty());
        // Act + Assert
        assertThrows(EntityNotFoundException.class, () -> pictureService
                .updatePicture(pictureId, updatedPicture));
        verify(pictureRepository, times(0)).save(updatedPicture);
    }

    @Test
    void updatePictureById_whenUnknownError_thenThrowRuntimeException() {
        // Arrange
        // prepare data
        String pictureId = UUID.randomUUID().toString();
        Picture oldPicture = testPicture;
        Picture updatedPicture = testPicture;
        User newUser = adminUser;
        updatedPicture.setId(pictureId);
        updatedPicture.setUploadDate(testPicture.getUploadDate().plusHours(3));
        updatedPicture.setUser(newUser);

        when(pictureRepository.findById(pictureId)).thenReturn(Optional.of(oldPicture));
        when(pictureRepository.save(any(Picture.class))).thenThrow(new RuntimeException());
        // Act + Assert
        assertThrows(RuntimeException.class, () -> pictureService.updatePicture(pictureId, updatedPicture));
        verify(pictureRepository, times(1)).save(updatedPicture);
    }
}