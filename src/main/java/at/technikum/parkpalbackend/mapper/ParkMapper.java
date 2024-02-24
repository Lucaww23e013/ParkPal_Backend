package at.technikum.parkpalbackend.mapper;

import at.technikum.parkpalbackend.dto.parkdtos.CreateParkDto;
import at.technikum.parkpalbackend.dto.parkdtos.ParkDto;
import at.technikum.parkpalbackend.model.Park;
import org.springframework.stereotype.Component;

@Component
public class ParkMapper {

    public ParkDto toDto(Park park){
        return ParkDto.builder()
                .parkId(park.getParkId())
                .name(park.getName())
                .description(park.getDescription())
                .parkEvents(park.getParkEvents())
                .parkPictures(park.getParkPictures())
                .build();
    }

    public Park toEntity(ParkDto parkDto) {
        return Park.builder()
                .parkId(parkDto.getParkId())
                .name(parkDto.getName())
                .description(parkDto.getDescription())
                .parkEvents(parkDto.getParkEvents())
                .parkPictures(parkDto.getParkPictures())
                .build();
    }

    public CreateParkDto toCreateParkDto(Park park){
        return CreateParkDto.builder()
                .parkId(park.getParkId())
                .name(park.getName())
                .description(park.getDescription())
                .address(park.getAddress())
                .build();
    }

    public Park CreateParkDtoToEntity(CreateParkDto createParkDto) {
        return Park.builder()
                .parkId(createParkDto.getParkId())
                .name(createParkDto.getName())
                .description(createParkDto.getDescription())
                .address(createParkDto.getAddress())
                .build();
    }
}
