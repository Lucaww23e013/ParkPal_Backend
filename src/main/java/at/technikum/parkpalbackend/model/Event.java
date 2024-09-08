package at.technikum.parkpalbackend.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.UuidGenerator;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;
import java.util.*;

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
    private String id;

    @Version
    private long version;

    private String title;

    @Length(max = 1000, message = "Description is too long")
    private String description;

    private LocalDateTime startTS;

    private LocalDateTime endTS;


    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "event_park_id", foreignKey = @ForeignKey(name = "fk_event_2_park"))
    private Park park;


    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "event_2_user_id", foreignKey = @ForeignKey(name = "fk_event_2_user"))
    @OnDelete(action = OnDeleteAction.SET_NULL)
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
    private Set<EventTag> tags = new HashSet<>();

//    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
//    @JoinTable(name = "event_event_pictures", joinColumns = @JoinColumn(name = "picture_id",
//            foreignKey = @ForeignKey(name = "fk_event_pictures_event")),
//            inverseJoinColumns = @JoinColumn(name = "event_picture_id",
//                    foreignKey = @ForeignKey(name = "fk_event_picture_user")))
//    @Builder.Default
//    @ToString.Exclude
//    private List<File> eventFiles = new ArrayList<>();
//
//    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
//    @JoinTable(name = "event_event_videos", joinColumns = @JoinColumn(name = "video_id",
//            foreignKey = @ForeignKey(name = "fk_event_video_event")),
//            inverseJoinColumns = @JoinColumn(name = "event_video_id",
//                    foreignKey = @ForeignKey(name = "fk_event_video_user")))


    public Event addJoinedUsers(User... users) {
        Arrays.stream(users).forEach(user -> this.joinedUsers.add(user));
        return this;
    }

    public Event removeJoinedUsers(User... users) {
        Arrays.stream(users).forEach(user -> this.joinedUsers.remove(user));
        return this;
    }

    public Event addEventTags(EventTag... eventTags) {
        Arrays.stream(eventTags).forEach(eventTag -> this.tags.add(eventTag));
        return this;
    }

    public Event removeEventTags(EventTag... eventTags) {
        Arrays.stream(eventTags).forEach(eventTag -> this.tags.remove(eventTag));
        return this;
    }

//    public Event addEventMedia(File... media) {
//        Arrays.stream(media).forEach(m -> this.eventFiles.add(m));
//        return this;
//    }
//
//    public Event removeEventMedia(File... media) {
//        Arrays.stream(media).forEach(m -> this.eventFiles.remove(m));
//        return this;
//    }

}
