package at.technikum.parkpalbackend.dto.event;

import at.technikum.parkpalbackend.model.EventTag;
import at.technikum.parkpalbackend.model.Media;
import at.technikum.parkpalbackend.model.Park;
import at.technikum.parkpalbackend.model.User;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jdk.jfr.Timestamp;
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
public class CreateEventDto {

    @NotBlank(message = "Event title is not valid")
    private String title;

    @NotBlank(message = "Event Description is not valid")
    private String description;

    @Timestamp
    @FutureOrPresent(message = "Event Start Time must be now or in the Future")
    @NotNull(message = "Event Start Time not found. All Events need to have a Start and End Time")
    private LocalDateTime startTS;

    @Timestamp
    @FutureOrPresent(message = "Event Start Time must be now or in the Future")
    @NotNull(message = "Event End Time not found. All Events need to have a Start and End Time")
    private LocalDateTime endTS;

    @NotEmpty(message = "Park not found. All Events need to take place in a Park")
    private Park park;

    @NotEmpty(message = "Creator not found. All Events need to have been created by an User")
    private User creator;

    @ToString.Exclude
    @Setter(AccessLevel.NONE)
    private List<User> joinedUsers;

    @ToString.Exclude
    @Setter(AccessLevel.NONE)
    private List<EventTag> eventTags;

    @ToString.Exclude
    @Setter(AccessLevel.NONE)
    private List<Media> eventMedia;
}
