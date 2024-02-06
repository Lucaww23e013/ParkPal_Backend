package at.technikum.parkpalbackend.service;

import at.technikum.parkpalbackend.exception.EntityNotFoundException;
import at.technikum.parkpalbackend.model.EventTag;
import at.technikum.parkpalbackend.repository.EventTagRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EventTagService {

    private final EventTagRepository eventTagRepository;

    public EventTagService(EventTagRepository eventTagRepository) {
        this.eventTagRepository = eventTagRepository;
    }

    public List<EventTag> findAllTags() {
        return eventTagRepository.findAll();
    }

    public EventTag save(EventTag eventTag) {
        return eventTagRepository.save(eventTag);
    }

    public EventTag findTagById(String eventTagId) {
        // to rethink if i should also save eventID by the entity EventTag
        return eventTagRepository.findById(eventTagId).orElseThrow(EntityNotFoundException::new);
    }
}
