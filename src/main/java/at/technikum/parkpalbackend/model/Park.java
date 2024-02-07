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
    @Column(unique = true)
    private String parkName;

    private String description;

    @Embedded
    private Address parkAddress;

    @OneToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @ToString.Exclude
    private List<Event> parkEvents = new ArrayList<>();

    @OneToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @ToString.Exclude
    private List<Media> parkMedia = new ArrayList<>();

    public Park(String parkId, String parkName, String description, Address parkAddress,List<Event> parkEvents,List<Media> parkMedia ) {
        this.parkId = parkId;
        this.parkName = parkName;
        this.description = description;
        this.parkAddress = parkAddress;
        this.parkEvents = parkEvents;
        this.parkMedia = parkMedia;
    }


    public Park addParkEvents(Event... events) {
        Arrays.stream(events).forEach(event -> this.parkEvents.add(event));
        return this;
    }

    public Park removeParkEvents(Event... events) {
        Arrays.stream(events).forEach(event -> this.parkEvents.remove(event));
        return this;
    }

    public Park addParkMedia(Media... media) {
        Arrays.stream(media).forEach(med -> this.parkMedia.add(med));
        return this;
    }

    public Park removeParkMedia(Media... media) {
        Arrays.stream(media).forEach(med -> this.parkMedia.remove(med));
        return this;
    }

}
