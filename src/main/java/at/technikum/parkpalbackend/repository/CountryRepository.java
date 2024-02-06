package at.technikum.parkpalbackend.repository;

import at.technikum.parkpalbackend.model.Country;
import at.technikum.parkpalbackend.model.Park;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CountryRepository extends JpaRepository<Country, String> {

    Optional<Country>findCountryByCountryId(String countryId);

    Optional<Country>findCountryByName(String name);

}
