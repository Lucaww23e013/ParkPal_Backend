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

    private String countryId;

    @NotBlank(message = "Country-Name not found")
    private String name;

    //@Column(length = 3, unique = true)
    private String iso2Code;

}
