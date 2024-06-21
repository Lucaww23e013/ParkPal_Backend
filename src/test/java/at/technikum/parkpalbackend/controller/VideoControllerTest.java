package at.technikum.parkpalbackend.controller;

import at.technikum.parkpalbackend.TestFixtures;
import at.technikum.parkpalbackend.dto.VideoDto;
import at.technikum.parkpalbackend.mapper.VideoMapper;
import at.technikum.parkpalbackend.model.User;
import at.technikum.parkpalbackend.model.Video;
import at.technikum.parkpalbackend.security.filter.JwtAuthenticationFilter;
import at.technikum.parkpalbackend.security.jwt.JwtDecoder;
import at.technikum.parkpalbackend.service.UserService;
import at.technikum.parkpalbackend.service.VideoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(VideoController.class)
@AutoConfigureMockMvc(addFilters = false)
public class VideoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private VideoService videoService;

    @MockBean
    private UserService userService;

    @MockBean
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @MockBean
    private JwtDecoder jwtDecoder;

    @MockBean
    private ResponseEntity<String> uploadResponse;

    @MockBean
    private VideoMapper videoMapper;

    @MockBean
    private UploadController uploadController;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testCreateVideo() throws Exception {
        // Arrange
        VideoDto videoDto = TestFixtures.testVideoDto;
        videoDto.setId("1");

        Video createdVideo = TestFixtures.testVideo;
        createdVideo.setId("1");

        // Mock the behavior of uploadController.fileUpload
        ResponseEntity<String> successResponse = ResponseEntity.status(HttpStatus.OK).body("Upload successful");
        when(uploadController.fileUpload(any(MultipartFile.class))).thenReturn(successResponse);

        when(videoMapper.toEntity(any(VideoDto.class))).thenReturn(createdVideo);
        when(videoService.save(any(Video.class))).thenReturn(createdVideo);
        when(videoMapper.toDto(any(Video.class))).thenReturn(videoDto);

        // Act & Assert
        mockMvc.perform(multipart("/upload")
                        .file("file", "content".getBytes())
                        .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().isOk()); // Assuming upload endpoint returns HttpStatus.OK
    }

    @Test
    public void testCreateVideo_FileUploadFailure() throws Exception {
        // Arrange
        VideoDto videoDto = TestFixtures.testVideoDto;

        // Mock the behavior of uploadController.fileUpload to simulate failure
        ResponseEntity<String> failureResponse = ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE).body("Unsupported media type");
        when(uploadController.fileUpload(any(MockMultipartFile.class))).thenReturn(failureResponse);

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.multipart("/videos")
                        .file(new MockMultipartFile("file", "test-video.mp4", MediaType.MULTIPART_FORM_DATA_VALUE, "content".getBytes()))
                        .contentType(MediaType.MULTIPART_FORM_DATA)
                        .content(objectMapper.writeValueAsString(videoDto)))
                .andExpect(status().isUnsupportedMediaType());
    }

    @Test
    public void testGetAllVideos() throws Exception {
        // Arrange
        List<Video> mockVideos = Collections.singletonList(Video.builder().build());
        List<VideoDto> mockVideoDtos = Collections.singletonList(TestFixtures.testVideoDto);

        when(videoService.findAllVideos()).thenReturn(mockVideos);
        when(videoMapper.toDto(any(Video.class))).thenReturn(TestFixtures.testVideoDto);

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/videos")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].id").value(TestFixtures.testVideoDto.getId()));
    }

@Test
    public void testGetVideoByVideoId() throws Exception {
        // Arrange
        String videoId = "1";
        VideoDto videoDto = TestFixtures.testVideoDto;
        videoDto.setId(videoId);

        when(videoService.findVideoByVideoId(ArgumentMatchers.eq(videoId))).thenReturn(Video.builder().build());
        when(videoMapper.toDto(any(Video.class))).thenReturn(videoDto);

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/videos/{videoId}", videoId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void testGetVideoByUserId() throws Exception {
        // Arrange
        String userId = "user1";
        User user = TestFixtures.adminUser;
        user.setId(userId);

        VideoDto videoDto = TestFixtures.testVideoDto;
        videoDto.setId("1");

        List<Video> mockVideos = Collections.singletonList(Video.builder().build());

        when(userService.findByUserId(ArgumentMatchers.eq(userId))).thenReturn(user);
        when(videoService.findVideosByUser(ArgumentMatchers.eq(user))).thenReturn(mockVideos);
        when(videoMapper.toDto(any(Video.class))).thenReturn(videoDto);

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/videos/user/{userId}", userId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void testUpdateVideo() throws Exception {
        // Arrange
        String videoId = "1";
        VideoDto updatedVideoDto = TestFixtures.testVideoDto;
        updatedVideoDto.setId(videoId);
        updatedVideoDto.setUploadDate(LocalDateTime.now());
        updatedVideoDto.setUserId(TestFixtures.normalUser.getId());

        Video updatedVideo = TestFixtures.testVideo;
        updatedVideo.setId(videoId);
        updatedVideo.setUploadDate(LocalDateTime.now());
        updatedVideo.setId(TestFixtures.normalUser.getId());

        when(videoService.updateVideo(ArgumentMatchers.eq(videoId), any(Video.class))).thenReturn(updatedVideo);
        when(videoMapper.toEntity(any(VideoDto.class))).thenReturn(updatedVideo);
        when(videoMapper.toDto(any(Video.class))).thenReturn(updatedVideoDto);

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.patch("/videos/{videoId}", videoId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedVideoDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId").value(TestFixtures.normalUser.getId()));
    }

    @Test
    public void testDeleteVideoByVideoId() throws Exception {
        // Arrange
        String videoId = "1";

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.delete("/videos/{videoId}", videoId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        Mockito.verify(videoService, Mockito.times(1)).deleteVideoByVideoId(ArgumentMatchers.eq(videoId));
    }
}
