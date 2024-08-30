package at.technikum.parkpalbackend.mapper;

import at.technikum.parkpalbackend.dto.FileDto;

import at.technikum.parkpalbackend.model.FileMetadata;
import at.technikum.parkpalbackend.service.FileUploadService;
import at.technikum.parkpalbackend.service.UserService;
import org.springframework.stereotype.Component;


@Component
public class FileMapper {

    public UserService userService;
    public FileUploadService fileUploadService;

    public FileDto toDto(FileMetadata file) {
        if (file == null) {
            throw new IllegalArgumentException("File entity or its fields cannot be null");
        }
        return FileDto.builder()
                .id(file.getId())
                .userId(file.getUser().getId())
                .uploadDate(file.getUploadDate())
                .build();
    }

    public FileMetadata toEntity(FileDto fileDto) {
        if (fileDto == null) {
            throw new IllegalArgumentException("FileDTO or its fields cannot be null");
        }
        return FileMetadata.builder()
                .id(fileDto.getId())
                .user(userService.findByUserId(fileDto.getUserId()))
                .uploadDate(fileDto.getUploadDate())
                .build();
    }
}
