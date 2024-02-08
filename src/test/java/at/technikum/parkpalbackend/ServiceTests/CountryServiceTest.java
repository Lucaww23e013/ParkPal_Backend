package at.technikum.parkpalbackend.ServiceTests;

import at.technikum.parkpalbackend.model.Country;
import at.technikum.parkpalbackend.repository.CountryRepository;
import at.technikum.parkpalbackend.service.CountryService;
import org.junit.Test;


public class CountryServiceTest {


    private CountryService countryService;


    private CountryRepository countryRepository;


    void setUp() {

    }

    void tearDown() {
    }

    @Test
    void testSaveCountry() {
        Country countryToSave = Country.builder()
                .name("Austria")
                .iso2Code("AT")
                .build();

        Country savedCountry = countryRepository.save(countryToSave);

        //assert(savedCountry != null);
    }

    @Test
    void updateCountry() {
    }

    @Test
    void findAllCountries() {
    }

    @Test
    void findCountryByCountryId() {
    }

    @Test
    void findCountryByName() {
    }

    @Test
    void deleteCountryByCountryId() {
    }
}