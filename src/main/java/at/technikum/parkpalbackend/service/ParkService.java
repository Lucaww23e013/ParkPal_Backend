package at.technikum.parkpalbackend.service;

import at.technikum.parkpalbackend.exception.EntityNotFoundException;
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
        return parkRepository.findById(parkId)
                .orElseThrow(EntityNotFoundException::new);
    }

    public Park save(Park park) {
        return parkRepository.save(park);
    }

    // TODO add parkFiles
    public Park updatePark(String parkId, Park updatedPark) {
        Park park = parkRepository.findById(parkId).orElseThrow(
                () -> new EntityNotFoundException("Park with id %s not found "
                .formatted(parkId)));

        park.setId(updatedPark.getId());
        park.setName(updatedPark.getName());
        park.setDescription(updatedPark.getDescription());
        park.setAddress(updatedPark.getAddress());
        park.setParkEvents(updatedPark.getParkEvents());

        return parkRepository.save(park);
    }

    public Park deleteParkByParkId(String parkId) {
        Park park = parkRepository.findById(parkId).orElseThrow(
                () -> new EntityNotFoundException("Park with id %s not found "
                        .formatted(parkId)));
        parkRepository.delete(park);
        return park;
    }
}
