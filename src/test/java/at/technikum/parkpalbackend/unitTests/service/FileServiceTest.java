package at.technikum.parkpalbackend.unitTests.service;

import at.technikum.parkpalbackend.exception.FileNotFoundException;
import at.technikum.parkpalbackend.model.File;
import at.technikum.parkpalbackend.model.User;
import at.technikum.parkpalbackend.model.enums.FileType;
import at.technikum.parkpalbackend.persistence.FileRepository;
import at.technikum.parkpalbackend.service.FileService;
import at.technikum.parkpalbackend.service.MinioService;
import at.technikum.parkpalbackend.service.UserService;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class FileServiceTest {

    @InjectMocks
    FileService fileService;

    @Mock
    FileRepository fileRepository;

    @Mock
    UserService userService;

    @Mock
    MinioService minioService;

    @Value("${minio.bucket-name}")
    private String bucketName = "test-bucket";

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(minioService, "bucketName", "test-bucket");
    }

    @Test
    void uploadFile_whenInvalidFileExtension_thenBadRequest() {
        MultipartFile mockFile = mock(MultipartFile.class);

        ResponseEntity<String> response = fileService.uploadFile(mockFile, FileType.OTHER, null);

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Invalid file", response.getBody());
    }

    @Test
    void uploadFile_whenFileValid_thenSuccess() throws Exception {
        // Mock MinioClient
        MinioClient minioClient = mock(MinioClient.class);

        // Create an instance of MinioService with the mock MinioClient
        MinioService minioService = new MinioService(minioClient);

        ReflectionTestUtils.setField(minioService, "bucketName", "test-bucket");

        // Create InputStream directly for the file content
        InputStream fileContent = new ByteArrayInputStream("test content".getBytes());

        // Call the method under test
        minioService.uploadFile("valid_image.jpg", fileContent, "image/jpeg");

        // Use ArgumentCaptor to capture the PutObjectArgs passed to putObject
        ArgumentCaptor<PutObjectArgs> putObjectArgsCaptor = ArgumentCaptor.forClass(PutObjectArgs.class);
        verify(minioClient).putObject(putObjectArgsCaptor.capture());

        // Get the captured PutObjectArgs
        PutObjectArgs capturedArgs = putObjectArgsCaptor.getValue();

        // Assert that the values match what is expected
        assertEquals("test-bucket", capturedArgs.bucket());
        assertEquals("valid_image.jpg", capturedArgs.object());
        assertEquals("image/jpeg", capturedArgs.contentType());
    }

    @Test
    void downloadFile_whenFileNotFound_thenReturn404() {
        String externalId = UUID.randomUUID().toString();
        when(fileRepository.findByExternalId(externalId)).thenReturn(Optional.empty());

        ResponseEntity<?> response = fileService.downloadFile(externalId);

        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("File not found: " + externalId, response.getBody());
    }

    @Test
    void downloadFile_whenExceptionOccurs_thenInternalServerError() throws Exception {
        String externalId = UUID.randomUUID().toString();
        when(fileRepository.findByExternalId(externalId)).thenThrow(new RuntimeException("Database error"));

        ResponseEntity<?> response = fileService.downloadFile(externalId);

        assertNotNull(response);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Internal server error", response.getBody());
    }

    @Test
    void downloadFile_whenFileExists_thenSuccess() throws Exception {
        String externalId = UUID.randomUUID().toString();
        File mockFile = File.builder().build();
        mockFile.setExternalId(externalId);
        mockFile.setFilename("example_image.jpg");
        mockFile.setPath("pictures/example_image.jpg");

        InputStream mockInputStream = new ByteArrayInputStream(new byte[0]);

        when(fileRepository.findByExternalId(externalId)).thenReturn(Optional.of(mockFile));
        doReturn(mockInputStream).when(minioService).getFile(mockFile.getPath());
        doReturn("image/jpeg").when(minioService).getContentType(mockFile.getPath());

        ResponseEntity<?> response = fileService.downloadFile(externalId);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getHeaders().get(HttpHeaders.CONTENT_DISPOSITION).get(0).contains("example_image.jpg"));
        assertEquals(MediaType.IMAGE_JPEG, response.getHeaders().getContentType());
        assertInstanceOf(InputStreamResource.class, response.getBody());
    }

    @Test
    void deleteFileByExternalId_whenFileNotFound_thenReturn404() {
        String externalId = UUID.randomUUID().toString();
        when(fileRepository.findByExternalId(externalId)).thenReturn(Optional.empty());

        ResponseEntity<String> response = fileService.deleteFileByExternalId(externalId);

        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("File not found for externalId: " + externalId, response.getBody());
    }

    @Test
    void deleteFileByExternalId_whenExceptionOccurs_thenInternalServerError() {
        String externalId = UUID.randomUUID().toString();
        when(fileRepository.findByExternalId(externalId)).thenThrow(new RuntimeException("Database error"));

        ResponseEntity<String> response = fileService.deleteFileByExternalId(externalId);

        assertNotNull(response);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("File deletion failed.", response.getBody());
    }

    @Test
    void deleteFileByExternalId_whenFileExists_thenSuccess() {
        String externalId = UUID.randomUUID().toString();
        File mockFile = File.builder().build();
        mockFile.setExternalId(externalId);

        when(fileRepository.findByExternalId(externalId)).thenReturn(Optional.of(mockFile));

        ResponseEntity<String> response = fileService.deleteFileByExternalId(externalId);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("File deleted successfully.", response.getBody());
    }

    @Test
    void assignProfilePicture_whenInvalidProfilePictureId_thenDoNothing() {
        User mockUser = User.builder().build();
        mockUser.setMedia(new ArrayList<>());

        fileService.assignProfilePicture(mockUser, null, false);

        // Ensure that no file was assigned
        assertTrue(mockUser.getMedia().isEmpty());
    }

    @Test
    void assignProfilePicture_whenFileNotFound_thenThrowException() {
        String profilePictureId = UUID.randomUUID().toString();
        when(fileRepository.findByExternalId(profilePictureId)).thenReturn(Optional.empty());

        User mockUser = User.builder().build();

        assertThrows(FileNotFoundException.class, () ->
                fileService.assignProfilePicture(mockUser, profilePictureId, true)
        );
    }

    @Test
    void assignProfilePicture_whenValidProfilePicture_thenSuccess() {
        User mockUser = User.builder().build();
        mockUser.setMedia(new ArrayList<>());

        String profilePictureId = UUID.randomUUID().toString();
        File mockProfilePicture = File.builder().build();
        mockProfilePicture.setExternalId(profilePictureId);
        mockProfilePicture.setFileType(FileType.PROFILE_PICTURE);

        when(fileRepository.findByExternalId(profilePictureId)).thenReturn(Optional.of(mockProfilePicture));

        fileService.assignProfilePicture(mockUser, profilePictureId, false);

        // Ensure that profile picture was successfully added
        assertEquals(1, mockUser.getMedia().size());
        assertEquals(FileType.PROFILE_PICTURE, mockUser.getMedia().get(0).getFileType());
    }

    @Test
    void listAllFiles_whenNoEventParkOrUserId_thenReturnEmptyList() {
        when(minioService.listAllFiles()).thenReturn(Collections.emptyList());

        List<String> files = fileService.listAllFiles(null, null, null);

        assertNotNull(files);
        assertTrue(files.isEmpty());
    }

    @Test
    void listAllFiles_whenNoFilesFoundForEvent_thenReturnEmptyList() {
        String eventId = UUID.randomUUID().toString();
        when(fileRepository.findByEventId(eventId)).thenReturn(Collections.emptyList());

        List<String> files = fileService.listAllFiles(eventId, null, null);

        assertNotNull(files);
        assertTrue(files.isEmpty());
    }

    @Test
    void listAllFiles_whenFilesFoundForUser_thenReturnFileIds() {
        String userId = UUID.randomUUID().toString();
        File mockFile = File.builder().build();
        mockFile.setExternalId(UUID.randomUUID().toString());

        when(fileRepository.findByUserId(userId)).thenReturn(Collections.singletonList(mockFile));

        List<String> files = fileService.listAllFiles(null, null, userId);

        assertNotNull(files);
        assertEquals(1, files.size());
        assertEquals(mockFile.getExternalId(), files.get(0));
    }
}