package at.technikum.parkpalbackend.controller;

import at.technikum.parkpalbackend.dto.parkdtos.CreateParkDto;
import at.technikum.parkpalbackend.dto.parkdtos.ParkDto;
import at.technikum.parkpalbackend.mapper.ParkMapper;
import at.technikum.parkpalbackend.model.Event;
import at.technikum.parkpalbackend.model.Park;
import at.technikum.parkpalbackend.service.EventService;
import at.technikum.parkpalbackend.service.ParkService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/parks")
@CrossOrigin

//@AllArgsConstructor
public class ParkController {
    private final ParkService parkService;

    private final EventService eventService;

    private final ParkMapper parkMapper;

    public ParkController(ParkService parkService, EventService eventService, ParkMapper parkMapper){
        this.parkService = parkService;
        this.eventService = eventService;
        this.parkMapper = parkMapper;
    }

    @PostMapping("/parks")
    //@Preauthorize with Spring security later
    @ResponseStatus(HttpStatus.CREATED)
    public CreateParkDto createPark(@RequestBody @Valid CreateParkDto createParkDto){
        Park park = parkMapper.CreateParkDtoToEntity(createParkDto);
        park = parkService.save(park);
        return parkMapper.toCreateParkDto(park);
    }

    @PutMapping("/parks/{parkId}")
    //@Preauthorize with Spring security later
    public ParkDto updatePark(@PathVariable String parkId, @RequestBody ParkDto updatedParkDto){
        Park updatedPark = parkMapper.toEntity(updatedParkDto);
        updatedPark = parkService.updatePark(parkId, updatedPark);
        return parkMapper.toDto(updatedPark);
    }

    @GetMapping("/parks")
    public List<ParkDto> getAllParks() {
        List<Park> allParks = parkService.findAllParks();
        return allParks.stream().map(park -> parkMapper.toDto(park)).toList();
    }

    @GetMapping("/parks/{parkId}")
    public ParkDto getParkByParkId(@PathVariable @Valid String parkId){
        Park park = parkService.findParkByParkId(parkId);
        return parkMapper.toDto(park);
    }

    @GetMapping("/parks/{eventId}")
    public ParkDto getParkByEventId(@PathVariable @Valid String eventId){
        List<Event> selectedEvents = new ArrayList<Event>();
        Event event = eventService.findByEventId(eventId);
        selectedEvents.add(event);
        Park park = parkService.findParkByEvents(selectedEvents);
        return parkMapper.toDto(park);
    }

   @DeleteMapping("/parks/{parkId}")
   //@Preauthorize with Spring security later
    public ParkDto deleteParkByParkById(@PathVariable @Valid String parkId){
        Park park = parkService.deleteParkByParkId(parkId);
        return null;
   }




}
