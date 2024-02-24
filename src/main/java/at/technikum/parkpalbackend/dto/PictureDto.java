package at.technikum.parkpalbackend.dto;

import jakarta.persistence.Id;
import jakarta.validation.Valid;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

@Getter
@Setter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Valid
public class PictureDto {
    @Id
    @UuidGenerator
    private String pictureId;

    private String userId;
}
