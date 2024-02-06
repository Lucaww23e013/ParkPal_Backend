package at.technikum.parkpalbackend.mapper;

import at.technikum.parkpalbackend.dto.AddressDto;
import at.technikum.parkpalbackend.model.Address;
import org.springframework.stereotype.Component;

@Component
public class AddressMapper {
    public AddressDto toDto(Address address){
        return new AddressDto(
                address.getAddressId(),
                address.getStreetNumber(),
                address.getZipCode(),
                address.getCity(),
                address.getCountry()
        );
    }

    public Address toEntity(AddressDto addressDto) {
        return new Address(
                addressDto.getAddressId(),
                addressDto.getStreetNumber(),
                addressDto.getZipCode(),
                addressDto.getCity(),
                addressDto.getCountry()
        );
    }
}
