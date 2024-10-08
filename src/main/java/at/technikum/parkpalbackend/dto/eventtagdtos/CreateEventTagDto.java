package at.technikum.parkpalbackend.dto.eventtagdtos;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Valid
public class CreateEventTagDto {

    @NotBlank(message = "Event Tag Name cannot be empty.")
    private String name;

    private Set<String> eventIds;
}

