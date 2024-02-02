package at.technikum.parkpalbackend.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder

@Entity
public class Country {
    @Id
    @UuidGenerator
    @NotNull(message = "name cant be null")
    private String countryId;

    @NotBlank(message = "Country-Name not found")
    private String name;

    @NotEmpty(message = "Country iso2Code not found")
    private String iso2Code;
}
