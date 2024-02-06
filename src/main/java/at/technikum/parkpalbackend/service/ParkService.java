package at.technikum.parkpalbackend.service;

import at.technikum.parkpalbackend.model.Park;
import at.technikum.parkpalbackend.model.User;
import at.technikum.parkpalbackend.repository.ParkRepository;
import at.technikum.parkpalbackend.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class ParkService {
    private final ParkRepository parkRepository;

    public ParkService(ParkRepository parkRepository) {
        this.parkRepository = parkRepository;
    }

    public Park findParkByParkName(String parkName) {
        return parkRepository.findParkByParkName(parkName).orElseThrow();
    }

}
