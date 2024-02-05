package at.technikum.parkpalbackend.dto;

import at.technikum.parkpalbackend.model.Media;
import at.technikum.parkpalbackend.model.Park;
import at.technikum.parkpalbackend.model.User;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class EventDto {
    private String eventId;

    private String title;

    private String description;

    private LocalDateTime startTS;

    private LocalDateTime endTS;

    private List<Media> eventMedia;

    private Park park;

    private User creator;

    private List<User> joinedUsers;
}
