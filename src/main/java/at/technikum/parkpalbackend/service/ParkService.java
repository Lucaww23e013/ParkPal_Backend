package at.technikum.parkpalbackend.service;

import at.technikum.parkpalbackend.exception.EntityNotFoundException;
import at.technikum.parkpalbackend.model.Event;
import at.technikum.parkpalbackend.model.Park;
import at.technikum.parkpalbackend.persistence.ParkRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ParkService {
    private final ParkRepository parkRepository;

    public ParkService(ParkRepository parkRepository) {
        this.parkRepository = parkRepository;
    }

    public List<Park> findAllParks() {
        return parkRepository.findAll();
    }
    public Park findParkByParkId(String parkId) {
        return parkRepository.findParkByParkId(parkId).orElseThrow(EntityNotFoundException::new);
    }

    public Park save(Park park) {
        return parkRepository.save(park);
    }

    public Park updatePark(String parkId, Park updatedPark) {
        Park park = parkRepository.findParkByParkId(parkId).orElseThrow();

        park.setParkId(updatedPark.getParkId());
        park.setName(updatedPark.getName());
        park.setDescription(updatedPark.getDescription());
        park.setAddress(updatedPark.getAddress());
        park.setParkEvents(updatedPark.getParkEvents());
        park.setParkPictures(updatedPark.getParkPictures());

        return parkRepository.save(park);
    }

    public Park deleteParkByParkId(String parkId) {
        Park park = parkRepository.findParkByParkId(parkId).orElseThrow();
        parkRepository.delete(park);
        return null;
    }

    public Park findParkByEvents(List<Event> selectedEvents) {
        return parkRepository.findByParkEventsIn(selectedEvents).orElseThrow(EntityNotFoundException::new);
    }
}
