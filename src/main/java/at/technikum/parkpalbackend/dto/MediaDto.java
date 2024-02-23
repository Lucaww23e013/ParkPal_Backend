package at.technikum.parkpalbackend.dto;

import at.technikum.parkpalbackend.model.MediaCategory;
import jakarta.persistence.Id;
import jakarta.validation.Valid;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

@Getter
@Setter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
//@Builder
@Valid
public class MediaDto {
    @Id
    @UuidGenerator
    private String mediaId;

    private String userId;

    private MediaCategory mediaCategory;

}
