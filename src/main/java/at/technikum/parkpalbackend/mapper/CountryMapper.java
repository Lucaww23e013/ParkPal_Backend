package at.technikum.parkpalbackend.mapper;

import at.technikum.parkpalbackend.dto.CountryDto;
import at.technikum.parkpalbackend.model.Country;
import org.springframework.stereotype.Component;

@Component
public class CountryMapper {
    public CountryDto toDto(Country country){
        return new CountryDto(
                country.getCountryId(),
                country.getName(),
                country.getIso2Code()
        );
    }

    public Country toEntity(CountryDto countryDto) {
        return new Country(
                countryDto.getCountryId(),
                countryDto.getName(),
                countryDto.getIso2Code()
        );
    }
}
