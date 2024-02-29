package at.technikum.parkpalbackend.dto;

import jakarta.persistence.Id;
import jakarta.validation.Valid;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;

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

    private LocalDateTime uploadDate;
}
