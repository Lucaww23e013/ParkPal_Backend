package at.technikum.parkpalbackend.service;

import at.technikum.parkpalbackend.TestFixtures;
import at.technikum.parkpalbackend.exception.EntityNotFoundException;
import at.technikum.parkpalbackend.model.File;
import at.technikum.parkpalbackend.model.User;
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

import static at.technikum.parkpalbackend.TestFixtures.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
class FileServiceTest {

    @Mock
    private FileRepository fileRepository;

    private UserRepository userRepository;

    @Mock
    private UserService userService;

    @InjectMocks
    private FileService fileService;

    @Test
    void savePicture_SuccessfullySaved_thenReturnSavedPicture() {
        // Arrange
        File file = TestFixtures.testFileTypeFile;
        file.setId(UUID.randomUUID().toString());
        when(fileRepository.save(file)).thenReturn(file);
        // Act
        File result = fileService.save(file);
        // Assert
        assertNotNull(result);
        assertEquals(file, result);
        verify(fileRepository).save(file);
    }

    @Test
    void findAllPictures_whenPicturesExist_thenReturnPictures() {
        // Arrange
        File file1 = testFileTypeFile;
        File file2 = alternateTestFile;
        List<File> expectedFiles = new ArrayList<>();
        expectedFiles.add(file1);
        expectedFiles.add(file2);

        when(fileRepository.findAll()).thenReturn(expectedFiles);
        // Act
        List<File> foundFiles = fileService.findAllPictures();
        // Assert
        assertNotNull(foundFiles);
        assertEquals(expectedFiles, foundFiles);
        verify(fileRepository).findAll();
    }


    @Test
    void findAllPictures_whenNoPictureExist_thenReturnEmptyList() {
        // Arrange
        when(fileRepository.findAll()).thenReturn(new ArrayList<>());
        // Act
        List<File> foundFiles = fileService.findAllPictures();
        // Assert
        assertEquals(0, foundFiles.size());
        verify(fileRepository).findAll();
    }

    @Test
    void findByPictureId_whenPictureDoesNotExist_thenThrowEntityNotFoundException() {
        // Arrange
        String pictureId = UUID.randomUUID().toString();
        when(fileRepository.findById(pictureId)).thenReturn(Optional.empty());
        // Act + Assert
        assertThrows(EntityNotFoundException.class, () -> fileService.findPictureByPictureId(pictureId));
        verify(fileRepository).findById(pictureId);
    }

    @Test
    void findAllPicturesUploadedByUser_whenUserExists_thenReturnAllPictures() {
        // Arrange
        String userId = UUID.randomUUID().toString();
        User user = TestFixtures.normalUser;
        user.setId(userId);
        // prepare expected events
        File file1 = testFileTypeFile;
        File file2 = alternateTestFile;
        List<File> expectedFiles = new ArrayList<>();
        expectedFiles.add(file1);
        expectedFiles.add(file2);

        when(fileRepository.getPicturesByUser(user)).thenReturn(expectedFiles);

        // Act
        List<File> foundFiles = fileService.findPicturesByUser(user);
        // Assert
        assertNotNull(foundFiles);
        assertEquals(2, foundFiles.size());
        assertEquals(expectedFiles, foundFiles);
        verify(fileRepository).getPicturesByUser(user);
    }

    @Test
    void findAllPicturesUploadedByUser_whenUserDoesNotExist_thenThrowEntityNotFoundException() {
        // Arrange
        String userId = UUID.randomUUID().toString();
        userRepository = mock(UserRepository.class);
        userService = new UserService(userRepository);
        fileService = new FileService(fileRepository);
        // Act + Assert
        assertThrows(EntityNotFoundException.class, () -> fileService
                .findPicturesByUser(userService.findByUserId(userId)));
    }

    @Test
    void findAllPicturesUploadedByUser_whenUnknownError_thenThrowRuntimeException() {
        // Arrange
        String userId = UUID.randomUUID().toString();
        User user = TestFixtures.normalUser;
        userRepository = mock(UserRepository.class);
        userService = new UserService(userRepository);
        fileService = new FileService(fileRepository);
        when(fileService.findPicturesByUser(user)).thenThrow(new RuntimeException());
        // Act + Assert
        assertThrows(RuntimeException.class, () -> fileService.findPicturesByUser(user));
    }

