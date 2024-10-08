package at.technikum.parkpalbackend.mapper;

import at.technikum.parkpalbackend.dto.FileDto;
import at.technikum.parkpalbackend.model.File;
import at.technikum.parkpalbackend.service.EventService;
import at.technikum.parkpalbackend.service.ParkService;
import at.technikum.parkpalbackend.service.UserService;
import org.springframework.stereotype.Component;


@Component
public class FileMapper {

    private final UserService userService;
    private final ParkService parkService;
    private final EventService eventService;

    public FileMapper(UserService userService,
                      ParkService parkService,
                      EventService eventService) {
        this.userService = userService;
        this.parkService = parkService;
        this.eventService = eventService;
    }

    public FileDto toDto(File file) {
        if (file == null) {
            throw new IllegalArgumentException("File entity or its fields cannot be null");
        }
        return FileDto.builder()
                .externalId(file.getExternalId())
                .filename(file.getFilename())
                .parkId(file.getPark() != null ?
                        file.getPark().getId() : null)
                .evenId(file.getEvent() != null ?
                        file.getEvent().getId() : null)
                .fileType(file.getFileType())
                .userId(file.getUser() != null ?
                        file.getUser().getId() : null)
                .uploadDate(file.getUploadDate())
                .build();
    }

    public File toEntity(FileDto fileDto) {
        if (fileDto == null) {
            throw new IllegalArgumentException("FileDTO or its fields cannot be null");
        }
        return File.builder()
                .user(userService.findByUserId(fileDto.getUserId()))
                .externalId(fileDto.getExternalId())
                .filename(fileDto.getFilename())
                .fileType(fileDto.getFileType())
                .event(fileDto.getEvenId() != null ?
                        eventService.findByEventId(fileDto.getEvenId()) : null)
                .park(fileDto.getParkId() != null ?
                        parkService.findParkById(fileDto.getParkId()) : null)
                .user(userService.findByUserId(fileDto.getUserId()))
                .uploadDate(fileDto.getUploadDate())
                .build();
    }
}
