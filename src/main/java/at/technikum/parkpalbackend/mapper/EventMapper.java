package at.technikum.parkpalbackend.mapper;

import at.technikum.parkpalbackend.dto.eventdtos.CreateEventDto;
import at.technikum.parkpalbackend.dto.eventdtos.EventDto;
import at.technikum.parkpalbackend.model.Event;
import at.technikum.parkpalbackend.model.EventTag;
import at.technikum.parkpalbackend.model.File;
import at.technikum.parkpalbackend.model.User;
import at.technikum.parkpalbackend.service.*;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Component
public class EventMapper {

    private final ParkService parkService;
    private final UserService userService;
    private final EventService eventService;
    private final EventTagService eventTagService;
    private final FileService fileService;

    public EventMapper(ParkService parkService, UserService userService, EventService eventService,
                       EventTagService eventTagService, FileService fileService) {
        this.parkService = parkService;
        this.userService = userService;
        this.eventService = eventService;
        this.eventTagService = eventTagService;
        this.fileService = fileService;
    }

    // TODO add eventFiles
    public EventDto toDto(Event event) {
        if (event == null) {
            throw new IllegalArgumentException("event cannot be null");
        }

        return EventDto.builder()
                .id(event.getId())
                .title(event.getTitle())
                .description(event.getDescription())
                .startTS(event.getStartTS())
                .endTS(event.getEndTS())
                .parkId(event.getPark() != null ?
                        event.getPark().getId() : null)
                .creatorUserId(eventService.findEventCreatorUserId(event.getId()))
                .creatorName(eventService.findEventCreatorName(event.getId()))
                .joinedUserIds(getJoinedUserIds(event.getJoinedUsers()))
                .eventTagsIds(getEventTagIds(event.getTags()))
                .eventTagNames(getEventTagNames(event.getTags()))
                .build();
    }

    public EventDto toDtoAllArgs(Event event) {
        if (event == null) {
            throw new IllegalArgumentException("event cannot be null");
        }

        return EventDto.builder()
                .id(event.getId())
                .title(event.getTitle())
                .description(event.getDescription())
                .startTS(event.getStartTS())
                .endTS(event.getEndTS())
                .parkId(event.getPark() != null ?
                        event.getPark().getId() : null)
                .creatorUserId(getCreatorUserId(event))
                .joinedUserIds(getJoinedUserIds(event.getJoinedUsers()))
                .eventTagsIds(getEventTagIds(event.getTags()))
                .mediaFileIds(getFileIdsFromMediaFiles(event.getMedia()))
                .build();
    }

    private static String getCreatorUserId(Event event) {
        if (event == null || event.getCreator() == null) {
            return null;
        }
        return event.getCreator().getId();
    }

    // TODO add eventFiles
    public CreateEventDto toDtoCreateEvent(Event event) {
        if (event == null) {
            throw new IllegalArgumentException("event cannot be null");
        }

        return CreateEventDto.builder()
                .title(event.getTitle())
                .description(event.getDescription())
                .startTS(event.getStartTS())
                .endTS(event.getEndTS())
                .parkId(event.getPark() != null ?
                        event.getPark().getId() : null)
                .creatorUserId(eventService.findEventCreatorUserId(event.getId()))
                .createMediaFileIds(getFileIdsFromMediaFiles(event.getMedia()))
                .eventTagsIds(getEventTagIds(event.getTags()))
                .eventTagNames(getEventTagNames(event.getTags()))
                .build();
    }

    public Event toEntity(EventDto eventDto) {
        return getEvent(eventDto);
    }

    public Event toEntityAllArgs(EventDto eventDto) {
        return getEvent(eventDto);
    }

    private Event getEvent(EventDto eventDto) {
        if (eventDto == null) {
            throw new IllegalArgumentException("eventDto cannot be null");
        }

        return Event.builder()
                .title(eventDto.getTitle())
                .description(eventDto.getDescription())
                .startTS(eventDto.getStartTS())
                .endTS(eventDto.getEndTS())
                .park(parkService.findParkByParkId(eventDto.getParkId()))
                .creator(userService.findByUserId(eventDto.getCreatorUserId()))
                .joinedUsers(userService.findUsersByIds(eventDto.getJoinedUserIds()))
                .tags(eventTagService.findTagsByIds(eventDto.getEventTagsIds()))
                .media(fileService.findFilesByIds(eventDto.getMediaFileIds()))
                .build();
    }

    public Event toEntityCreateEvent(CreateEventDto createEventDto) {
        if (createEventDto == null) {
            throw new IllegalArgumentException("createEventDto cannot be null");
        }

        return Event.builder()
                .title(createEventDto.getTitle())
                .description(createEventDto.getDescription())
                .startTS(createEventDto.getStartTS())
                .endTS(createEventDto.getEndTS())
                .creator(userService.findByUserId(createEventDto.getCreatorUserId()))
                .park(parkService.findParkByParkId(createEventDto.getParkId()))
                .media(fileService.findFilesByIds(createEventDto.getCreateMediaFileIds()))
                .build();
    }

    private static List<String> getJoinedUserIds(List<User> joinedUsers) {
        if (joinedUsers == null) {
            return null;
        }
        return joinedUsers.stream()
                .map(User::getId)
                .collect(Collectors.toList());
    }

    private static List<String> getFileIdsFromMediaFiles(List<File> mediaFiles) {
        if (mediaFiles == null) {
            return null;
        }
        return mediaFiles.stream()
                .map(File::getExternalId)
                .collect(Collectors.toList());
    }

    private static Set<String> getEventTagIds(Set<EventTag> eventTags) {
        if (eventTags == null) {
            return null;
        }
        return eventTags.stream()
                .map(EventTag::getId)
                .collect(Collectors.toSet());
    }

    private static Set<String> getEventTagNames(Set<EventTag> eventTags) {
        if (eventTags == null) {
            return null;
        }

        return eventTags.stream()
                .map(EventTag::getName)
                .collect(Collectors.toSet());
    }
}
