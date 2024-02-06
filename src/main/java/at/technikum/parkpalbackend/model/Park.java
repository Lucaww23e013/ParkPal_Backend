package at.technikum.parkpalbackend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)


@Entity
public class Park {

    @Id
    @UuidGenerator
    @Column(name = "park_id")
    private String parkId;

    @NotBlank(message="Park name not found. All parks need a name")
    private String name;

    private String description;

    @Embedded
    private Address parkAddress;

    @OneToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @ToString.Exclude
    private List<Event> parkEvents = new ArrayList<>();

    @OneToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @ToString.Exclude
    private List<Media> parkMedia = new ArrayList<>();

    public Park(String name, String description, Address parkAddress) {
        this.name = name;
        this.description = description;
        this.parkAddress = parkAddress;
    }

    public Park addParkEvents(Event... events) {
        Arrays.stream(events).forEach(event -> this.parkEvents.add(event));
        return this;
    }

    public Park addParkMedia(Media... media) {
        Arrays.stream(media).forEach(med -> this.parkMedia.add(med));
        return this;
    }

}
