package at.technikum.parkpalbackend.dto;

import at.technikum.parkpalbackend.model.Country;
import lombok.*;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class AddressDto {
    private String addressId;

    private String streetNumber;

    private String zipCode;

    private String city;

    private Country country;

}
