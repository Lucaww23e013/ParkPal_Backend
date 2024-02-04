package at.technikum.parkpalbackend.dto;

import at.technikum.parkpalbackend.model.Media;
import at.technikum.parkpalbackend.model.User;
import lombok.*;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class UserJoinEventDt0 {
    private String userJoinEventId;

    private User user;

    private Media media;
}
