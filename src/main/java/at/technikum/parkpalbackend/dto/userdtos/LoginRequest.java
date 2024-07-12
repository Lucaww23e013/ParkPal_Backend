package at.technikum.parkpalbackend.dto.userdtos;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class LoginRequest {

    @NotBlank
    private String username;

    @NotBlank
    private String password;

}
