package at.technikum.parkpalbackend.controller;

import at.technikum.parkpalbackend.dto.ParkDto;
import at.technikum.parkpalbackend.dto.UserDto;
import at.technikum.parkpalbackend.mapper.ParkMapper;
import at.technikum.parkpalbackend.model.Park;
import at.technikum.parkpalbackend.model.User;
import at.technikum.parkpalbackend.service.ParkService;
import jakarta.validation.Valid;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/parks")
@CrossOrigin

//@AllArgsConstructor
public class ParkController {
    private final ParkService parkService;

    private final ParkMapper parkMapper;

    public ParkController(ParkService parkService, ParkMapper parkMapper){
        this.parkService = parkService;
        this.parkMapper = parkMapper;
    }

    /*@GetMapping("/parks")
    public List<ParkDto> getAllParks(@PathVariable String parkId) {

        return ;
    }*/

    @GetMapping("parks/{parkName}")
    public ParkDto getUserByName(@PathVariable @Valid String parkName) {
        Park park = parkService.findParkByParkName(parkName);
        return parkMapper.toDto(park);
    }

}
