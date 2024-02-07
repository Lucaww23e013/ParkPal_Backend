package at.technikum.parkpalbackend.dto;

import at.technikum.parkpalbackend.model.Country;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
//@AllArgsConstructor
//@Builder
@Valid
public class AddressDto {

    @Id
    @NotEmpty(message = "Address-Id not found")
    private String addressId;


    @NotEmpty(message = "Street and Number not found")
    private String streetNumber;

    @NotEmpty(message = "Zip Code not found")
    @Min(value = 4, message = "Zip Code cant be less than 4 Characters")
    @Max(value = 10, message = "Zip Code cant be more than 10 Characters")
    private String zipCode;

    private String city;

    private Country country;

    public AddressDto(String addressId, String streetNumber, String zipCode, String city, Country country) {
        this.addressId = addressId;
        this.streetNumber = streetNumber;
        this.zipCode = zipCode;
        this.city = city;
        this.country = country;
    }

}
