package at.technikum.parkpalbackend.mapper;

import at.technikum.parkpalbackend.dto.parkdtos.CreateParkDto;
import at.technikum.parkpalbackend.dto.parkdtos.ParkDto;
import at.technikum.parkpalbackend.model.Event;
import at.technikum.parkpalbackend.model.Media;
import at.technikum.parkpalbackend.model.Park;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ParkMapper {

    public ParkDto toDto(Park park){
        return ParkDto.builder()
                .parkId(park.getParkId())
                .parkName(park.getParkName())
                .description(park.getDescription())
                .parkEvents(park.getParkEvents())
                .parkMedia(park.getParkMedia())
                .build();
    }

    public Park toEntity(ParkDto parkDto) {
        return Park.builder()
                .parkId(parkDto.getParkId())
                .parkName(parkDto.getParkName())
                .description(parkDto.getDescription())
                .parkEvents(parkDto.getParkEvents())
                .parkMedia(parkDto.getParkMedia())
                .build();
    }

    public CreateParkDto toCreateParkDto(Park park){
        return CreateParkDto.builder()
                .parkId(park.getParkId())
                .parkName(park.getParkName())
                .description(park.getDescription())
                .parkAddress(park.getParkAddress())
                .build();
    }

    public Park CreateParkDtoToEntity(CreateParkDto createParkDto) {
        return Park.builder()
                .parkId(createParkDto.getParkId())
                .parkName(createParkDto.getParkName())
                .description(createParkDto.getDescription())
                .parkAddress(createParkDto.getParkAddress())
                .build();
    }
}
