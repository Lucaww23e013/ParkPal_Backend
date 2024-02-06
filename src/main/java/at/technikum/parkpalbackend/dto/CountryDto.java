package at.technikum.parkpalbackend.dto;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
//@Builder
public class CountryDto {
    private String name;

    private String iso2Code;

}
