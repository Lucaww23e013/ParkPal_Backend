package at.technikum.parkpalbackend.RepositoryTests;

import at.technikum.parkpalbackend.model.Country;
import at.technikum.parkpalbackend.persistence.CountryRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;



import static at.technikum.parkpalbackend.TestFixtures.austria;
import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest
//@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
class CountryRepositoryTest {

    @Autowired
    public CountryRepositoryTest(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }

    private  CountryRepository countryRepository;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void saveCountries(){
        Country country = austria;

        Country savedCountry = countryRepository.save(country);

        Assertions.assertThat(savedCountry).isNotNull();
        Assertions.assertThat(savedCountry.getCountryId()).isNotNull();
        Assertions.assertThat(savedCountry.getName()).isNotNull();
        Assertions.assertThat(savedCountry.getIso2Code()).isNotNull();
    }
    @Test
    void CountryRepository_findCountryByCountryId() {
        Country testCountry = austria;

        countryRepository.save(austria);

        Country foundCountry = countryRepository.findCountryByCountryId(testCountry.getCountryId()).orElseThrow();

        assertEquals(foundCountry.getCountryId(), testCountry.getCountryId());
        assertEquals(foundCountry.getName(), testCountry.getName());
        assertEquals(foundCountry.getIso2Code(), testCountry.getIso2Code());


    }

    @Test
    void CountryRepository_findCountryByName() {
        Country expectedCountry = austria;

        countryRepository.save(austria);

        Country foundCountry = countryRepository.findCountryByName(expectedCountry.getName()).orElseThrow();

        assertEquals(foundCountry.getCountryId(), expectedCountry.getCountryId());
        assertEquals(foundCountry.getName(), expectedCountry.getName());
        assertEquals(foundCountry.getIso2Code(), expectedCountry.getIso2Code());
    }

    @Test
    void deleteCountries() {
        Country country = austria;

        countryRepository.save(country);

        countryRepository.delete(country);

        Country deletedCountry = countryRepository.findCountryByCountryId(country.getCountryId()).orElse(null);
        Assertions.assertThat(deletedCountry).isNull();
    }

}