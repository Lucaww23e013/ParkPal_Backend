package at.technikum.parkpalbackend.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

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

    @NotBlank(message = "Event Tag cannot be empty.")
    private String name;

    @ManyToMany(mappedBy = "tags")
    @ToString.Exclude
    private Set<Event> events = new HashSet<>();
}
