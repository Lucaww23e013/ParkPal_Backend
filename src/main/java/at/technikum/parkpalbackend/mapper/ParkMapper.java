package at.technikum.parkpalbackend.mapper;

import at.technikum.parkpalbackend.dto.parkdtos.CreateParkDto;
import at.technikum.parkpalbackend.dto.parkdtos.ParkDto;
import at.technikum.parkpalbackend.model.Event;
import at.technikum.parkpalbackend.model.Park;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ParkMapper {
    private final EventMapper eventMapper;

    public ParkMapper(EventMapper eventMapper) {
        this.eventMapper = eventMapper;
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
                .parkEventDtos(park.getParkEvents().stream()
                        .map(event -> eventMapper.toDto(event))
                        .toList())  //
                .build();
    }
    // TODO add parkFiles
    public Park toEntity(ParkDto parkDto) {
        if (parkDto == null) {
            throw new IllegalArgumentException("ParkDto or its fields cannot be null");
        }
        return Park.builder()
                .id(parkDto.getId())
                .name(parkDto.getName())
                .description(parkDto.getDescription())
                .address(parkDto.getAddress())
                .parkEvents(getList(parkDto))
                .build();
    }

    @NotNull
    private List<Event> getList(ParkDto parkDto) {
        if (parkDto.getParkEventDtos() == null) {
            return new ArrayList<>();
        }
        return parkDto.getParkEventDtos().stream()
                .map(eventDto -> eventMapper.toEntity(eventDto)).toList();
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
}
