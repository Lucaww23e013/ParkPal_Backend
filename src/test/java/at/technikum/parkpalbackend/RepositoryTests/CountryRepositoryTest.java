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

    Country testCountry;


    @BeforeEach
    void setUp() {
        testCountry = austria;
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void saveCountries(){
        Country savedCountry = countryRepository.save(testCountry);

        Assertions.assertThat(savedCountry).isNotNull();
        Assertions.assertThat(savedCountry.getCountryId()).isNotNull();
        Assertions.assertThat(savedCountry.getName()).isNotNull();
        Assertions.assertThat(savedCountry.getIso2Code()).isNotNull();
    }
    @Test
    void CountryRepository_findCountryByCountryId() {

        countryRepository.save(austria);

        Country foundCountry = countryRepository.findCountryByCountryId(testCountry.getCountryId()).orElseThrow();

        assertEquals(foundCountry.getCountryId(), testCountry.getCountryId());
        assertEquals(foundCountry.getName(), testCountry.getName());
        assertEquals(foundCountry.getIso2Code(), testCountry.getIso2Code());


    }

    @Test
    void CountryRepository_findCountryByName() {

        countryRepository.save(austria);

        Country foundCountry = countryRepository.findCountryByName(testCountry.getName()).orElseThrow();

        assertEquals(foundCountry.getCountryId(), testCountry.getCountryId());
        assertEquals(foundCountry.getName(), testCountry.getName());
        assertEquals(foundCountry.getIso2Code(), testCountry.getIso2Code());
    }

    @Test
    void deleteCountries() {

        countryRepository.save(testCountry);

        countryRepository.delete(testCountry);

        Country deletedCountry = countryRepository.findCountryByCountryId(testCountry.getCountryId()).orElse(null);
        Assertions.assertThat(deletedCountry).isNull();
    }

}