    @Test
    void deletePictureById_whenPictureExists_thenDeletePicture() {
        // Arrange
        String pictureId = UUID.randomUUID().toString();
        File fileToDelete = testFileTypeFile;
        when(fileRepository.findById(pictureId)).thenReturn(Optional.of(fileToDelete));
        // Act
        File deletedFile = fileService.deletePictureByPictureId(pictureId);
        // Assert
        assertNotNull(deletedFile);
        assertEquals(fileToDelete, deletedFile);
        verify(fileRepository, times(1)).delete(fileToDelete);
    }

    @Test
    void deletePictureById_whenPictureDoesNotExist_thenThrowEntityNotFoundException() {
        // Arrange
        String pictureId = UUID.randomUUID().toString();
        File fileToDelete = testFileTypeFile;
        when(fileRepository.findById(pictureId)).thenReturn(Optional.empty());
        // Act + Assert
        assertThrows(EntityNotFoundException.class, () -> fileService.deletePictureByPictureId(pictureId));
        verify(fileRepository, times(0)).delete(fileToDelete);
    }

    @Test
    void deletePictureById_whenUnknownError_thenThrowRuntimeException() {
        // Arrange
        String pictureId = UUID.randomUUID().toString();
        File fileToDelete = testFileTypeFile;
        fileToDelete.setId(pictureId);
        when(fileRepository.findById(pictureId)).thenReturn(Optional.of(fileToDelete));
        when(fileService.deletePictureByPictureId(pictureId)).thenThrow(new RuntimeException());
        // Act + Assert
        assertThrows(RuntimeException.class, () -> fileService.deletePictureByPictureId(pictureId));
        verify(fileRepository, times(1)).delete(fileToDelete);
    }

    @Test
    void updatePictureById_whenPictureExists_thenUpdatePicture() {
        // Arrange
        // prepare data
        String pictureId = UUID.randomUUID().toString();
        File oldFile = testFileTypeFile;
        File updatedFile = testFileTypeFile;
        User newUser = adminUser;
        updatedFile.setId(pictureId);
        updatedFile.setUploadDate(testFileTypeFile.getUploadDate().plusHours(3));
        updatedFile.setUser(newUser);

        when(fileRepository.findById(pictureId)).thenReturn(Optional.of(oldFile));
        when(fileRepository.save(any(File.class))).thenReturn(updatedFile);
        // Act
        File newFile = fileService.updatePicture(pictureId, updatedFile);
        // Assert
        assertNotNull(newFile);
        assertEquals(updatedFile, newFile);
        verify(fileRepository).save(updatedFile);
    }

    @Test
    void updatePictureById_whenPictureDoesNotExist_thenThrowEntityNotFoundException() {
        // Arrange
        // prepare data
        String pictureId = UUID.randomUUID().toString();
        File updatedFile = testFileTypeFile;

        when(fileRepository.findById(pictureId)).thenReturn(Optional.empty());
        // Act + Assert
        assertThrows(EntityNotFoundException.class, () -> fileService
                .updatePicture(pictureId, updatedFile));
        verify(fileRepository, times(0)).save(updatedFile);
    }

    @Test
    void updatePictureById_whenUnknownError_thenThrowRuntimeException() {
        // Arrange
        // prepare data
        String pictureId = UUID.randomUUID().toString();
        File oldFile = testFileTypeFile;
        File updatedFile = testFileTypeFile;
        User newUser = adminUser;
        updatedFile.setId(pictureId);
        updatedFile.setUploadDate(testFileTypeFile.getUploadDate().plusHours(3));
        updatedFile.setUser(newUser);

        when(fileRepository.findById(pictureId)).thenReturn(Optional.of(oldFile));
        when(fileRepository.save(any(File.class))).thenThrow(new RuntimeException());
        // Act + Assert
        assertThrows(RuntimeException.class, () -> fileService.updatePicture(pictureId, updatedFile));
        verify(fileRepository, times(1)).save(updatedFile);
    }
}