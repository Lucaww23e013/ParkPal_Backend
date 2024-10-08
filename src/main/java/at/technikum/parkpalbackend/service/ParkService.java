package at.technikum.parkpalbackend.service;

import at.technikum.parkpalbackend.exception.EntityNotFoundException;
import at.technikum.parkpalbackend.model.Event;
import at.technikum.parkpalbackend.model.File;
import at.technikum.parkpalbackend.model.Park;
import at.technikum.parkpalbackend.persistence.FileRepository;
import at.technikum.parkpalbackend.persistence.ParkRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ParkService {
    private final ParkRepository parkRepository;
    private final FileRepository fileRepository;
    private final EventService eventService;

    public ParkService(ParkRepository parkRepository,
                       FileRepository fileRepository,
                       EventService eventService) {
        this.parkRepository = parkRepository;
        this.fileRepository = fileRepository;
        this.eventService = eventService;
    }

    public List<Park> findAllParks() {
        return parkRepository.findAll();
    }
    public Park findParkById(String parkId) {
        return parkRepository.findById(parkId)
                .orElseThrow(() -> new EntityNotFoundException("Park with id %s not found "
                        .formatted(parkId)));
    }

    public Park save(Park park) {
        return parkRepository.save(park);
    }

    public Park updatePark(String parkId, Park updatedPark) {
        Park park = findParkById(parkId);
        updateParkDetails(park, updatedPark);
        updateParkEvents(park, updatedPark);
        updateParkFiles(park, updatedPark);
        return parkRepository.save(park);
    }

    public Park deleteParkByParkId(String parkId) {
        Park park = parkRepository.findById(parkId).orElseThrow(
                () -> new EntityNotFoundException("Park with id %s not found "
                        .formatted(parkId)));
        parkRepository.delete(park);
        return park;
    }


    private void updateParkDetails(Park park, Park updatedPark) {
        park.setName(updatedPark.getName());
        park.setDescription(updatedPark.getDescription());
        park.setAddress(updatedPark.getAddress());
    }

    private void updateParkEvents(Park park, Park updatedPark) {
        // Disassociate existing events from the park
        List<Event> parkEvents = new ArrayList<>(park.getEvents());
        if (!parkEvents.isEmpty()) {
            for (Event event : parkEvents) {
                event.setPark(null);
                eventService.save(event);
            }
            parkEvents.clear();
        }
        // Associate new events with the park
        List<Event> updatedParkEvents = new ArrayList<>(updatedPark.getEvents());
        if (!updatedParkEvents.isEmpty()) {
            for (Event event : updatedParkEvents) {
                event.setPark(park);
                eventService.save(event);
                parkEvents.add(event);
            }
        }
        park.setEvents(parkEvents);
    }

    private void updateParkFiles(Park park, Park updatedPark) {
        // Disassociate existing files from the park
        List<File> parkMedia = new ArrayList<>(park.getMedia());
        if (!parkMedia.isEmpty()) {
            for (File file : parkMedia) {
                file.setPark(null);
                fileRepository.save(file);
            }
            parkMedia.clear();
        }
        // Associate new files with the park
        List<File> updatedParkMedia = new ArrayList<>(updatedPark.getMedia());
        if (!updatedParkMedia.isEmpty()) {
            for (File file : updatedParkMedia) {
                file.setPark(park);
                fileRepository.save(file);
                parkMedia.add(file);
            }
        }
        park.setMedia(parkMedia);
    }

}
