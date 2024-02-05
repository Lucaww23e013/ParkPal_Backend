package at.technikum.parkpalbackend.dto;

import at.technikum.parkpalbackend.model.Country;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.ManyToOne;
import lombok.*;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class AddressDto {


    private String streetNumber;
    private String zipCode;
    private String city;

    private Country country;

}
