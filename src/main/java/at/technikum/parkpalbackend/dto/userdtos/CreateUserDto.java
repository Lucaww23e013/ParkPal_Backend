package at.technikum.parkpalbackend.dto.userdtos;

import at.technikum.parkpalbackend.model.enums.Gender;
import at.technikum.parkpalbackend.model.enums.Role;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Valid
public class CreateUserDto {

    private String id;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @NotBlank(message = "Enter a Salutation")
    private String salutation;

    @Enumerated(EnumType.STRING)
    private Role role;

    @NotBlank(message = "Enter a Username")
    @Length(min = 5, max = 255, message = "Username should have at least 5 characters" +
            "and should not exceed 255 characters.")
    private String userName;

    @NotBlank(message = "Enter a your Firstname")
    private String firstName;

    @NotBlank(message = "Enter a your Lastname")
    private String lastName;

    @NotBlank(message = "Enter an Email-Address")
    @Email(message = "Email is not valid")
    private String email;

    @Pattern(regexp = "^(?=.*[a-z])(?=.*\\d)(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).+$",
            message = "Password must contain at least one lowercase letter & one uppercase letter."
                    + "One number and one special character")
    @Size(min = 12, message = "Password must be at least 12 characters long")
    @NotBlank(message = "Enter a Password")
    private String password;

    @NotBlank(message = "Enter a Country")
    private String countryId;

    private String profilePictureId;

}
