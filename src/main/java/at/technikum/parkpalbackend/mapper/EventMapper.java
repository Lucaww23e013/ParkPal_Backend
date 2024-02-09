package at.technikum.parkpalbackend.mapper;

import at.technikum.parkpalbackend.dto.eventdtos.CreateEventDto;
import at.technikum.parkpalbackend.dto.eventdtos.EventDto;
import at.technikum.parkpalbackend.model.Event;
import at.technikum.parkpalbackend.model.Park;
import at.technikum.parkpalbackend.model.User;
import org.springframework.stereotype.Component;

@Component
public class EventMapper {

    public EventDto toDto(Event event) {
        return EventDto.builder()
                .eventId(event.getEventId())
                .title(event.getTitle())
                .description(event.getDescription())
                .startTS(event.getStartTS())
                .endTS(event.getEndTS())
                .park(event.getPark())
                .creator(event.getCreator())
                .build();
    }

    public EventDto toDtoAllArgs(Event event) {
        return EventDto.builder()
                .eventId(event.getEventId())
                .title(event.getTitle())
                .description(event.getDescription())
                .startTS(event.getStartTS())
                .endTS(event.getEndTS())
                .park(event.getPark())
                .creator(event.getCreator())
                .joinedUsers(event.getJoinedUsers())
                .eventTags(event.getEventTags())
                .eventMedia(event.getEventMedia())
                .build();
    }

    public CreateEventDto toDtoCreateEvent(Event event) {
        return CreateEventDto.builder()
                .title(event.getTitle())
                .description(event.getDescription())
                .startTS(event.getStartTS())
                .endTS(event.getEndTS())
                .parkId(event.getPark().getParkId())
                .creatorUserId(event.getCreator().getUserId())
//                .joinedUsers(event.getJoinedUsers())
//                .eventTags(event.getEventTags())
//                .eventMedia(event.getEventMedia())
                .build();
    }

    public Event toEntity(EventDto eventDto) {
        return Event.builder()
                .title(eventDto.getTitle())
                .description(eventDto.getDescription())
                .startTS(eventDto.getStartTS())
                .endTS(eventDto.getEndTS())
                .park(eventDto.getPark())
                .creator(eventDto.getCreator())
                .build();
    }

    public Event toEntityAllArgs(EventDto eventDto) {
        return Event.builder()
                .title(eventDto.getTitle())
                .description(eventDto.getDescription())
                .startTS(eventDto.getStartTS())
                .endTS(eventDto.getEndTS())
                .park(eventDto.getPark())
                .creator(eventDto.getCreator())
                .joinedUsers(eventDto.getJoinedUsers())
                .eventTags(eventDto.getEventTags())
                .eventMedia(eventDto.getEventMedia())
                .build();
    }

    public Event toEntityCreateEvent(CreateEventDto createEventDto) {
        return Event.builder()
                .title(createEventDto.getTitle())
                .description(createEventDto.getDescription())
                .startTS(createEventDto.getStartTS())
                .endTS(createEventDto.getEndTS())
                .creator(User.builder().userId(createEventDto.getCreatorUserId()).build())
                .park(Park.builder().parkId(createEventDto.getParkId()).build())
                .build();
    }


}
