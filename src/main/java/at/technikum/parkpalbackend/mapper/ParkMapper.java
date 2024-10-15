package at.technikum.parkpalbackend.mapper;

import at.technikum.parkpalbackend.dto.parkdtos.CreateParkDto;
import at.technikum.parkpalbackend.dto.parkdtos.ParkDto;
import at.technikum.parkpalbackend.model.Event;
import at.technikum.parkpalbackend.model.File;
import at.technikum.parkpalbackend.model.Park;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ParkMapper {

    public ParkDto toDto(Park park){
        if (park == null) {
            throw new IllegalArgumentException("Park entity or its fields cannot be null");
        }
        return ParkDto.builder()
                .id(park.getId())
                .name(park.getName())
                .description(park.getDescription())
                .address(park.getAddress())
                .eventIds(getEventIds(park.getEvents()))
                .filesExternalIds(getFileExternalIds(park.getMedia()))
                .build();
    }

    public CreateParkDto toCreateParkDto(Park park){
        if (park == null) {
            throw new IllegalArgumentException("Park entity or its fields cannot be null");
        }
        return CreateParkDto.builder()
                .id(park.getId())
                .name(park.getName())
                .description(park.getDescription())
                .address(park.getAddress())
                .mediaFileExternalIds(park.getMedia() != null ?
                        getFileExternalIds(park.getMedia()) : new ArrayList<>())
                .build();
    }

    public Park createParkDtoToEntity(CreateParkDto createParkDto, List<File> mediaFiles) {
        if (createParkDto == null) {
            throw new IllegalArgumentException("CreateParkDto or its fields cannot be null");
        }
        return Park.builder()
                .name(createParkDto.getName())
                .description(createParkDto.getDescription())
                .address(createParkDto.getAddress())
                .media(mediaFiles != null ? new ArrayList<>(mediaFiles) : new ArrayList<>())
                .build();
    }

    private static List<String> getEventIds(List<Event> events) {
        if (events == null) {
            return new ArrayList<>();
        }

        return events.stream()
                .map(Event::getId)
                .toList();
    }

    private static List<String> getFileExternalIds(List<File> mediaFiles) {
        if (mediaFiles == null) {
            return new ArrayList<>();
        }

        return mediaFiles.stream()
                .map(File::getExternalId)
                .toList();
    }
}
