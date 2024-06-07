package at.technikum.parkpalbackend.mapper;

import at.technikum.parkpalbackend.TestFixtures;
import at.technikum.parkpalbackend.dto.PictureDto;
import at.technikum.parkpalbackend.model.Picture;
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
public class PictureMapperTest {


    @Mock
    private UserService userService;

    @Mock
    private UploadService uploadService;

    @Mock
    private MultipartFile file;

    @InjectMocks
    private PictureMapper pictureMapper;

    @Test
    public void whenEntity_thenToDto() {
        // Arrange
        Picture picture = TestFixtures.testPicture;
        picture.setId(UUID.randomUUID().toString());

        // Act
        PictureDto pictureDto = pictureMapper.toDto(picture);

        // Assert
        assertEquals(picture.getId(), pictureDto.getId());
        assertEquals(picture.getUser().getId(), pictureDto.getUserId());
        assertEquals(picture.getUploadDate(), pictureDto.getUploadDate());

    }


    @Test
    public void whenEntityNull_toDto_thenThrowIllegalArgumentException() {
        // Arrange
        Picture picture = null;

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> pictureMapper.toDto(picture));
    }


    @Test
    public void whenEntityIncomplete_toDto_thenThrowIllegalArgumentException() {
        // Arrange
        Picture picture = Picture.builder().build();
        picture.setId(UUID.randomUUID().toString()); // Picture object without ID and iso2Code

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> pictureMapper.toDto(picture));
    }




    @Test
    public void whenDTO_thenToEntity() {
        // Arrange
        PictureDto pictureDto = TestFixtures.testPictureDto;
        pictureDto.setId(UUID.randomUUID().toString());

        // Act
        Picture picture = pictureMapper.toEntity(pictureDto);


        // Assert
        assertEquals(pictureDto.getId(), picture.getId());
        assertEquals(userService.findByUserId(pictureDto.getUserId()), picture.getUser());
        assertEquals(pictureDto.getUploadDate(), picture.getUploadDate());
    }
    @Test
    public void whenDTONull_toEntity_thenThrowIllegalArgumentException() {
        // Arrange
        PictureDto pictureDto = null;


        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> pictureMapper.toEntity(pictureDto));
    }


    @Test
    public void whenDTOIncomplete_toEntity_thenThrowIllegalArgumentException() {
        // Arrange
        PictureDto pictureDto = PictureDto.builder().build();
        pictureDto.setId(UUID.randomUUID().toString()); // PictureDto object without ID and iso2Code


        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> pictureMapper.toEntity(pictureDto));
    }

    @Test
    public void whenMultipleFilePart_toEntity() throws IOException {
        //Arrange
        MultipartFile file = mock(MultipartFile.class);
        byte[] fileBytes = {1, 2, 3, 4, 5};

        //Act
        when(uploadService.transferToBytes(any(MultipartFile.class))).thenReturn(fileBytes);
        Picture picture = pictureMapper.fromMultipartFileToEntity(file);
        //Assert
        Assertions.assertNotNull(picture);
        Assertions.assertArrayEquals(fileBytes, picture.getFile());
    }

}
