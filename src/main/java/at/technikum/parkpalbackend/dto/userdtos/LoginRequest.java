package at.technikum.parkpalbackend.dto.userdtos;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class LoginRequest {

    @NotBlank(message = "Please enter a Username")
    private String username;

    @NotBlank(message = "Please enter a Password")
    private String password;

}
