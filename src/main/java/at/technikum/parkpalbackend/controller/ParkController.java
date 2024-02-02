package at.technikum.parkpalbackend.controller;

import at.technikum.parkpalbackend.dto.ParkDto;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/parks")
@CrossOrigin

//@AllArgsConstructor
@NoArgsConstructor
public class ParkController {

    @GetMapping
    public List<ParkDto> readAll() {
        List<ParkDto> parks = new ArrayList<>();
        parks.add(new ParkDto("1", "park pro"));
        parks.add(new ParkDto("2", "park luca"));
        return parks;
    }

}
