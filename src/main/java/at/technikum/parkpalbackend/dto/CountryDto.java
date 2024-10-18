package at.technikum.parkpalbackend.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Valid

public class CountryDto {

    private String id;

    @NotBlank(message = "Country-Name not found")
    private String name;

    private String iso2Code;
}
