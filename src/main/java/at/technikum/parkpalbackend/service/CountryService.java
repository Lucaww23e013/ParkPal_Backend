package at.technikum.parkpalbackend.service;

import at.technikum.parkpalbackend.exception.EntityNotFoundException;
import at.technikum.parkpalbackend.model.Country;
import at.technikum.parkpalbackend.persistence.CountryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CountryService {
    private final CountryRepository countryRepository;

    public CountryService(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }

    public Country save(Country country) {
        return countryRepository.save(country);
    }

    public Country updateCountry(String countryId, Country updatedCountry) {
        Country country = countryRepository.findById(countryId)
                .orElseThrow(() -> new EntityNotFoundException("Country with id %s not found "
                        .formatted(countryId)));
        country.setId(updatedCountry.getId());
        country.setName(updatedCountry.getName());
        country.setIso2Code(updatedCountry.getIso2Code());

        return countryRepository.save(country);
    }

    public List<Country> findAllCountries() {
        return countryRepository.findAll();
    }

    public Country findCountryByCountryId(String countryId) {
        return countryRepository.findById(countryId)
                .orElseThrow(() -> new EntityNotFoundException("Country with id %s not found "
                                .formatted(countryId)));
    }

    public Country deleteCountryByCountryId(String countryId) {
        Country deltedCountry = countryRepository.findById(countryId)
                .orElseThrow(() -> new EntityNotFoundException("Country with id %s not found "
                        .formatted(countryId)));
        countryRepository.delete(deltedCountry);
        return deltedCountry;
    }
}
