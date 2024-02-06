package at.technikum.parkpalbackend.repository;

import at.technikum.parkpalbackend.model.Address;
import at.technikum.parkpalbackend.model.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AddressRepository extends JpaRepository<Address, String> {
    Optional<Address> findAddressByAddressId(String addressId);

    Optional<Address>findAddressByCountry(Country country);

    Optional<Address>findAddressByCity(String city);
}
