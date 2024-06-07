package at.technikum.parkpalbackend.mapper;

import at.technikum.parkpalbackend.TestFixtures;
import at.technikum.parkpalbackend.dto.VideoDto;
import at.technikum.parkpalbackend.model.Video;
import at.technikum.parkpalbackend.service.UploadService;
import at.technikum.parkpalbackend.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


//@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class VideoMapperTest {

    @Mock
    private UserService userService;

    @Mock
    private UploadService uploadService;

    @Mock
    private MultipartFile file;

    @InjectMocks
    private VideoMapper videoMapper;

    @Test
    public void whenEntity_thenToDto() {
        // Arrange
        Video video = TestFixtures.testVideo;
        video.setId(UUID.randomUUID().toString());

        // Act
        VideoDto videoDto = videoMapper.toDto(video);

        // Assert
        Assertions.assertEquals(video.getId(), videoDto.getId());
        Assertions.assertEquals(video.getUser().getId(), videoDto.getUserId());
        Assertions.assertEquals(video.getUploadDate(), videoDto.getUploadDate());

    }


    @Test
    public void whenEntityNull_toDto_thenThrowIllegalArgumentException() {
        // Arrange
        Video video = null;

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> videoMapper.toDto(video));
    }


    @Test
    public void whenEntityIncomplete_toDto_thenThrowIllegalArgumentException() {
        // Arrange
        Video video = Video.builder().build();
        video.setId(UUID.randomUUID().toString()); // Picture object without ID and iso2Code

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> videoMapper.toDto(video));
    }



    @Test
    public void whenDTO_thenToEntity() {
        // Arrange
        VideoDto videoDto = TestFixtures.testVideoDto;
        videoDto.setId(UUID.randomUUID().toString());

        // Act
        Video video = videoMapper.toEntity(videoDto);


        // Assert
        Assertions.assertEquals(videoDto.getId(), video.getId());
        Assertions.assertEquals(userService.findByUserId(videoDto.getUserId()), video.getUser());
        Assertions.assertEquals(videoDto.getUploadDate(), video.getUploadDate());
    }
    @Test
    public void whenDTONull_toEntity_thenThrowIllegalArgumentException() {
        // Arrange
        VideoDto videoDto = null;

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> videoMapper.toEntity(videoDto));
    }


    @Test
    public void whenDTOIncomplete_toEntity_thenThrowIllegalArgumentException() {
        // Arrange
        VideoDto videoDto = VideoDto.builder().build();
        videoDto.setId(UUID.randomUUID().toString()); // PictureDto object without ID and iso2Code


        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> videoMapper.toEntity(videoDto));
    }

    @Test
    public void whenMultipleFilePart_toEntity() throws IOException {
        //Arrange
        MultipartFile file = mock(MultipartFile.class);
        byte[] fileBytes = {1, 2, 3, 4, 5};

        //Act
        when(uploadService.transferToBytes(any(MultipartFile.class))).thenReturn(fileBytes);
        Video video = videoMapper.fromMultipartFileToEntity(file);
        //Assert
        Assertions.assertNotNull(video);
        Assertions.assertArrayEquals(fileBytes, video.getFile());
    }

}
