package at.technikum.parkpalbackend.model;

import at.technikum.parkpalbackend.model.enums.Gender;
import at.technikum.parkpalbackend.model.enums.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
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
@Table(name = "user", uniqueConstraints = {
    @UniqueConstraint(name = "unique_username", columnNames = "user_name"),
    @UniqueConstraint(name = "unique_email", columnNames = "email")
})
public class User {

    @Id
    @UuidGenerator
    @Column(name = "user_id")
    private String id;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private String salutation;

    @Column(unique = true, name = "user_name")
    private String userName;

    private String firstName;

    private String lastName;

    @Email(message = "Email is not valid")
    @Column(unique = true, name = "email")
    private String email;

    @Pattern(regexp = "^(?=.*[a-z])" +
            "(?=.*\\d)" +
            "(?=.*[A-Z])" +
            "(?=.*[@#$%^&+=!])" +
            "(?=\\S+$).+$",
            message = "Password must contain at least one lowercase letter & one uppercase letter."
                    + "One number and one special character")
    @Size(min = 12, message = "Password must be at least 12 characters long")
    private String password;

    @Column(columnDefinition = "boolean default false")
    private boolean locked;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "country_id", foreignKey = @ForeignKey(name = "fk_user_2_country"))
    private Country country;

    @Enumerated(EnumType.STRING)
    private Role role;

    @ManyToMany(mappedBy = "joinedUsers",cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @Builder.Default
    @ToString.Exclude
    @JsonIgnore
    private List<Event> joinedEvents = new ArrayList<>();

    @OneToMany(mappedBy = "creator")
    @Builder.Default
    @ToString.Exclude
    private List<Event> createdEvents = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @Builder.Default
    @ToString.Exclude
    private List<File> media = new ArrayList<>();

    public User addJoinedEvents(Event... events) {
        this.joinedEvents.addAll(Arrays.asList(events));
        return this;
    }

    public User removeJoinedEvents(Event... events) {
        Arrays.stream(events).forEach(event -> this.joinedEvents.remove(event));
        return this;
    }

    public User addMedia(File... media) {
        this.media.addAll(Arrays.asList(media));
        return this;
    }

    public User removeMedia(File... media) {
        Arrays.stream(media).forEach(med -> this.media.remove(med));
        return this;
    }

}
