package at.technikum.parkpalbackend.service;

import at.technikum.parkpalbackend.dto.parkdtos.CreateParkDto;
import at.technikum.parkpalbackend.dto.parkdtos.ParkDto;
import at.technikum.parkpalbackend.exception.EntityNotFoundException;
import at.technikum.parkpalbackend.mapper.ParkMapper;
import at.technikum.parkpalbackend.model.Event;
import at.technikum.parkpalbackend.model.File;
import at.technikum.parkpalbackend.model.Park;
import at.technikum.parkpalbackend.persistence.ParkRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ParkService {
    private final ParkRepository parkRepository;
    private final EventService eventService;
    private final FileService fileService;
    private final ParkMapper parkMapper;

    public ParkService(ParkRepository parkRepository,
                       EventService eventService,
                       FileService fileService,
                       ParkMapper parkMapper) {
        this.parkRepository = parkRepository;
        this.fileService = fileService;
        this.eventService = eventService;
        this.parkMapper = parkMapper;
    }

    public List<Park> findAllParks() {
        return parkRepository.findAll();
    }
    public Park findParkById(String parkId) {
        return parkRepository.findById(parkId)
                .orElseThrow(() -> new EntityNotFoundException("Park with id %s not found "
                        .formatted(parkId)));
    }

    public CreateParkDto saveCreatePark(CreateParkDto createParkDto) {

        List<File> mediaFiles = fileService.getFileList(createParkDto.getFilesExternalIds());

        Park park = parkMapper.createParkDtoToEntity(createParkDto, mediaFiles);

        park = parkRepository.save(park);

        fileService.setParkMedia(park, mediaFiles);

        return parkMapper.toCreateParkDto(park);
    }

    public Park updatePark(String parkId, ParkDto parkDto) {
        // Fetch the existing park entity
        Park existingPark = parkRepository.findById(parkId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Park not found with id: " + parkId));

        // Update the park fields
        existingPark.setName(parkDto.getName());
        existingPark.setDescription(parkDto.getDescription());
        existingPark.setAddress(parkDto.getAddress());

        // Resolve events and files
        if (parkDto.getEventIds() != null) {
            List<Event> events = parkDto.getEventIds().stream()
                    .map(eventService::findByEventId)
                    .collect(Collectors.toList());
            existingPark.setEvents(events);
        }

        if (parkDto.getFilesExternalIds() != null) {
            List<File> files = parkDto.getFilesExternalIds().stream()
                    .map(fileService::findFileByExternalId)
                    .collect(Collectors.toList());
            existingPark.setMedia(files);
        }

        // Save the updated park entity
        return parkRepository.save(existingPark); // Assuming there's a repository
    }

    public Park deleteParkByParkId(String parkId) {
        Park park = parkRepository.findById(parkId).orElseThrow(
                () -> new EntityNotFoundException("Park with id %s not found "
                        .formatted(parkId)));
        parkRepository.delete(park);
        return park;
    }
}
