package at.technikum.parkpalbackend.model;

import at.technikum.parkpalbackend.persistence.CountryRepository;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CountryDeserializer extends JsonDeserializer<Country> {

    private final CountryRepository countryRepository;

    public CountryDeserializer(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }

    @Override
    public Country deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        String countryId = p.getText();
        return countryRepository.findById(countryId)
                .orElseThrow(() -> new IOException("Country not found"));
    }
}