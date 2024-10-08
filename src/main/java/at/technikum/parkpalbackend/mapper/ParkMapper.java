package at.technikum.parkpalbackend.mapper;

import at.technikum.parkpalbackend.dto.parkdtos.CreateParkDto;
import at.technikum.parkpalbackend.dto.parkdtos.ParkDto;
import at.technikum.parkpalbackend.dto.parkdtos.UpdateParkDto;
import at.technikum.parkpalbackend.model.Event;
import at.technikum.parkpalbackend.model.File;
import at.technikum.parkpalbackend.model.Park;
import at.technikum.parkpalbackend.service.EventService;
import at.technikum.parkpalbackend.service.FileService;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class ParkMapper {
    private final EventMapper eventMapper;
    private final EventService eventService;
    private final FileService fileService;
    private final FileMapper fileMapper;


    public ParkMapper(EventMapper eventMapper,
                      EventService eventService,
                      FileService fileService,
                      FileMapper fileMapper) {
        this.eventMapper = eventMapper;
        this.eventService = eventService;
        this.fileService = fileService;
        this.fileMapper = fileMapper;
    }

    // TODO add parkFiles
    public ParkDto toDto(Park park){
        if (park == null) {
            throw new IllegalArgumentException("Park entity or its fields cannot be null");
        }
        return ParkDto.builder()
                .id(park.getId())
                .name(park.getName())
                .description(park.getDescription())
                .address(park.getAddress())
                .eventDtos(park.getEvents().stream()
                        .map(event -> eventMapper.toDto(event))
                        .toList())
                .filesExternalIds(park.getMedia().stream()
                        .map(file -> file.getExternalId())
                        .toList())
                .build();
    }

    public Park toEntity(ParkDto parkDto) {
        if (parkDto == null) {
            throw new IllegalArgumentException("ParkDto or its fields cannot be null");
        }
        return Park.builder()
                .id(parkDto.getId())
                .name(parkDto.getName())
                .description(parkDto.getDescription())
                .address(parkDto.getAddress())
                .events(getList(parkDto))
                .media(getFiles(parkDto))
                .build();
    }

    private List<File> getFiles(ParkDto parkDto) {
        if (parkDto.getFilesExternalIds() == null) {
            return new ArrayList<>();
        }
        return parkDto.getFilesExternalIds().stream()
                .map(fileService::findFileByExternalId)
                .toList();
    }

    @NotNull
    private List<Event> getList(ParkDto parkDto) {
        if (parkDto.getEventDtos() == null) {
            return new ArrayList<>();
        }
        return parkDto.getEventDtos().stream()
                .map(eventDto -> eventMapper.toEntity(eventDto, Optional.empty())).toList();
    }

    public CreateParkDto toCreateParkDto(Park park){
        if (park == null) {
            throw new IllegalArgumentException("Park entity or its fields cannot be null");
        }
        return CreateParkDto.builder()
                .parkId(park.getId())
                .name(park.getName())
                .description(park.getDescription())
                .address(park.getAddress())
                .build();
    }

    public Park createParkDtoToEntity(CreateParkDto createParkDto) {
        if (createParkDto == null) {
            throw new IllegalArgumentException("CreateParkDto or its fields cannot be null");
        }
        return Park.builder()
                .id(createParkDto.getParkId())
                .name(createParkDto.getName())
                .description(createParkDto.getDescription())
                .address(createParkDto.getAddress())
                .build();
    }

    public Park updateParkDtoToEntity(UpdateParkDto updateParkDto) {
        if (updateParkDto == null) {
            throw new IllegalArgumentException("UpdateParkDto or its fields cannot be null");
        }

        if (updateParkDto.getEventIds() == null) {
            throw new IllegalArgumentException("EventIds cannot be null");
        }

        List<Event> events = updateParkDto.getEventIds().stream()
                    .map(eventService::findByEventId)
                    .toList();

        List<File> files = updateParkDto.getFilesExternalIds().stream()
                    .map(fileService::findFileByExternalId)
                    .toList();

        return Park.builder()
                .name(updateParkDto.getName())
                .description(updateParkDto.getDescription())
                .address(updateParkDto.getAddress())
                .events(events)
                .media(files)
                .build();
    }
}
