package at.technikum.parkpalbackend.mapper;

import at.technikum.parkpalbackend.dto.parkdtos.CreateParkDto;
import at.technikum.parkpalbackend.dto.parkdtos.ParkDto;
import at.technikum.parkpalbackend.model.Park;
import org.springframework.stereotype.Component;

@Component
public class ParkMapper {
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
                .parkEvents(park.getParkEvents())
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
                .parkEvents(parkDto.getParkEvents())
                .build();
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
