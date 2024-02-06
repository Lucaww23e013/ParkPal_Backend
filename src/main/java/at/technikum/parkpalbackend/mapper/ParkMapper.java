package at.technikum.parkpalbackend.mapper;

import at.technikum.parkpalbackend.dto.ParkDto;
import at.technikum.parkpalbackend.model.Park;
import org.springframework.stereotype.Component;

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
}
