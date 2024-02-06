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
import java.util.Arrays;
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

    @GetMapping("/parks")
    public List<ParkDto> getAllParks() {
        List<Park> allParks = parkService.findAllParks();
        return allParks.stream().map(park -> parkMapper.toDto(park)).toList();
    }

    @GetMapping("parks/{parkId}")
    public ParkDto getParkByParkId(@PathVariable @Valid String parkId) {
        Park park = parkService.findParkByParkId(parkId);
        return parkMapper.toDto(park);
    }

   /*@GetMapping("parks/{eventId}")
    public ParkDto getParkByEventId(){}*/


}
