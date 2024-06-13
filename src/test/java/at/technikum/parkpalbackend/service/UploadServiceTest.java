package at.technikum.parkpalbackend.service;

import at.technikum.parkpalbackend.mapper.PictureMapper;
import at.technikum.parkpalbackend.mapper.VideoMapper;
import at.technikum.parkpalbackend.model.Picture;
import at.technikum.parkpalbackend.model.Video;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UploadServiceTest {

    @Mock
    private PictureService pictureService;

    @Mock
    private VideoService videoService;

    @Mock
    private PictureMapper pictureMapper;

    @Mock
    private VideoMapper videoMapper;

    @Mock
    private MultipartFile mockFile;

    @InjectMocks
    private UploadService uploadService;

    private static final List<String> ALLOWED_PICTURE_TYPES = Arrays.asList("jpg", "jpeg", "png", "gif");
    private static final List<String> ALLOWED_VIDEO_TYPES = Arrays.asList("mp4", "avi", "mov");

    @Test
    void testProcessAndSaveFile_ValidPictureFile_Success() throws IOException {
        // Arrange
        MultipartFile file = new MockMultipartFile(
                "file", "test.jpg", "image/jpeg", "test data".getBytes());

        Picture mockPicture = Picture.builder().build();
        mockPicture.setId(UUID.randomUUID().toString()); // Example: Set any specific attributes or mock behaviors
        when(pictureMapper.fromMultipartFileToEntity(any(MultipartFile.class))).thenReturn(mockPicture);

        // Act
        ResponseEntity<String> result = uploadService.processAndSaveFile(file);

        // Verify
        verify(pictureMapper, times(1)).fromMultipartFileToEntity(any(MultipartFile.class));
        verify(pictureService, times(1)).save(mockPicture);

        // Assert
        Assertions.assertEquals(HttpStatus.OK, result.getStatusCode(), "HTTP Status code should be OK");
        Assertions.assertEquals("Picture uploaded successfully", result.getBody(), "Response body should match");
    }

    @Test
    void testProcessAndSaveFile_ValidVideoFile_Success() throws IOException {
        // Arrange
        MultipartFile file = new MockMultipartFile(
                "file", "test.mp4", "video/mp4", "test data".getBytes());

        Video mockVideo = Video.builder().build();
        mockVideo.setId(UUID.randomUUID().toString());
        when(videoMapper.fromMultipartFileToEntity(any(MultipartFile.class))).thenReturn(mockVideo);

        // Act
        ResponseEntity<String> result = uploadService.processAndSaveFile(file);

        // Verify
        verify(videoMapper, times(1)).fromMultipartFileToEntity(any(MultipartFile.class));
        verify(videoService, times(1)).save(mockVideo);

        // Assert
        Assertions.assertEquals(HttpStatus.OK, result.getStatusCode(), "HTTP Status code should be OK");
        Assertions.assertEquals("Video uploaded successfully", result.getBody(), "Response body should match");
    }

    @Test
    void testProcessAndSaveFile_InvalidFileType() throws IOException {
        // Arrange
        MultipartFile file = new MockMultipartFile(
                "file", "test.txt", "text/plain", "test data".getBytes());

        // Act
        ResponseEntity<String> result = uploadService.processAndSaveFile(file);


        // Assert
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode(), "HTTP Status code should be BAD_REQUEST");
        Assertions.assertEquals("Invalid file type. Allowed picture types: jpg, jpeg, png, gif. Allowed video types: mp4, avi, mov",
                result.getBody(), "Response body should match");
    }

    @Test
    void testProcessAndSaveFile_ValidPictureButUnsupportedType() throws IOException {
        // Arrange
        MultipartFile file = new MockMultipartFile(
                "file", "test.bmp", "image/bmp", "test data".getBytes());

        // Act
        ResponseEntity<String> result = uploadService.processAndSaveFile(file);


        // Assert
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode(), "HTTP Status code should be BAD_REQUEST");
        Assertions.assertEquals("Invalid file type. Allowed picture types: jpg, jpeg, png, gif. Allowed video types: mp4, avi, mov",
                result.getBody(), "Response body should match");
    }

    @Test
    void testProcessAndSaveFile_ValidVideoButUnsupportedType() throws IOException {
        // Arrange
        MultipartFile file = new MockMultipartFile(
                "file", "test.xyz", "video/xyz", "test data".getBytes());

        // Act
        ResponseEntity<String> result = uploadService.processAndSaveFile(file);


        // Assert
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode(), "HTTP Status code should be BAD_REQUEST");
        Assertions.assertEquals("Invalid file type. Allowed picture types: jpg, jpeg, png, gif. Allowed video types: mp4, avi, mov",
                result.getBody(), "Response body should match");
    }


    @Test
    public void testTransferToBytes_EmptyFile() throws IOException {
        // Arrange
        byte[] emptyBytes = new byte[0];
        when(mockFile.getBytes()).thenReturn(emptyBytes);

        // Act
        byte[] result = uploadService.transferToBytes(mockFile);

        // Assert
        Assertions.assertArrayEquals(emptyBytes, result, "Byte arrays should be empty");
        verify(mockFile).getBytes(); // Verify that getBytes() was called
    }

    @Test
    void testProcessAndSaveFile_NullFileExtension() throws IOException {
        // Arrange
        MultipartFile file = new MockMultipartFile(
                "file", "test", "image/jpeg", "test data".getBytes());

        // Act
        ResponseEntity<String> result = uploadService.processAndSaveFile(file);

        // Assert
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode(), "HTTP Status code should be BAD_REQUEST");
        Assertions.assertEquals("Invalid file type. Allowed picture types: jpg, jpeg, png, gif. Allowed video types: mp4, avi, mov",
                result.getBody(), "Response body should match");
    }

    @Test
    void testProcessAndSaveFile_EmptyFileExtension() throws IOException {
        // Arrange
        MultipartFile file = new MockMultipartFile(
                "file", "test.", "image/jpeg", "test data".getBytes());

        // Act
        ResponseEntity<String> result = uploadService.processAndSaveFile(file);


        // Assert
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode(), "HTTP Status code should be BAD_REQUEST");
        Assertions.assertEquals("Invalid file type. Allowed picture types: jpg, jpeg, png, gif. Allowed video types: mp4, avi, mov",
                result.getBody(), "Response body should match");
    }

    @Test
    void testProcessAndSaveFile_UnsupportedFileType() throws IOException {
        // Arrange
        MultipartFile file = new MockMultipartFile(
                "file", "test.bmp", "image/bmp", "test data".getBytes());

        // Act
        ResponseEntity<String> result = uploadService.processAndSaveFile(file);


        // Assert
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode(), "HTTP Status code should be BAD_REQUEST");
        Assertions.assertEquals("Invalid file type. Allowed picture types: jpg, jpeg, png, gif. Allowed video types: mp4, avi, mov",
                result.getBody(), "Response body should match");
    }

    @Test
    void testTransferToBytes_SuccessfulTransfer() throws IOException {
        // Arrange
        byte[] fileBytes = "test data".getBytes();
        when(mockFile.getBytes()).thenReturn(fileBytes);

        // Act
        byte[] result = uploadService.transferToBytes(mockFile);

        // Assert
        Assertions.assertArrayEquals(fileBytes, result, "Byte arrays should be equal");
        verify(mockFile).getBytes(); // Verify that getBytes() was called
    }

    @Test
    void testTransferToBytes_IOExceptionHandling() throws IOException {
        // Arrange
        when(mockFile.getBytes()).thenThrow(new IOException("Simulated IOException"));

        // Act + Assert
        Assertions.assertThrows(IOException.class, () -> uploadService.transferToBytes(mockFile));
        verify(mockFile).getBytes(); // Verify that getBytes() was called
    }
}
