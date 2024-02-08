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
        return new ParkDto(
                park.getParkId(),
                park.getParkName(),
                park.getDescription(),
                park.getParkAddress(),
                park.getParkEvents(),
                park.getParkMedia()
        );
    }

    public Park toEntity(ParkDto parkDto) {
        return new Park(
                parkDto.getParkId(),
                parkDto.getParkName(),
                parkDto.getDescription(),
                parkDto.getParkAddress(),
                parkDto.getParkEvents(),
                parkDto.getParkMedia()
        );
    }

    public CreateParkDto toCreateParkDto(Park park){
        return new CreateParkDto(
                park.getParkId(),
                park.getParkName(),
                park.getDescription(),
                park.getParkAddress()
        );
    }

    public Park CreateParkDtoToEntity(CreateParkDto createParkDto) {
       // List<Event> parkEvents = new ArrayList<>(); -- use if we wanna send an empty list
       // List<Media> parkMedia = new ArrayList<>();
        return new Park(
                createParkDto.getParkId(),
                createParkDto.getParkName(),
                createParkDto.getDescription(),
                createParkDto.getParkAddress(),
                null,
                null
        );
    }
}
