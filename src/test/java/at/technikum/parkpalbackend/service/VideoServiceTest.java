package at.technikum.parkpalbackend.service;

import at.technikum.parkpalbackend.TestFixtures;
import at.technikum.parkpalbackend.exception.EntityNotFoundException;
import at.technikum.parkpalbackend.model.User;
import at.technikum.parkpalbackend.model.Video;
import at.technikum.parkpalbackend.persistence.UserRepository;
import at.technikum.parkpalbackend.persistence.VideoRepository;
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
import static at.technikum.parkpalbackend.TestFixtures.testPicture;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
class VideoServiceTest {

    @Mock
    private VideoRepository videoRepository;

    private UserRepository userRepository;

    @Mock
    private UserService userService;

    @InjectMocks
    private VideoService videoService;

    @Test
    void saveVideo_SuccessfullySaved_thenReturnSavedVideo() {
        // Arrange
        Video video = testVideo;
        video.setId(UUID.randomUUID().toString());
        when(videoRepository.save(video)).thenReturn(video);
        // Act
        Video result = videoService.save(video);
        // Assert
        assertNotNull(result);
        assertEquals(video, result);
        verify(videoRepository).save(video);
    }

    @Test
    void findAllVideos_whenVideosExist_thenReturnVideos() {
        // Arrange
        Video video1 = testVideo;
        Video video2 = alternateTestVideo;
        List<Video> expectedVideos = new ArrayList<>();
        expectedVideos.add(video1);
        expectedVideos.add(video2);

        when(videoRepository.findAll()).thenReturn(expectedVideos);
        // Act
        List<Video> foundVideos = videoService.findAllVideos();
        // Assert
        assertNotNull(foundVideos);
        assertEquals(expectedVideos, foundVideos);
        verify(videoRepository).findAll();
    }


    @Test
    void findAllVideos_whenNoVideoExist_thenReturnEmptyList() {
        // Arrange
        when(videoRepository.findAll()).thenReturn(new ArrayList<>());
        // Act
        List<Video> foundVideos = videoService.findAllVideos();
        // Assert
        assertEquals(0, foundVideos.size());
        verify(videoRepository).findAll();
    }

    @Test
    void findByVideoId_whenVideoDoesNotExist_thenThrowEntityNotFoundException() {
        // Arrange
        String videoId = UUID.randomUUID().toString();
        when(videoRepository.findById(videoId)).thenReturn(Optional.empty());
        // Act + Assert
        assertThrows(EntityNotFoundException.class, () -> videoService.findVideoByVideoId(videoId));
        verify(videoRepository).findById(videoId);
    }

    @Test
    void findAllVideosUploadedByUser_whenUserExists_thenReturnAllVideos() {
        // Arrange
        String userId = UUID.randomUUID().toString();
        User user = TestFixtures.normalUser;
        user.setId(userId);
        // prepare expected events
        Video video1 = testVideo;
        Video video2 = alternateTestVideo;
        List<Video> expectedVideos = new ArrayList<>();
        expectedVideos.add(video1);
        expectedVideos.add(video2);

        when(videoRepository.findVideosByUser(user)).thenReturn(expectedVideos);

        // Act
        List<Video> foundVideos = videoService.findVideosByUser(user);
        // Assert
        assertNotNull(foundVideos);
        assertEquals(2, foundVideos.size());
        assertEquals(expectedVideos, foundVideos);
        verify(videoRepository).findVideosByUser(user);
    }

    @Test
    void findAllVideosUploadedByUser_whenUserDoesNotExist_thenThrowEntityNotFoundException() {
        // Arrange
        String userId = UUID.randomUUID().toString();
        userRepository = mock(UserRepository.class);
        userService = new UserService(userRepository);
        videoService = new VideoService(videoRepository);
        // Act + Assert
        assertThrows(EntityNotFoundException.class, () -> videoService
                .findVideosByUser(userService.findByUserId(userId)));
    }

    @Test
    void findAllVideosUploadedByUser_whenUnknownError_thenThrowRuntimeException() {
        // Arrange
        String userId = UUID.randomUUID().toString();
        User user = TestFixtures.normalUser;
        userRepository = mock(UserRepository.class);
        userService = new UserService(userRepository);
        videoService = new VideoService(videoRepository);
        when(videoService.findVideosByUser(user)).thenThrow(new RuntimeException());
        // Act + Assert
        assertThrows(RuntimeException.class, () -> videoService.findVideosByUser(user));
    }

