package at.technikum.parkpalbackend.mapper;

import at.technikum.parkpalbackend.dto.eventdtos.CreateEventDto;
import at.technikum.parkpalbackend.dto.eventdtos.EventDto;
import at.technikum.parkpalbackend.model.Event;
import at.technikum.parkpalbackend.model.EventTag;
import at.technikum.parkpalbackend.model.File;
import at.technikum.parkpalbackend.model.User;
import at.technikum.parkpalbackend.service.*;
import org.springframework.stereotype.Component;

import java.util.*;
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
                .joinedUserNames(eventService.findEventJoinedUserNames(event.getId()))
                .eventTagsIds(getEventTagIds(event.getTags()))
                .eventTagNames(getEventTagNames(event.getTags()))
                .mediaFileExternalIds(getFileExternalIds(event.getMedia()))
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
                .joinedUserNames(eventService.findEventJoinedUserNames(event.getId()))
                .eventTagsIds(getEventTagIds(event.getTags()))
                .mediaFileExternalIds(getFileExternalIds(event.getMedia()))
                .build();
    }

    private static String getCreatorUserId(Event event) {
        if (event == null || event.getCreator() == null) {
            return null;
        }
        return event.getCreator().getId();
    }

    public CreateEventDto toDtoCreateEvent(Event event) {
        if (event == null) {
            throw new IllegalArgumentException("event cannot be null");
        }

        return CreateEventDto.builder()
                .id(event.getId())
                .title(event.getTitle())
                .description(event.getDescription())
                .startTS(event.getStartTS())
                .endTS(event.getEndTS())
                .parkId(event.getPark() != null ? event.getPark().getId() : null)
                .creatorUserId(event.getCreator() != null ? event.getCreator().getId() : null)
                .mediaFileExternalIds(event.getMedia() != null ?
                        getFileExternalIds(event.getMedia()) : new ArrayList<>())
                .eventTagsIds(event.getTags() != null ?
                        getEventTagIds(event.getTags()) : new HashSet<>())
                .eventTagNames(event.getTags() != null ?
                        getEventTagNames(event.getTags()) : new HashSet<>())
                .build();
    }

    public Event toEntity(EventDto eventDto, Optional<String> eventId) {
        return getEvent(eventDto, eventId.orElse(null));
    }

    private Event getEvent(EventDto eventDto, String eventId) {
        if (eventDto == null) {
            throw new IllegalArgumentException("eventDto cannot be null");
        }

        Event event = getOrCreateEvent(eventId);
        List<User> newJoinedUsers = userService.findUsersByIds(eventDto.getJoinedUserIds());
        List<User> oldJoinedUsers = event.getJoinedUsers();

        updateUserAssociations(oldJoinedUsers, newJoinedUsers, event);
        updateEventDetails(event, eventDto, newJoinedUsers);

        return event;
    }

    private Event getOrCreateEvent(String eventId) {
        if (eventId != null) {
            return eventService.findByEventId(eventId);
        } else {
            return Event.builder().build();
        }
    }

    private void updateUserAssociations(List<User> oldJoinedUsers,
                                        List<User> newJoinedUsers,
                                        Event event) {
        removeOldUserAssociations(oldJoinedUsers, newJoinedUsers, event);
        addNewUserAssociations(newJoinedUsers, oldJoinedUsers, event);
    }

    private void updateEventDetails(Event event, EventDto eventDto, List<User> newJoinedUsers) {
        event.setTitle(eventDto.getTitle());
        event.setDescription(eventDto.getDescription());
        event.setStartTS(eventDto.getStartTS());
        event.setEndTS(eventDto.getEndTS());
        event.setPark(parkService.findParkById(eventDto.getParkId()));
        event.setCreator(userService.findByUserId(eventDto.getCreatorUserId()));
        event.setMedia(getFileList(eventDto.getMediaFileExternalIds()));
        event.setJoinedUsers(newJoinedUsers);

        Set<EventTag> newTags = eventTagService.findTagsByIds(eventDto.getEventTagsIds());
        Set<EventTag> currentTags = event.getTags();

        // Remove associations for tags that are no longer linked
        currentTags.stream()
                .filter(tag -> !newTags.contains(tag))
                .forEach(tag -> tag.getEvents().remove(event));

        // Add associations for new tags
        newTags.stream()
                .filter(tag -> !currentTags.contains(tag))
                .forEach(tag -> tag.getEvents().add(event));

        event.setTags(newTags);
    }

    public Event toEntityCreateEvent(CreateEventDto createEventDto, List<File> mediaFiles) {
        if (createEventDto == null) {
            throw new IllegalArgumentException("createEventDto cannot be null");
        }

        return Event.builder()
                .title(createEventDto.getTitle())
                .description(createEventDto.getDescription())
                .startTS(createEventDto.getStartTS())
                .endTS(createEventDto.getEndTS())
                .creator(userService.findByUserId(createEventDto.getCreatorUserId()))
                .park(createEventDto.getParkId() != null ?
                        parkService.findParkById(createEventDto.getParkId()) : null)
                .media(mediaFiles != null ? new ArrayList<>(mediaFiles) : new ArrayList<>())
                .tags(createEventDto.getEventTagsIds() != null
                        && !createEventDto.getEventTagsIds().isEmpty() ?
                        eventTagService.findTagsByIds(createEventDto.getEventTagsIds())
                        : new HashSet<>())
                .build();
    }


    private List<File> getFileList(List<String> mediaFileExternalIds) {
        if (mediaFileExternalIds == null) {
            return new ArrayList<>();
        }

        return mediaFileExternalIds.stream()
                .map(fileService::findFileByExternalId)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    private static List<String> getJoinedUserIds(List<User> joinedUsers) {
        if (joinedUsers == null) {
            return new ArrayList<>();
        }

        return joinedUsers.stream()
                .map(User::getId)
                .toList();
    }

    private static List<String> getFileExternalIds(List<File> mediaFiles) {
        if (mediaFiles == null) {
            return new ArrayList<>();
        }

        return mediaFiles.stream()
                .map(File::getExternalId)
                .toList();
    }

    private static Set<String> getEventTagIds(Set<EventTag> eventTags) {
        if (eventTags == null) {
            return new HashSet<>();
        }

        return eventTags.stream()
                .map(EventTag::getId)
                .collect(Collectors.toSet());
    }

    private static Set<String> getEventTagNames(Set<EventTag> eventTags) {
        if (eventTags == null) {
            return new HashSet<>();
        }

        return eventTags.stream()
                .map(EventTag::getName)
                .collect(Collectors.toSet());
    }

    private void removeOldUserAssociations(List<User> oldJoinedUsers,
                                           List<User> newJoinedUsers,
                                           Event event) {
        oldJoinedUsers.stream()
                .filter(user -> !newJoinedUsers.contains(user))
                .forEach(user -> user.removeJoinedEvents(event));
    }

    private void addNewUserAssociations(List<User> newJoinedUsers,
                                        List<User> oldJoinedUsers,
                                        Event event) {
        newJoinedUsers.stream()
                .filter(user -> !oldJoinedUsers.contains(user))
                .forEach(user -> user.addJoinedEvents(event));
    }

}
