package at.technikum.parkpalbackend.mapper;

import at.technikum.parkpalbackend.dto.eventdtos.CreateEventDto;
import at.technikum.parkpalbackend.dto.eventdtos.EventDto;
import at.technikum.parkpalbackend.model.*;
import at.technikum.parkpalbackend.service.*;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;


@Component
public class EventMapper {

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
                .parkId(event.getPark() != null ? event.getPark().getId() : null)
                .creatorUserId(event.getCreator() != null ? event.getCreator().getId() : null)
                .joinedUserIds(getJoinedUserIds(event.getJoinedUsers()))
                .eventTagsIds(getEventTagIds(event.getTags()))
                .mediaFileExternalIds(getFileExternalIds(event.getMedia()))
                .build();
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
                .build();
    }

    public Event toEntityCreateEvent(
            CreateEventDto createEventDto,
            User creator, Park park,
            List<File> mediaFiles,
            Set<EventTag> eventTags) {

        if (createEventDto == null) {
            throw new IllegalArgumentException("createEventDto cannot be null");
        }

        return Event.builder()
                .title(createEventDto.getTitle())
                .description(createEventDto.getDescription())
                .startTS(createEventDto.getStartTS())
                .endTS(createEventDto.getEndTS())
                .creator(creator)
                .park(park)
                .media(mediaFiles != null ? new ArrayList<>(mediaFiles) : new ArrayList<>())
                .tags(eventTags != null ? new HashSet<>(eventTags) : new HashSet<>())
                .build();
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
}
