package at.technikum.parkpalbackend.mapper;

import at.technikum.parkpalbackend.dto.parkdtos.CreateParkDto;
import at.technikum.parkpalbackend.dto.parkdtos.ParkDto;
import at.technikum.parkpalbackend.model.Event;
import at.technikum.parkpalbackend.model.File;
import at.technikum.parkpalbackend.model.Park;
import org.springframework.stereotype.Component;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ParkMapper {



    public ParkMapper() {}

    public ParkDto toDto(Park park){
        if (park == null) {
            throw new IllegalArgumentException("Park entity or its fields cannot be null");
        }
        return ParkDto.builder()
                .id(park.getId())
                .name(park.getName())
                .description(park.getDescription())
                .address(park.getAddress())
                .eventIds(park.getEvents() != null ?
                        park.getEvents().stream()
                                .map(Event::getId)
                                .collect(Collectors.toList()) :
                        Collections.emptyList())
                .filesExternalIds(park.getMedia() != null ?
                        park.getMedia().stream()
                                .map(File::getExternalId)
                                .collect(Collectors.toList()) :
                        Collections.emptyList())
                .build();
    }



    public CreateParkDto toCreateParkDto(Park park){
        if (park == null) {
            throw new IllegalArgumentException("Park entity or its fields cannot be null");
        }
        return CreateParkDto.builder()
                .name(park.getName())
                .description(park.getDescription())
                .address(park.getAddress())
                .filesExternalIds(park.getMedia() != null ?
                        park.getMedia().stream()
                                .map(File::getExternalId)
                                .collect(Collectors.toList()) :
                        Collections.emptyList())
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
                .media(mediaFiles)
                .build();
    }
}
