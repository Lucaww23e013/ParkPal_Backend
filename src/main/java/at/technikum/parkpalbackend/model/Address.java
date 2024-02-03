package at.technikum.parkpalbackend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder

@Entity
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @NotNull(message = "addressId not found")
    private String addressId;

    @NotEmpty(message = "Street and Number not found")
    private String streetNumber;

    @NotEmpty(message = "Zip Code not found")
    @Min(value = 4, message = "Zip Code cant be less than 4 Characters")
    @Max(value = 10, message = "Zip Code cant be more than 10 Characters")
    private String zipCode;

    @NotEmpty(message= "City not found")
    private String city;

    @ManyToOne
    @JoinColumn(name = "country_iso_2_code")
    private Country country;

}
