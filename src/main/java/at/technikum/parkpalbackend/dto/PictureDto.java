package at.technikum.parkpalbackend.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Valid
public class PictureDto {

    private String pictureId;

    @NotNull(message="Picture must belong to a User. Please add a User")
    private String userId;

    @NotBlank(message = "File must have an uploadDate")
    private LocalDateTime uploadDate;
}
