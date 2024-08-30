package at.technikum.parkpalbackend.mapper;
//
//import at.technikum.parkpalbackend.TestFixtures;
//import at.technikum.parkpalbackend.dto.FileDto;
//import at.technikum.parkpalbackend.model.File;
//import at.technikum.parkpalbackend.service.UserService;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.IOException;
//import java.util.UUID;
//
//import static org.junit.Assert.*;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.mock;
//import static org.mockito.Mockito.when;
//
//
////@SpringBootTest
//@ExtendWith(MockitoExtension.class)
//public class FileMapperTest {
//
//
//    @Mock
//    private UserService userService;
//
//    @Mock
//    private UploadService uploadService;
//
//    @Mock
//    private MultipartFile file;
//
//    @InjectMocks
//    private FileMapper fileMapper;
//
//    @Test
//    public void whenEntity_thenToDto() {
//        // Arrange
//        File file = TestFixtures.testFileTypeFile;
//        file.setId(UUID.randomUUID().toString());
//
//        // Act
//        FileDto fileDto = fileMapper.toDto(file);
//
//        // Assert
//        assertEquals(file.getId(), fileDto.getId());
//        assertEquals(file.getUser().getId(), fileDto.getUserId());
//        assertEquals(file.getUploadDate(), fileDto.getUploadDate());
//
//    }
//
//
//    @Test
//    public void whenEntityNull_toDto_thenThrowIllegalArgumentException() {
//        // Arrange
//        File file = null;
//
//        // Act & Assert
//        assertThrows(IllegalArgumentException.class, () -> fileMapper.toDto(file));
//    }
//
//
//
//    @Test
//    public void whenDTO_thenToEntity() {
//        // Arrange
//        FileDto fileDto = TestFixtures.testFileDto;
//        fileDto.setId(UUID.randomUUID().toString());
//
//        // Act
//        File file = fileMapper.toEntity(fileDto);
//
//
//        // Assert
//        assertEquals(fileDto.getId(), file.getId());
//        assertEquals(userService.findByUserId(fileDto.getUserId()), file.getUser());
//        assertEquals(fileDto.getUploadDate(), file.getUploadDate());
//    }
//    @Test
//    public void whenDTONull_toEntity_thenThrowIllegalArgumentException() {
//        // Arrange
//        FileDto fileDto = null;
//
//
//        // Act & Assert
//        assertThrows(IllegalArgumentException.class, () -> fileMapper.toEntity(fileDto));
//    }
//
//
//
//
//    @Test
//    public void whenMultipleFilePart_toEntity() throws IOException {
//        //Arrange
//        MultipartFile file = mock(MultipartFile.class);
//        byte[] fileBytes = {1, 2, 3, 4, 5};
//
//        //Act
//        when(uploadService.transferToBytes(any(MultipartFile.class))).thenReturn(fileBytes);
//        File picture = fileMapper.fromMultipartFileToEntity(file);
//        //Assert
//        Assertions.assertNotNull(picture);
//        Assertions.assertArrayEquals(fileBytes, picture.getFile());
//    }
//
//}
