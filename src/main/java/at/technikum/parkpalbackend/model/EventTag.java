package at.technikum.parkpalbackend.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;


@Getter
@Setter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder

@Entity
public class EventTag {

    @Id
    @UuidGenerator
    @Column(name = "event_tag_id")
    private String id;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @NotBlank(message = "Event Tag cannot be empty.")
    private String name;

    @ManyToMany(mappedBy = "tags", cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @ToString.Exclude
    @JsonIgnore
    private Set<Event> events = new HashSet<>();

    public void addEvent(Event event) {
        this.events.add(event);
        event.getTags().add(this);
    }

    public void removeEvent(Event event) {
        this.events.remove(event);
        event.getTags().remove(this);
    }
}
