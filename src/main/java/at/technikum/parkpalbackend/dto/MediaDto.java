package at.technikum.parkpalbackend.dto;

import at.technikum.parkpalbackend.model.MediaCategory;
import at.technikum.parkpalbackend.model.User;
import jakarta.validation.Valid;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
//@Builder
@Valid
public class MediaDto {

    private String mediaId;

    private User user;

    private MediaCategory mediaCategory;

}
