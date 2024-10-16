package at.technikum.parkpalbackend.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder

@Entity
@Table(name = "park", uniqueConstraints = {
    @UniqueConstraint(name = "unique_parkName", columnNames = "name")
})
public class Park {

    @Id
    @UuidGenerator
    @Column(name = "park_id")
    private String id;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @Version
    private Long version;

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

}
