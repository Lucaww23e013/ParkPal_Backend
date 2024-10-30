package at.technikum.parkpalbackend.unitTests.service;

import at.technikum.parkpalbackend.exception.EntityNotFoundException;
import at.technikum.parkpalbackend.model.Country;
import at.technikum.parkpalbackend.model.User;
import at.technikum.parkpalbackend.persistence.CountryRepository;
import at.technikum.parkpalbackend.service.CountryService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static at.technikum.parkpalbackend.TestFixtures.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CountryServiceTest {

    @Mock
    private CountryRepository countryRepository;

    @InjectMocks
    private CountryService countryService;

    @Test
    void saveCountry_SuccessfullySaved_thenReturnSavedCountry() {
        // Arrange
        Country country = austria;
        country.setId(UUID.randomUUID().toString());
        when(countryRepository.save(country)).thenReturn(country);
        // Act
        Country result = countryService.save(country);
        // Assert
        assertNotNull(result);
        assertEquals(country, result);
        verify(countryRepository).save(country);
    }

    @Test
    void findAllCountries_whenCountriesExist_thenReturnCountries() {
        // Arrange
        Country country1 = austria;
        Country country2 = germany;
        List<Country> expectedCountries = new ArrayList<>();
        expectedCountries.add(country1);
        expectedCountries.add(country2);

        when(countryRepository.findAll()).thenReturn(expectedCountries);
        // Act
        List<Country> foundCountries = countryService.findAllCountries();
        // Assert
        assertNotNull(foundCountries);
        assertEquals(expectedCountries, foundCountries);
        verify(countryRepository).findAll();
    }


    @Test
    void findAllCountries_whenNoCountryExist_thenReturnEmptyList() {
        // Arrange
        when(countryRepository.findAll()).thenReturn(new ArrayList<>());
        // Act
        List<Country> foundCountries = countryService.findAllCountries();
        // Assert
        assertEquals(0, foundCountries.size());
        verify(countryRepository).findAll();
    }

    @Test
    void findByCountryId_whenCountryDoesNotExist_thenThrowEntityNotFoundException() {
        // Arrange
        String countryId = UUID.randomUUID().toString();
        when(countryRepository.findById(countryId)).thenReturn(Optional.empty());
        // Act + Assert
        assertThrows(EntityNotFoundException.class, () -> countryService.findCountryByCountryId(countryId));
        verify(countryRepository).findById(countryId);
    }


    @Test
    void deleteCountryById_whenCountryExists_thenDeleteCountry() {
        // Arrange
        String countryId = UUID.randomUUID().toString();
        Country countryToDelete = austria;
        when(countryRepository.findById(countryId)).thenReturn(Optional.of(countryToDelete));
        // Act
        Country deletedCountry = countryService.deleteCountryByCountryId(countryId);
        // Assert
        assertNotNull(deletedCountry);
        assertEquals(countryToDelete, deletedCountry);
        verify(countryRepository, times(1)).delete(countryToDelete);
    }

    @Test
    void deleteCountryById_whenCountryDoesNotExist_thenThrowEntityNotFoundException() {
        // Arrange
        String countryId = UUID.randomUUID().toString();
        Country countryToDelete = austria;
        when(countryRepository.findById(countryId)).thenReturn(Optional.empty());
        // Act + Assert
        assertThrows(EntityNotFoundException.class, () -> countryService.deleteCountryByCountryId(countryId));
        verify(countryRepository, times(0)).delete(countryToDelete);
    }

    @Test
    void deleteCountryById_whenUnknownError_thenThrowRuntimeException() {
        // Arrange
        String countryId = UUID.randomUUID().toString();
        Country countryToDelete = austria;
        countryToDelete.setId(countryId);
        when(countryRepository.findById(countryId)).thenReturn(Optional.of(countryToDelete));
        when(countryService.deleteCountryByCountryId(countryId)).thenThrow(new RuntimeException());
        // Act + Assert
        assertThrows(RuntimeException.class, () -> countryService.deleteCountryByCountryId(countryId));
        verify(countryRepository, times(1)).delete(countryToDelete);
    }

    @Test
    void updateCountryById_whenCountryExists_thenUpdateCountry() {
        // Arrange
        // prepare data
        String countryId = UUID.randomUUID().toString();
        Country oldCountry = austria;
        Country updatedCountry = austria;
        User newUser = adminUser;
        updatedCountry.setId(countryId);
        updatedCountry.setName(germany.getName());
        updatedCountry.setIso2Code(germany.getIso2Code());

        when(countryRepository.findById(countryId)).thenReturn(Optional.of(oldCountry));
        when(countryRepository.save(any(Country.class))).thenReturn(updatedCountry);
        // Act
        Country newCountry = countryService.updateCountry(countryId, updatedCountry);
        // Assert
        assertNotNull(newCountry);
        assertEquals(updatedCountry, newCountry);
        verify(countryRepository).save(updatedCountry);
    }

    @Test
    void updateCountryById_whenCountryDoesNotExist_thenThrowEntityNotFoundException() {
        // Arrange
        // prepare data
        String countryId = UUID.randomUUID().toString();
        Country updatedCountry = austria;

        when(countryRepository.findById(countryId)).thenReturn(Optional.empty());
        // Act + Assert
        assertThrows(EntityNotFoundException.class, () -> countryService
                .updateCountry(countryId, updatedCountry));
        verify(countryRepository, times(0)).save(updatedCountry);
    }

    @Test
    void updateCountryById_whenUnknownError_thenThrowRuntimeException() {
        // Arrange
        // prepare data
        String countryId = UUID.randomUUID().toString();
        Country oldCountry = austria;
        Country updatedCountry = austria;
        User newUser = adminUser;
        updatedCountry.setId(countryId);
        updatedCountry.setName(germany.getName());
        updatedCountry.setIso2Code(germany.getIso2Code());

        when(countryRepository.findById(countryId)).thenReturn(Optional.of(oldCountry));
        when(countryRepository.save(any(Country.class))).thenThrow(new RuntimeException());
        // Act + Assert
        assertThrows(RuntimeException.class, () -> countryService.updateCountry(countryId, updatedCountry));
        verify(countryRepository, times(1)).save(updatedCountry);
    }
}