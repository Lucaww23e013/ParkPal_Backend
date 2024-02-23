package at.technikum.parkpalbackend.dto;

import jakarta.persistence.Id;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

@Getter
@Setter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Valid
public class MediaDto {
    @Id
    @UuidGenerator
    private String mediaId;
    @NotBlank(message = "userId not found")
    private String userId;


}
