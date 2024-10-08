package at.technikum.parkpalbackend.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

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
public class Park {

    @Id
    @UuidGenerator
    @Column(name = "park_id")
    private String id;

    @Column(unique = true, length = 65535)
    private String name;

    @Column(length = 65535)
    private String description;

    @Embedded
    private Address address;

    @OneToMany(mappedBy = "park", cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @Builder.Default
    @ToString.Exclude
    private List<Event> events = new ArrayList<>();

    @OneToMany(mappedBy = "park", cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @Builder.Default
    @ToString.Exclude
    private List<File> media = new ArrayList<>();

    public Park addParkEvents(Event... events) {
        this.events.addAll(Arrays.asList(events));
        return this;
    }

    public Park removeParkEvents(Event... events) {
        Arrays.stream(events).forEach(event -> this.events.remove(event));
        return this;
    }

    public Park addMedia(File... media) {
        this.media.addAll(Arrays.asList(media));
        return this;
    }

    public Park removeMedia(File... media) {
        Arrays.stream(media).forEach(med -> this.media.remove(med));
        return this;
    }

}
