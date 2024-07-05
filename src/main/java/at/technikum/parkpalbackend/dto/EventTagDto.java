package at.technikum.parkpalbackend.dto;

import at.technikum.parkpalbackend.dto.eventdtos.EventDto;
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
public class EventTagDto {

    private String id;

    @NotBlank(message = "Event Tag cannot be empty.")
    private String name;

    private Set<EventDto> eventDtoSet;

}

