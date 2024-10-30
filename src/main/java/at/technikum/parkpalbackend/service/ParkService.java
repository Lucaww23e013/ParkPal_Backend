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

    public Park findParkById(String parkId) {
        if (parkId == null) {
            throw new IllegalArgumentException("The park ID cannot be null");
        }

        return parkRepository.findById(parkId)
                .orElseThrow(() -> new EntityNotFoundException("Park with id %s not found"
                        .formatted(parkId)));
    }

    public Park save(Park park) {
        if (park == null) {
            throw new IllegalArgumentException("The park cannot be null.");
        }
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
