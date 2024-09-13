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
    @ToString.Exclude
    private List<Event> parkEvents = new ArrayList<>();

//    @OneToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
//    @ToString.Exclude
//    private List<File> parkFiles = new ArrayList<>();

    public Park addParkEvents(Event... events) {
        Arrays.stream(events).forEach(event -> this.parkEvents.add(event));
        return this;
    }

    public Park removeParkEvents(Event... events) {
        Arrays.stream(events).forEach(event -> this.parkEvents.remove(event));
        return this;
    }

//    public Park addParkMedia(File... media) {
//        Arrays.stream(media).forEach(med -> this.parkFiles.add(med));
//        return this;
//    }
//
//    public Park removeParkMedia(File... media) {
//        Arrays.stream(media).forEach(med -> this.parkFiles.remove(med));
//        return this;
//    }

}
