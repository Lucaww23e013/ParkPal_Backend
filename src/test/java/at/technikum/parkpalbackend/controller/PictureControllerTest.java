package at.technikum.parkpalbackend.controller;

import at.technikum.parkpalbackend.TestFixtures;
import at.technikum.parkpalbackend.dto.PictureDto;
import at.technikum.parkpalbackend.mapper.PictureMapper;
import at.technikum.parkpalbackend.model.Picture;
import at.technikum.parkpalbackend.model.User;
import at.technikum.parkpalbackend.security.filter.JwtAuthenticationFilter;
import at.technikum.parkpalbackend.security.jwt.JwtDecoder;
import at.technikum.parkpalbackend.service.PictureService;
import at.technikum.parkpalbackend.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PictureController.class)
@AutoConfigureMockMvc(addFilters = false)
public class PictureControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PictureService pictureService;

    @MockBean
    private UserService userService;

    @MockBean
    private PictureMapper pictureMapper;

    @MockBean
    private JwtDecoder jwtDecoder;

    @MockBean
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testCreatePicture() throws Exception {
        // Arrange
        PictureDto pictureDto = TestFixtures.testPictureDto;
        pictureDto.setId("1");

        Picture createdPicture = TestFixtures.testPicture;
        createdPicture.setId("1");

        when(pictureMapper.toEntity(any(PictureDto.class))).thenReturn(createdPicture);
        when(pictureService.save(any(Picture.class))).thenReturn(createdPicture);
        when(pictureMapper.toDto(any(Picture.class))).thenReturn(pictureDto);

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.post("/pictures")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(pictureDto)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value("1")); // Adjust as per your DTO structure
    }

    @Test
    public void testGetAllPictures() throws Exception {
        // Arrange
        List<Picture> mockPictures = Collections.singletonList(Picture.builder().build());
        List<PictureDto> mockPictureDtos = Collections.singletonList(TestFixtures.testPictureDto);

        when(pictureService.findAllPictures()).thenReturn(mockPictures);
        when(pictureMapper.toDto(any(Picture.class))).thenReturn(TestFixtures.testPictureDto);

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/pictures")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].id").value(TestFixtures.testPictureDto.getId()));
    }

    @Test
    public void testGetPictureByPictureId() throws Exception {
        // Arrange
        String pictureId = "1";
        PictureDto pictureDto = TestFixtures.testPictureDto;
        pictureDto.setId(pictureId);

        when(pictureService.findPictureByPictureId(ArgumentMatchers.eq(pictureId))).thenReturn(TestFixtures.testPicture);
        when(pictureMapper.toDto(any(Picture.class))).thenReturn(pictureDto);

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/pictures/{pictureId}", pictureId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(pictureId));
    }

    @Test
    public void testGetPictureByUserId() throws Exception {
        // Arrange
        String userId = "user1";
        User user = TestFixtures.adminUser;
        user.setId(userId);

        PictureDto pictureDto = TestFixtures.testPictureDto;
        pictureDto.setId("1");

        List<Picture> mockPictures = Collections.singletonList(Picture.builder().build());

        when(userService.findByUserId(ArgumentMatchers.eq(userId))).thenReturn(user);
        when(pictureService.findPicturesByUser(ArgumentMatchers.eq(user))).thenReturn(mockPictures);
        when(pictureMapper.toDto(any(Picture.class))).thenReturn(pictureDto);

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/pictures/user/{userId}", userId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void testUpdatePicture() throws Exception {
        // Arrange
        String pictureId = "1";
        PictureDto updatedPictureDto = TestFixtures.testPictureDto;
        updatedPictureDto.setId(pictureId);

        Picture updatedPicture = TestFixtures.testPicture;
        updatedPicture.setId(pictureId);

        when(pictureService.updatePicture(ArgumentMatchers.eq(pictureId), any(Picture.class))).thenReturn(updatedPicture);
        when(pictureMapper.toEntity(any(PictureDto.class))).thenReturn(updatedPicture);
        when(pictureMapper.toDto(any(Picture.class))).thenReturn(updatedPictureDto);

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.patch("/pictures/{pictureId}", pictureId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedPictureDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(pictureId));
    }

    @Test
    public void testDeletePictureByPictureId() throws Exception {
        // Arrange
        String pictureId = "1";

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.delete("/pictures/{pictureId}", pictureId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        Mockito.verify(pictureService, Mockito.times(1)).deletePictureByPictureId(ArgumentMatchers.eq(pictureId));
    }
}
