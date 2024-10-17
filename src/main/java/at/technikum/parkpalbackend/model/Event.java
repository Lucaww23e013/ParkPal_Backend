package at.technikum.parkpalbackend.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.*;
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
    private Long version;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    private String title;

    @Length(max = 1000, message = "Description is too long")
    private String description;

    private LocalDateTime startTS;

    private LocalDateTime endTS;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "event_park_id", foreignKey = @ForeignKey(name = "fk_event_2_park"))
    @OnDelete(action = OnDeleteAction.SET_NULL)
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

    @OneToMany(mappedBy = "event", cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @Builder.Default
    @ToString.Exclude
    private List<File> media = new ArrayList<>();


    public Event addJoinedUsers(User... users) {
        if (users == null) {
            return this;
        }
        this.joinedUsers.addAll(Arrays.asList(users));
        return this;
    }

    public Event removeJoinedUsers(User... users) {
        if (users == null) {
            return this;
        }
        Set<User> usersToRemove = new HashSet<>(Arrays.asList(users));
        this.joinedUsers.removeIf(usersToRemove::contains);
        return this;
    }

    public Event addEventTags(EventTag... eventTags) {
        if (eventTags == null) {
            return this;
        }
        this.tags.addAll(new HashSet<>(Arrays.asList(eventTags)));
        return this;
    }

    public Event removeEventTags(EventTag... eventTags) {
        if (eventTags == null) {
            return this;
        }
        Set<EventTag> eventTagsToRemove = new HashSet<>(Arrays.asList(eventTags));
        this.tags.removeIf(eventTagsToRemove::contains);
        return this;
    }

    public Event addMedia(File... media) {
        if (media == null) {
            return this;
        }
        this.media.addAll(Arrays.asList(media));
        return this;
    }

}
