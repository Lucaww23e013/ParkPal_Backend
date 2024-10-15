package at.technikum.parkpalbackend.util;

import at.technikum.parkpalbackend.dto.eventdtos.CreateEventDto;
import at.technikum.parkpalbackend.mapper.EventMapper;
import at.technikum.parkpalbackend.model.Event;
import at.technikum.parkpalbackend.model.EventTag;
import at.technikum.parkpalbackend.model.File;
import at.technikum.parkpalbackend.persistence.EventRepository;
import at.technikum.parkpalbackend.service.EventTagService;
import at.technikum.parkpalbackend.service.FileService;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@Component
public class EventUtil {

    private final EventRepository eventRepository;
    private final FileService fileService;
    private final EventMapper eventMapper;
    private final EventTagService eventTagService;

    public EventUtil(EventRepository eventRepository,
                     FileService fileService,
                     EventMapper eventMapper,
                     EventTagService eventTagService) {
        this.eventRepository = eventRepository;
        this.fileService = fileService;
        this.eventMapper = eventMapper;
        this.eventTagService = eventTagService;
    }

    public CreateEventDto saveCreateEvent(CreateEventDto createEventDto) {

        List<File> mediaFiles = fileService.getFileList(createEventDto.getMediaFileExternalIds());
        Set<EventTag> eventTags = eventTagService.findTagsByIds(createEventDto.getEventTagsIds());

        Event event = eventMapper.toEntityCreateEvent(createEventDto, mediaFiles);

        event = eventRepository.save(event);

        addEventToTags(eventTags, event);
        fileService.setEventMedia(event, mediaFiles);

        return eventMapper.toDtoCreateEvent(event);
    }

    public void addEventToTags(Set<EventTag> tags, Event event) {
        for (EventTag tag : tags) {
            tag.getEvents().add(event);
        }
    }
}
