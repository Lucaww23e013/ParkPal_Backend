package at.technikum.parkpalbackend.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder

@Entity
public class UserJoinEvent {
    @Id
    @SequenceGenerator(name = "", initialValue = 1, allocationSize = 2000)
    @NotNull(message = "userJoinEvent not found")
    private String userJoinEventId;

    @ManyToOne
    @JoinColumn(name = "user_user_id")
    @NotEmpty(message = "user of this media not found")
    private User user;


    @ManyToOne
    @JoinColumn(name = "media_media_id")
    @NotEmpty(message = "media for this user not found")
    private Media media;
}
