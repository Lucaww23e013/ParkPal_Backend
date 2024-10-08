package at.technikum.parkpalbackend.dto.eventtagdtos;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Valid
public class UpdateEventTagDto {

    @NotBlank(message = "Event Tag cannot be empty.")
    private String name;

    private List<String> eventIds;
}

