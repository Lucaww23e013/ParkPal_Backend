package at.technikum.parkpalbackend.mapper;

import at.technikum.parkpalbackend.dto.eventdtos.CreateEventDto;
import at.technikum.parkpalbackend.dto.eventdtos.EventDto;
import at.technikum.parkpalbackend.model.Event;
import at.technikum.parkpalbackend.model.User;
import at.technikum.parkpalbackend.service.ParkService;
import org.springframework.stereotype.Component;

@Component
public class EventMapper {

    private ParkService parkService;

    public EventMapper(ParkService parkService) {
        this.parkService = parkService;
    }

    public EventDto toDto(Event event) {
        return EventDto.builder()
                .id(event.getId())
                .title(event.getTitle())
                .description(event.getDescription())
                .startTS(event.getStartTS())
                .endTS(event.getEndTS())
                .parkId(event.getPark().getId())
                .creator(event.getCreator())
                .eventFiles(event.getEventFiles())
                .build();
    }

    public EventDto toDtoAllArgs(Event event) {
        return EventDto.builder()
                .id(event.getId())
                .title(event.getTitle())
                .description(event.getDescription())
                .startTS(event.getStartTS())
                .endTS(event.getEndTS())
                .parkId(event.getPark().getId())
                .creator(event.getCreator())
                .joinedUsers(event.getJoinedUsers())
                .eventTags(event.getTags())
                .eventFiles(event.getEventFiles())
                .build();
    }

    public CreateEventDto toDtoCreateEvent(Event event) {
        return CreateEventDto.builder()
                .title(event.getTitle())
                .description(event.getDescription())
                .startTS(event.getStartTS())
                .endTS(event.getEndTS())
                .parkId(event.getPark().getId())
                .creatorUserId(event.getCreator().getId())
//                .joinedUsers(event.getJoinedUsers())
//                .eventTags(event.getEventTags())
                .build();
    }

    public Event toEntity(EventDto eventDto) {
        return Event.builder()
                .title(eventDto.getTitle())
                .description(eventDto.getDescription())
                .startTS(eventDto.getStartTS())
                .endTS(eventDto.getEndTS())
                .park(parkService.findParkByParkId(eventDto.getParkId()))
                .creator(eventDto.getCreator())
                .build();
    }

    public Event toEntityAllArgs(EventDto eventDto) {
        return Event.builder()
                .title(eventDto.getTitle())
                .description(eventDto.getDescription())
                .startTS(eventDto.getStartTS())
                .endTS(eventDto.getEndTS())
                .park(parkService.findParkByParkId(eventDto.getParkId()))
                .creator(eventDto.getCreator())
                .joinedUsers(eventDto.getJoinedUsers())
                .tags(eventDto.getEventTags())
                .eventFiles(eventDto.getEventFiles())
                .build();
    }

    public Event toEntityCreateEvent(CreateEventDto createEventDto) {
        return Event.builder()
                .title(createEventDto.getTitle())
                .description(createEventDto.getDescription())
                .startTS(createEventDto.getStartTS())
                .endTS(createEventDto.getEndTS())
                .creator(User.builder().id(createEventDto.getCreatorUserId()).build())
                .park(parkService.findParkByParkId(createEventDto.getParkId()))
                .build();
    }
}
