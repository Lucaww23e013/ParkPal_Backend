package at.technikum.parkpalbackend.service;

import at.technikum.parkpalbackend.exception.EntityAlreadyExistsException;
import at.technikum.parkpalbackend.exception.EntityNotFoundException;
import at.technikum.parkpalbackend.model.Park;
import at.technikum.parkpalbackend.persistence.EventRepository;
import at.technikum.parkpalbackend.persistence.ParkRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ParkService {
    private final ParkRepository parkRepository;
    private final EventRepository eventRepository;

    public ParkService(ParkRepository parkRepository, EventRepository eventRepository) {
        this.parkRepository = parkRepository;
        this.eventRepository = eventRepository;
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
        try {
            return parkRepository.save(park);
        } catch (DataIntegrityViolationException e) {
            if (e.getCause() instanceof org.hibernate.exception.ConstraintViolationException) {
                throw new EntityAlreadyExistsException(
                        "A park with the name '" + park.getName() + "' already exists.");
            }
            throw e; // rethrow if it's a different exception
        }
    }

    public Park deleteParkByParkId(String parkId) {
        if (parkId == null) {
            throw new IllegalArgumentException("The park ID cannot be null");
        }
        Park park = parkRepository.findById(parkId).orElseThrow(
                () -> new EntityNotFoundException("Park with id %s not found "
                        .formatted(parkId)));

        // Delete related events first
        eventRepository.deleteAll(park.getEvents());

        parkRepository.delete(park);
        return park;
    }
}
