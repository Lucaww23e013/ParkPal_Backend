package at.technikum.parkpalbackend.model;

import at.technikum.parkpalbackend.listener.FileEntityListener;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder

@Entity
@EntityListeners(FileEntityListener.class)
public class File {

    @Version
    private long version;

    @Id
    @UuidGenerator
    @Column(name = "file_id")
    private String id;

    private String externalId;

    @Column(length = 2048)
    private String path;

    @Column(length = 2048)
    private String filename;

    @Builder.Default
    private LocalDateTime uploadDate = LocalDateTime.now();

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "user_id")
    private User user;

    private boolean assigned;
}
