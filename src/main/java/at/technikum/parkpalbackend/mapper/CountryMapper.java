package at.technikum.parkpalbackend.mapper;

import at.technikum.parkpalbackend.dto.CountryDto;
import at.technikum.parkpalbackend.model.Country;
import org.springframework.stereotype.Component;

@Component
public class CountryMapper {
    public CountryDto toDto(Country country){
        return CountryDto.builder()
                .countryId(country.getCountryId())
                .name(country.getName())
                .iso2Code(country.getIso2Code())
                .build();
    }

    public Country toEntity(CountryDto countryDto) {
        return Country.builder()
                .countryId(countryDto.getCountryId())
                .name(countryDto.getName())
                .iso2Code(countryDto.getIso2Code())
                .build();
    }
}
