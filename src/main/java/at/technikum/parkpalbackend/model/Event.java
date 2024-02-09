package at.technikum.parkpalbackend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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

    @FutureOrPresent(message = "Start EventTime must be now or in the Future")
    @NotNull(message="Event Start Time not found. All Events need to have a Start and End Time")
    private LocalDateTime startTS;

    @FutureOrPresent(message = "End EventTime must be now or in the Future")
    @NotNull(message="Event End Time not found. All Events need to have a Start and End Time")
    private LocalDateTime endTS;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "event_park_id", foreignKey = @ForeignKey(name = "fk_event_2_park"))
    @NotNull(message = "Park not found. All Events need to take place in a Park")
    private Park park;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "event_2_user_id", foreignKey = @ForeignKey(name = "fk_event_2_user"))
    @NotNull(message = "Creator not found. All Events need to have been created by an User")
    private User creator;

    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinTable(name = "event_joined_user", joinColumns = @JoinColumn(name = "event_id",
                        foreignKey = @ForeignKey(name = "fk_joined_user_event")),
                inverseJoinColumns = @JoinColumn(name = "user_id",
                        foreignKey = @ForeignKey(name = "fk_joined_user_user")))
    @Builder.Default
    @ToString.Exclude
    private List<User> joinedUsers = new ArrayList<>();

    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinTable(name = "event_event_tag", joinColumns = @JoinColumn(name = "event_id",
            foreignKey = @ForeignKey(name = "fk_event_tag_event")),
            inverseJoinColumns = @JoinColumn(name = "event_tag_id",
                    foreignKey = @ForeignKey(name = "fk_event_event_tag_user")))
    @Builder.Default
    @ToString.Exclude
    private List<EventTag> eventTags = new ArrayList<>();

    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinTable(name = "event_event_media", joinColumns = @JoinColumn(name = "event_id",
            foreignKey = @ForeignKey(name = "fk_event_media_event")),
            inverseJoinColumns = @JoinColumn(name = "event_media_id",
                    foreignKey = @ForeignKey(name = "fk_event_media_user")))
    @Builder.Default
    @ToString.Exclude
    private List<Media> eventMedia = new ArrayList<>();

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
