package at.technikum.parkpalbackend.mapper;

import at.technikum.parkpalbackend.TestFixtures;
import at.technikum.parkpalbackend.dto.VideoDto;
import at.technikum.parkpalbackend.model.Video;
import at.technikum.parkpalbackend.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;


//@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class VideoMapperTest {

    @Mock
    private UserService userService;

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
        assertEquals(video.getId(), videoDto.getId());
        assertEquals(video.getUser().getId(), videoDto.getUserId());
        assertEquals(video.getUploadDate(), videoDto.getUploadDate());

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
        video.setId(UUID.randomUUID().toString()); // Country object without ID and iso2Code

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
        assertEquals(videoDto.getId(), video.getId());
        assertEquals(userService.findByUserId(videoDto.getUserId()), video.getUser());
        assertEquals(videoDto.getUploadDate(), video.getUploadDate());
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
        videoDto.setId(UUID.randomUUID().toString()); // CountryDTOs object without ID and iso2Code

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> videoMapper.toEntity(videoDto));
    }


}
