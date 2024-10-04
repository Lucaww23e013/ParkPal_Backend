package at.technikum.parkpalbackend.service;

import at.technikum.parkpalbackend.exception.FileNotFoundException;
import at.technikum.parkpalbackend.model.File;
import io.minio.*;
import io.minio.errors.ErrorResponseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.List;
import java.util.stream.StreamSupport;

@Service
public class MinioService {

    private final MinioClient minioClient;

    @Value("${minio.bucket-name}")
    private String bucketName;

    public MinioService(MinioClient minioClient) {
        this.minioClient = minioClient;
    }

    public void uploadFile(String objectName,
                           InputStream inputStream,
                           String contentType) throws Exception {
        minioClient.putObject(
                PutObjectArgs.builder()
                        .bucket(bucketName)
                        .object(objectName)
                        .stream(inputStream, inputStream.available(), -1)
                        .contentType(contentType)
                        .build()
        );
    }

    public InputStream getFile(String objectName) throws Exception {
        try {
            return minioClient.getObject(GetObjectArgs.builder()
                    .bucket(bucketName)
                    .object(objectName)
                    .build());
        } catch (ErrorResponseException e) {
            if (e.errorResponse().code().equals("NoSuchKey")) {
                throw new FileNotFoundException("File not found: " + objectName);
            }
            throw e;
        } catch (Exception e) {
            throw e;
        }
    }

    public String getContentType(String objectName) throws Exception {
        return minioClient.statObject(StatObjectArgs.builder()
                .bucket(bucketName)
                .object(objectName)
                .build()).contentType();
    }

    /*
        * Deletes a file from the Minio bucket.
        * ignores the governance mode.
        * This means that the file will be deleted even if it is under retention.
        * @param path The path of the file to be deleted.
        * @throws RuntimeException If the file could not be deleted.
     */
    public void deleteFile(String path) {
        try {
            if (path == null || path.isEmpty()) {
                return;
            }
            minioClient.removeObject(
                    RemoveObjectArgs.builder()
                            .bucket(bucketName)
                            .object(path)
                            .bypassGovernanceMode(true)
                            .build()
            );
        } catch (ErrorResponseException e) {
            if (!e.errorResponse().code().equals("NoSuchKey")) {
                throw new RuntimeException("Failed to delete file: " + path, e);
            }
        }catch (Exception e) {
            throw new RuntimeException("Failed to delete file: " + path, e);
        }
    }

    public boolean doesFileExist(String objectName) {
        try {
            minioClient.statObject(
                    StatObjectArgs.builder().bucket(bucketName).object(objectName).build());
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public List<File> listAllFiles() {
        try {
            return StreamSupport.stream(minioClient
                            .listObjects(ListObjectsArgs.builder().bucket(bucketName)
                                    .recursive(true).build())
                            .spliterator(), false)
                    .map(result -> {
                        try {
                            return result.get();
                        } catch (Exception e) {
                            throw new RuntimeException("Failed to get item", e);
                        }
                    })
                    .map(item -> File.builder()
                            .path(item.objectName())
                            .externalId(item.objectName().split("/")[1])
                            .build())
                    .filter(file -> file.getExternalId() != null)
                    .toList();
        } catch (Exception e) {
            throw new RuntimeException("Failed to list files", e);
        }
    }
}
