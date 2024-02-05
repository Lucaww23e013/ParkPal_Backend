package at.technikum.parkpalbackend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jdk.jfr.Timestamp;
import lombok.*;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder

@Entity
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String eventId;


    @NotEmpty(message = "Event title not found")
    private String title;


    private String description;

    @Timestamp
    @NotNull(message="Event Start Time not found. All Events need to have a Start and End Time")
    private LocalDateTime startTS;

    @Timestamp
    @NotNull(message="Event End Time not found. All Events need to have a Start and End Time")
    private LocalDateTime endTS;

    @OneToMany
    @ToString.Exclude
    private List<Media> eventMedia;

    @ManyToOne
    @NotEmpty(message = "Park not found. All Events need to take place in a Park")
    private Park park;

    @ManyToOne
    @NotEmpty(message = "Creator not found. All Events need to have been created by an User")
    private User creator;

    @OneToMany
    @ToString.Exclude
    private List<User> joinedUsers;


}
