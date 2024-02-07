package at.technikum.parkpalbackend.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Valid
public class EventTagDto {

    private String eventTagId;

    @NotBlank(message = "Event Tag cannot be empty.")
    private String tagName;

}

