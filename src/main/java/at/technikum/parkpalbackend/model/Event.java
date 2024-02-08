package at.technikum.parkpalbackend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jdk.jfr.Timestamp;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
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
    @UuidGenerator
    @Column(name = "event_Id")
    private String eventId;


    @NotBlank(message = "Event title not Valid")
    private String title;

    @NotBlank(message = "Event description not Valid")
    private String description;

    @Timestamp
    @FutureOrPresent
    @NotNull(message="Event Start Time not found. All Events need to have a Start and End Time")
    private LocalDateTime startTS;

    @Timestamp
    @FutureOrPresent
    @NotNull(message="Event End Time not found. All Events need to have a Start and End Time")
    private LocalDateTime endTS;

    @ManyToOne
    @NotNull(message = "Park not found. All Events need to take place in a Park")
    private Park park;

    @ManyToOne
    @NotNull(message = "Creator not found. All Events need to have been created by an User")
    private User creator;

    @OneToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @Builder.Default
    @ToString.Exclude
    private List<User> joinedUsers = new ArrayList<>();

    @OneToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REMOVE})
    // check if should also delete this if the event is deleted
    @Builder.Default
    @ToString.Exclude
    private List<EventTag> eventTags = new ArrayList<>();

    @OneToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REMOVE})
    @Builder.Default
    @ToString.Exclude
    private List<Media> eventMedia = new ArrayList<>();

//    public Event(String string) {
//        this.eventId = eventId;
//    }

    public Event addJoinedUsers(User... users) {
        Arrays.stream(users).forEach(user -> this.joinedUsers.add(user));
        return this;
    }

    public Event removeJoinedUsers(User... users) {
        Arrays.stream(users).forEach(user -> this.joinedUsers.remove(user));
        return this;
    }

    public Event addEventTags(EventTag... eventTags) {
        Arrays.stream(eventTags).forEach(eventTag -> this.eventTags.add(eventTag));
        return this;
    }

    public Event removeEventTags(EventTag... eventTags) {
        Arrays.stream(eventTags).forEach(eventTag -> this.eventTags.remove(eventTag));
        return this;
    }

    public Event addEventMedia(Media... media) {
        Arrays.stream(media).forEach(m -> this.eventMedia.add(m));
        return this;
    }

    public Event removeEventMedia(Media... media) {
        Arrays.stream(media).forEach(m -> this.eventMedia.remove(m));
        return this;
    }

}
