package at.technikum.parkpalbackend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder

@Entity
public class Country {

    @Id
    @Column(name = "country_Id")
    private String countryId;

    @NotBlank(message = "Country-Name not found")
    private String name;

    //@Column(length = 3, unique = true)
    private String iso2Code;


}
