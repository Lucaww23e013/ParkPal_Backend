package at.technikum.parkpalbackend.dto;

import at.technikum.parkpalbackend.model.Country;
import lombok.*;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class UserDto {
    private String userId;

    private String username;

    private String firstName;

    private String lastName;

    private String email;

    private String password;

    private String authToken;

    private Country country;

}
