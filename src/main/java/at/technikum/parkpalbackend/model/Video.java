package at.technikum.parkpalbackend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder

@Entity(name = "videos")
public class Video {
    @Id
    @UuidGenerator
    @Column(name = "video_id")
    private String videoId;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "user_id")
    @NotNull(message="Video must belong to a User. Pls add a User")
    private User user;

    @NotBlank(message = "File must have an uploadDate")
    private LocalDateTime uploadDate;
}
