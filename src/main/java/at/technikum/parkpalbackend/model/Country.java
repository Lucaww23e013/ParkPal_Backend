package at.technikum.parkpalbackend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

@Getter
@Setter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
////@Builder

@Entity
public class Country {

    @Id
    @UuidGenerator
    @Column(name = "country_Id")
    private String countryId;

    @NotBlank(message = "Country-Name not found")
    private String name;

    @Column(length = 3)
    private String iso2Code;

    public Country(String countryId, String name, String iso2Code) {
        this.countryId = countryId;
        this.name = name;
        this.iso2Code = iso2Code;
    }
}
