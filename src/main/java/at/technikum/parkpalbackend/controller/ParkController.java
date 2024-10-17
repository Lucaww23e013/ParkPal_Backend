package at.technikum.parkpalbackend.controller;

import at.technikum.parkpalbackend.dto.parkdtos.CreateParkDto;
import at.technikum.parkpalbackend.dto.parkdtos.ParkDto;
import at.technikum.parkpalbackend.mapper.ParkMapper;
import at.technikum.parkpalbackend.model.Park;
import at.technikum.parkpalbackend.service.ParkService;
import at.technikum.parkpalbackend.util.ParkUtil;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/parks")
@CrossOrigin

public class ParkController {

    private final ParkService parkService;
    private final ParkMapper parkMapper;
    private final ParkUtil parkUtil;

    public ParkController(ParkService parkService,
                          ParkMapper parkMapper,
                          ParkUtil parkUtil){
        this.parkService = parkService;
        this.parkMapper = parkMapper;
        this.parkUtil = parkUtil;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CreateParkDto createNewPark(@RequestBody @Valid CreateParkDto createParkDto){
        return parkUtil.saveCreatePark(createParkDto);
    }

    @GetMapping
    public ResponseEntity<List<ParkDto>> getAllParks() {
        List<Park> allParks = parkService.findAllParks();
        if (allParks.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        List<ParkDto> parkDtos = allParks.stream()
                .map(park -> parkMapper.toDto(park))
                .toList();
        return ResponseEntity.ok(parkDtos);
    }

    @GetMapping("/{parkId}")
    public ParkDto getParkByParkId(@PathVariable String parkId){
        Park park = parkService.findParkById(parkId);
        return parkMapper.toDto(park);
    }

    @PutMapping("/{parkId}")
    public ResponseEntity<?> updatePark(
            @RequestBody @Valid ParkDto parkDto, @PathVariable String parkId) {
        Park updatedPark = parkUtil.updatePark(parkId, parkDto);
        return ResponseEntity.ok(parkMapper.toDto(updatedPark));
    }

    @DeleteMapping("/{parkId}")
    public ResponseEntity<Void> deleteParkById(@PathVariable String parkId){
        parkService.deleteParkByParkId(parkId);
        return ResponseEntity.noContent().build();
    }

}
