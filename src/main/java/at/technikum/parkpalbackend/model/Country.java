package at.technikum.parkpalbackend.model;

import jakarta.persistence.*;
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
    @Column(name = "country_id")
    private String countryId;

    private String name;

    //@Column(length = 3, unique = true)
    private String iso2Code;


}
