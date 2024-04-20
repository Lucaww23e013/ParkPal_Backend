package at.technikum.parkpalbackend.mapper;

import at.technikum.parkpalbackend.dto.parkdtos.CreateParkDto;
import at.technikum.parkpalbackend.dto.parkdtos.ParkDto;
import at.technikum.parkpalbackend.model.Park;
import org.springframework.stereotype.Component;

@Component
public class ParkMapper {

    public ParkDto toDto(Park park){
        return ParkDto.builder()
                .id(park.getId())
                .name(park.getName())
                .description(park.getDescription())
                .parkEvents(park.getParkEvents())
                .parkPictures(park.getParkPictures())
                .parkVideos(park.getParkVideos())
                .build();
    }

    public Park toEntity(ParkDto parkDto) {
        return Park.builder()
                .id(parkDto.getId())
                .name(parkDto.getName())
                .description(parkDto.getDescription())
                .parkEvents(parkDto.getParkEvents())
                .parkPictures(parkDto.getParkPictures())
                .parkVideos(parkDto.getParkVideos())
                .build();
    }

    public CreateParkDto toCreateParkDto(Park park){
        return CreateParkDto.builder()
                .parkId(park.getId())
                .name(park.getName())
                .description(park.getDescription())
                .address(park.getAddress())
                .build();
    }

    public Park createParkDtoToEntity(CreateParkDto createParkDto) {
        return Park.builder()
                .id(createParkDto.getParkId())
                .name(createParkDto.getName())
                .description(createParkDto.getDescription())
                .address(createParkDto.getAddress())
                .build();
    }
}
