package at.technikum.parkpalbackend.mapper;

import at.technikum.parkpalbackend.dto.CountryDto;
import at.technikum.parkpalbackend.model.Country;
import org.springframework.stereotype.Component;

@Component
public class CountryMapper {
    public CountryDto toDto(Country country) {
        if (country == null) {
            throw new IllegalArgumentException("Country entity or its fields cannot be null");
        }

        return CountryDto.builder()
                .id(country.getId())
                .name(country.getName())
                .iso2Code(country.getIso2Code())
                .build();
    }

    public Country toEntity(CountryDto countryDto) {
        if (countryDto == null) {
            throw new IllegalArgumentException("CountryDto or its fields cannot be null");
        }

        return Country.builder()
                .id(countryDto.getId())
                .name(countryDto.getName())
                .iso2Code(countryDto.getIso2Code())
                .build();
    }
}
