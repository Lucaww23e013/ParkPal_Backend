package at.technikum.parkpalbackend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
    @GeneratedValue(strategy = GenerationType.UUID)
    private String countryID;


    @NotBlank(message = "Country-Name not found")
    private String name;

    @Column(length = 3)
    private String iso2Code;
}
