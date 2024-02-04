package at.technikum.parkpalbackend.dto;

import lombok.*;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class CountryDto {
    private String name;

    private String iso2Code;

}
