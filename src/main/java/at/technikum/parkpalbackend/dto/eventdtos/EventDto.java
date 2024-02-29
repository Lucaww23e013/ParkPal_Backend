package at.technikum.parkpalbackend.dto.eventdtos;

import at.technikum.parkpalbackend.model.EventTag;
import at.technikum.parkpalbackend.model.Picture;
import at.technikum.parkpalbackend.model.User;
import at.technikum.parkpalbackend.model.Video;
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

    private String parkId;

    private User creator;

    private List<User> joinedUsers;

    private List<Picture> eventPictures;

    private List<Video> eventVideos;

    private List<EventTag> eventTags;
}
