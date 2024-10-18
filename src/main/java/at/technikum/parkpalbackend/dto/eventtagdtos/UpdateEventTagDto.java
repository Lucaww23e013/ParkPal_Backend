package at.technikum.parkpalbackend.dto.eventtagdtos;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import java.util.ArrayList;
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
    @Length(min = 3, max = 50, message = "Event Tag must be between 3 and 50 characters.")
    private String name;

    @Builder.Default
    private List<String> eventIds = new ArrayList<>();
}

