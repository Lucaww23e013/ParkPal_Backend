package at.technikum.parkpalbackend.unitTests.service;

import at.technikum.parkpalbackend.exception.FileNotFoundException;
import at.technikum.parkpalbackend.model.File;
import at.technikum.parkpalbackend.service.MinioService;
import io.minio.*;
import io.minio.errors.*;
import io.minio.messages.ErrorResponse;
import io.minio.messages.Item;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MinioServiceTest {

    @Mock
    private MinioClient minioClient;

    @InjectMocks
    private MinioService minioService;

    private final String bucketName = "test-bucket";
    private final String objectName = "test-object";

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(minioService, "bucketName", "test-bucket");
    }

    @Test
    void uploadFile_shouldUploadSuccessfully() throws Exception {
        // Positive test case for uploading file
        InputStream inputStream = new ByteArrayInputStream("test data".getBytes());
        minioService.uploadFile("test-object", inputStream, "text/plain");

        // Verify that the minioClient.putObject method was called
        verify(minioClient, times(1)).putObject(any(PutObjectArgs.class));
    }

    @Test
    void getFile_shouldThrowFileNotFoundException_whenFileDoesNotExist() throws Exception {
        // Mock ErrorResponseException and ErrorResponse
        ErrorResponseException errorResponseException = mock(ErrorResponseException.class);
        ErrorResponse errorResponse = mock(ErrorResponse.class);

        // Set up the error code to simulate a missing file ("NoSuchKey")
        when(errorResponse.code()).thenReturn("NoSuchKey");
        when(errorResponseException.errorResponse()).thenReturn(errorResponse);

        // Simulate the getObject method throwing ErrorResponseException
        when(minioClient.getObject(any(GetObjectArgs.class))).thenThrow(errorResponseException);

        // Act & Assert: Expect FileNotFoundException to be thrown
        FileNotFoundException thrown = assertThrows(FileNotFoundException.class, () -> {
            minioService.getFile(objectName);
        }, "Expected FileNotFoundException for missing file");

        // Assert that the exception message is correct
        assertEquals("File not found: " + objectName, thrown.getMessage());

        // Verify the interactions with the minioClient
        verify(minioClient, times(1)).getObject(any(GetObjectArgs.class));
        verifyNoMoreInteractions(minioClient);
    }

    // Positive Test: File exists
    @Test
    void doesFileExist_shouldReturnTrue_whenFileExists() throws Exception {
        when(minioClient.statObject(any(StatObjectArgs.class)))
                .thenReturn(mock(StatObjectResponse.class));

        // Act & Assert
        assertTrue(minioService.doesFileExist(objectName), "Expected true when file exists");

        verify(minioClient, times(1)).statObject(any(StatObjectArgs.class));
        verifyNoMoreInteractions(minioClient);
    }

    @Test
    void doesFileExist_shouldReturnFalse_whenFileDoesNotExist() throws Exception {
        // Mock ErrorResponseException to simulate file not found
        ErrorResponseException errorResponseException = mock(ErrorResponseException.class);
        ErrorResponse errorResponse = mock(ErrorResponse.class);

        // Simulate the "NoSuchKey" error code which means the file does not exist
        lenient().when(errorResponse.code()).thenReturn("NoSuchKey");
        lenient().when(errorResponseException.errorResponse()).thenReturn(errorResponse);

        // Simulate statObject throwing ErrorResponseException
        when(minioClient.statObject(any(StatObjectArgs.class))).thenThrow(errorResponseException);

        // Act & Assert
        assertFalse(minioService.doesFileExist(objectName),
                "Expected false when file does not exist");

        // Verify interactions with the minioClient
        verify(minioClient, times(1)).statObject(any(StatObjectArgs.class));
        verifyNoMoreInteractions(minioClient);
    }

    @Test
    void deleteFile_shouldThrowRuntimeException_whenDeletionFails() throws Exception {
        // Mock ErrorResponse and ErrorResponseException
        ErrorResponseException errorResponseException = mock(ErrorResponseException.class);
        ErrorResponse errorResponse = mock(ErrorResponse.class);

        // Set up the error code and exception behavior
        when(errorResponse.code()).thenReturn("SomeOtherError");
        when(errorResponseException.errorResponse()).thenReturn(errorResponse);

        // Simulate the removeObject method throwing an exception
        doThrow(errorResponseException).when(minioClient)
                .removeObject(any(RemoveObjectArgs.class));

        // Act and Assert: Expect RuntimeException to be thrown
        RuntimeException thrown = assertThrows(RuntimeException.class, () -> {
            minioService.deleteFile(objectName);
        });

        // Verify the exception message
        assertEquals("Failed to delete file: " + objectName, thrown.getMessage());

        // Verify that removeObject was called exactly once
        verify(minioClient, times(1)).removeObject(any(RemoveObjectArgs.class));
    }

    @Test
    void getFile_shouldReturnFileSuccessfully() throws Exception {
        // Mock the expected InputStream
        InputStream inputStream = new ByteArrayInputStream("test data".getBytes());

        // Mock GetObjectResponse to return the mocked InputStream
        GetObjectResponse mockResponse = mock(GetObjectResponse.class);
        lenient().when(mockResponse.readAllBytes()).thenReturn(inputStream.readAllBytes());  // Mock the method that returns the InputStream or byte data

        // Mock minioClient.getObject() to return the mocked GetObjectResponse
        when(minioClient.getObject(any(GetObjectArgs.class))).thenReturn(mockResponse);

        // Call the service method
        InputStream result = minioService.getFile(objectName);
        assertNotNull(result);

        // Verify that getObject was called once
        verify(minioClient, times(1)).getObject(any(GetObjectArgs.class));
    }


    @Test
    void getContentType_shouldReturnContentType() throws Exception {
        StatObjectResponse statObjectResponse = mock(StatObjectResponse.class);
        when(statObjectResponse.contentType()).thenReturn("text/plain");
        when(minioClient.statObject(any(StatObjectArgs.class))).thenReturn(statObjectResponse);

        String contentType = minioService.getContentType(objectName);
        assertEquals("text/plain", contentType);

        verify(minioClient, times(1)).statObject(any(StatObjectArgs.class));
    }

    @Test
    void deleteFile_shouldDoNothing_whenPathIsNull() throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        minioService.deleteFile(null);
        verify(minioClient, never()).removeObject(any(RemoveObjectArgs.class));
    }

    @Test
    void deleteFile_shouldDoNothing_whenPathIsEmpty() throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        minioService.deleteFile("");
        verify(minioClient, never()).removeObject(any(RemoveObjectArgs.class));
    }

    @Test
    void deleteFile_shouldDeleteSuccessfully() throws Exception {
        minioService.deleteFile(objectName);
        verify(minioClient, times(1)).removeObject(any(RemoveObjectArgs.class));
    }

    @Test
    void listAllFiles_shouldReturnListOfFiles() throws Exception {
        // Arrange
        // Create a controlled single-element iterable
        Result<Item> result = mock(Result.class);
        Item item = mock(Item.class);
        Iterable<Result<Item>> resultIterable = List.of(result); // Using List.of to ensure one item

        // Mock minioClient to return exactly our single-element iterable
        when(minioClient.listObjects(any(ListObjectsArgs.class))).thenReturn(resultIterable);

        // Set up the result to return the mocked item without exceptions
        when(result.get()).thenReturn(item);

        // Define the object name for the item
        when(item.objectName()).thenReturn("file1/test-object");

        // Act
        List<File> files = minioService.listAllFiles();

        // Assert
        assertEquals(1, files.size());  // Confirm exactly one file is returned
        assertEquals("test-object", files.get(0).getExternalId());
        assertEquals("file1/test-object", files.get(0).getPath());

        // Verify that listObjects was called exactly once
        verify(minioClient, times(1)).listObjects(any(ListObjectsArgs.class));
    }
}