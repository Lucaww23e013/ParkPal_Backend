package at.technikum.parkpalbackend.dto.userdtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class LoginUserDto {

    @NotBlank(message = "Enter an Email-Address")
    @Email(message = "Email is not valid")
    private String email;
}
