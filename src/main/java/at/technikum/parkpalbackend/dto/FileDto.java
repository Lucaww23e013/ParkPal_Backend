package at.technikum.parkpalbackend.dto;

import jakarta.validation.Valid;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Valid
public class FileDto {

    private String id;

    //@NotNull(message="File must belong to a User. Please add a User")
    private String userId;

    private String evenId;

    @Builder.Default
    private LocalDateTime uploadDate = LocalDateTime.now();
}
