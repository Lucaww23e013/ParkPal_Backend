package at.technikum.parkpalbackend.model;

import at.technikum.parkpalbackend.listener.FileEntityListener;
import at.technikum.parkpalbackend.model.enums.FileType;
import jakarta.persistence.CascadeType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.*;

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

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    private String externalId;

    @Column(length = 2048)
    private String path;

    @Column(length = 2048)
    private String filename;

    @Builder.Default
    private LocalDateTime uploadDate = LocalDateTime.now();

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "fk_user_2_file"))
    @OnDelete(action = OnDeleteAction.SET_NULL)
    private User user;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "event_id", foreignKey = @ForeignKey(name = "fk_event_2_file"))
    @OnDelete(action = OnDeleteAction.SET_NULL)
    private Event event;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "park_id", foreignKey = @ForeignKey(name = "fk_park_2_file"))
    @OnDelete(action = OnDeleteAction.SET_NULL)
    private Park park;

    private boolean assigned;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private FileType fileType = FileType.OTHER;

    public File(String string, String image, String contentType, Park park) {
    }
}
