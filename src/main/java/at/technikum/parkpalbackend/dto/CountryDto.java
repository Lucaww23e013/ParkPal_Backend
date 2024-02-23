package at.technikum.parkpalbackend.dto;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;
import org.hibernate.validator.constraints.UniqueElements;

@Getter
@Setter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Valid
public class CountryDto {
    @Id
    @UuidGenerator
    private String countryId;

    @NotBlank(message = "Country-Name not found")
    private String name;

    //@Column(length = 3, unique = true)
    private String iso2Code;

}
