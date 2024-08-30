package at.technikum.parkpalbackend.dto.eventdtos;

import at.technikum.parkpalbackend.model.EventTag;
import at.technikum.parkpalbackend.model.User;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jdk.jfr.Timestamp;
import lombok.*;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder

@Component
public class EventDto {
    private String id;

    @NotBlank(message = "Event title not Valid")
    private String title;

    @NotBlank(message = "Event description not Valid")
    private String description;

    @Timestamp
    @FutureOrPresent(message = "Start EventTime must be now or in the Future")
    @NotNull(message="Event Start Time not found. All Events need to have a Start and End Time")
    private LocalDateTime startTS;

    @Timestamp
    @FutureOrPresent(message = "End EventTime must be now or in the Future")
    @NotNull(message="Event End Time not found. All Events need to have a Start and End Time")
    private LocalDateTime endTS;

    @NotNull(message = "Park not found. All Events need to take place in a Park")
    private String parkId;

    @NotNull(message = "Creator not found. All Events need to have been created by an User")
    private User creator;

    private List<User> joinedUsers;

    //private List<File> eventFiles;

    private Set<EventTag> eventTags;
}
