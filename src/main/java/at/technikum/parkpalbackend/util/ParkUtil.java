package at.technikum.parkpalbackend.util;

import at.technikum.parkpalbackend.dto.parkdtos.CreateParkDto;
import at.technikum.parkpalbackend.dto.parkdtos.ParkDto;
import at.technikum.parkpalbackend.exception.EntityNotFoundException;
import at.technikum.parkpalbackend.mapper.ParkMapper;
import at.technikum.parkpalbackend.model.Event;
import at.technikum.parkpalbackend.model.File;
import at.technikum.parkpalbackend.model.Park;
import at.technikum.parkpalbackend.persistence.ParkRepository;
import at.technikum.parkpalbackend.service.EventService;
import at.technikum.parkpalbackend.service.FileService;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ParkUtil {

    private final FileService fileService;
    private final ParkMapper parkMapper;
    private final ParkRepository parkRepository;
    private final EventService eventService;

    public ParkUtil(FileService fileService,
                    ParkMapper parkMapper,
                    ParkRepository parkRepository,
                    EventService eventService) {
        this.fileService = fileService;
        this.parkMapper = parkMapper;
        this.parkRepository = parkRepository;
        this.eventService = eventService;
    }

    public CreateParkDto saveCreatePark(CreateParkDto createParkDto) {

        List<File> mediaFiles = fileService.getFileList(createParkDto.getMediaFileExternalIds());

        Park park = parkMapper.createParkDtoToEntity(createParkDto, mediaFiles);

        park = parkRepository.save(park);

        fileService.setParkMedia(park, mediaFiles);

        return parkMapper.toCreateParkDto(park);
    }

    public Park updatePark(String parkId, ParkDto parkDto) {
        Park existingPark = parkRepository.findById(parkId)
                .orElseThrow(() ->
                        new EntityNotFoundException("Park not found with id: " + parkId));

        updateBasicParkDetails(existingPark, parkDto);
        updateParkEvents(existingPark, parkDto);
        updateParkMedia(existingPark, parkDto);

        return parkRepository.save(existingPark);
    }

    private void updateBasicParkDetails(Park park, ParkDto parkDto) {
        park.setName(parkDto.getName());
        park.setDescription(parkDto.getDescription());
        park.setAddress(parkDto.getAddress());
    }

    private void updateParkEvents(Park park, ParkDto parkDto) {
        if (parkDto.getEventIds() != null) {
            List<Event> newEvents = parkDto.getEventIds().stream()
                    .map(eventService::findByEventId)
                    .collect(Collectors.toList());

            // Remove the park from old events
            for (Event oldEvent : park.getEvents()) {
                oldEvent.setPark(null);
            }

            // Clear the existing events
            park.getEvents().clear();

            // Add new events and maintain bidirectional relationship
            for (Event newEvent : newEvents) {
                park.getEvents().add(newEvent);
                newEvent.setPark(park);
            }
        }
    }

    private void updateParkMedia(Park park, ParkDto parkDto) {
        if (parkDto.getMediaFileExternalIds() != null) {
            for (File oldFile : park.getMedia()) {
                oldFile.setPark(null);
            }
            park.getMedia().clear();

            List<File> mediaFiles = parkDto.getMediaFileExternalIds().stream()
                    .map(fileService::findFileByExternalId)
                    .collect(Collectors.toList());

            for (File mediaFile : mediaFiles) {
                mediaFile.setPark(park);
                park.getMedia().add(mediaFile);
            }
        }
    }
}