    @Test
    void deleteVideoById_whenVideoExists_thenDeleteVideo() {
        // Arrange
        String videoId = UUID.randomUUID().toString();
        Video videoToDelete = testVideo;
        when(videoRepository.findById(videoId)).thenReturn(Optional.of(videoToDelete));
        // Act
        Video deletedVideo = videoService.deleteVideoByVideoId(videoId);
        // Assert
        assertNotNull(deletedVideo);
        assertEquals(videoToDelete, deletedVideo);
        verify(videoRepository, times(1)).delete(videoToDelete);
    }

    @Test
    void deleteVideoById_whenVideoDoesNotExist_thenThrowEntityNotFoundException() {
        // Arrange
        String videoId = UUID.randomUUID().toString();
        Video videoToDelete = testVideo;
        when(videoRepository.findById(videoId)).thenReturn(Optional.empty());
        // Act + Assert
        assertThrows(EntityNotFoundException.class, () -> videoService.deleteVideoByVideoId(videoId));
        verify(videoRepository, times(0)).delete(videoToDelete);
    }

    @Test
    void deleteVideoById_whenUnknownError_thenThrowRuntimeException() {
        // Arrange
        String videoId = UUID.randomUUID().toString();
        Video videoToDelete = testVideo;
        videoToDelete.setId(videoId);
        when(videoRepository.findById(videoId)).thenReturn(Optional.of(videoToDelete));
        when(videoService.deleteVideoByVideoId(videoId)).thenThrow(new RuntimeException());
        // Act + Assert
        assertThrows(RuntimeException.class, () -> videoService.deleteVideoByVideoId(videoId));
        verify(videoRepository, times(1)).delete(videoToDelete);
    }

    @Test
    void updateVideoById_whenVideoExists_thenUpdateVideo() {
        // Arrange
        // prepare data
        String videoId = UUID.randomUUID().toString();
        Video oldVideo = testVideo;
        Video updatedVideo = testVideo;
        User newUser = adminUser;
        updatedVideo.setId(videoId);
        updatedVideo.setUploadDate(testVideo.getUploadDate().plusHours(3));
        updatedVideo.setUser(newUser);

        when(videoRepository.findById(videoId)).thenReturn(Optional.of(oldVideo));
        when(videoRepository.save(any(Video.class))).thenReturn(updatedVideo);
        // Act
        Video newVideo = videoService.updateVideo(videoId, updatedVideo);
        // Assert
        assertNotNull(newVideo);
        assertEquals(updatedVideo, newVideo);
        verify(videoRepository).save(updatedVideo);
    }

    @Test
    void updateVideoById_whenVideoDoesNotExist_thenThrowEntityNotFoundException() {
        // Arrange
        // prepare data
        String videoId = UUID.randomUUID().toString();
        Video updatedVideo = testVideo;

        when(videoRepository.findById(videoId)).thenReturn(Optional.empty());
        // Act + Assert
        assertThrows(EntityNotFoundException.class, () -> videoService
                .updateVideo(videoId, updatedVideo));
        verify(videoRepository, times(0)).save(updatedVideo);
    }

    @Test
    void updateVideoById_whenUnknownError_thenThrowRuntimeException() {
        // Arrange
        // prepare data
        String videoId = UUID.randomUUID().toString();
        Video oldVideo = testVideo;
        Video updatedVideo = testVideo;
        User newUser = adminUser;
        updatedVideo.setId(videoId);
        updatedVideo.setUploadDate(testPicture.getUploadDate().plusHours(3));
        updatedVideo.setUser(newUser);

        when(videoRepository.findById(videoId)).thenReturn(Optional.of(oldVideo));
        when(videoRepository.save(any(Video.class))).thenThrow(new RuntimeException());
        // Act + Assert
        assertThrows(RuntimeException.class, () -> videoService.updateVideo(videoId, updatedVideo));
        verify(videoRepository, times(1)).save(updatedVideo);
    }
}