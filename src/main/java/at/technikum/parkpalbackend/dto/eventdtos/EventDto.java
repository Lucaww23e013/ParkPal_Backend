package at.technikum.parkpalbackend.dto.eventdtos;

import at.technikum.parkpalbackend.model.EventTag;
import at.technikum.parkpalbackend.model.Media;
import at.technikum.parkpalbackend.model.Park;
import at.technikum.parkpalbackend.model.User;
import lombok.*;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder

@Component
public class EventDto {
    private String eventId;

    private String title;

    private String description;

    private LocalDateTime startTS;

    private LocalDateTime endTS;

    private Park park;

    private User creator;

    private List<User> joinedUsers;

    private List<Media> eventMedia;

    private List<EventTag> eventTags;
}
