package at.technikum.parkpalbackend.dto.userdtos;

import at.technikum.parkpalbackend.model.Event;
import at.technikum.parkpalbackend.model.enums.Gender;
import at.technikum.parkpalbackend.model.enums.Role;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder

public class UserDto {

    private String id;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private String salutation;

    @NotBlank(message = "Enter a Username")
    private String userName;

    @NotBlank(message = "Enter a your Firstname")
    private String firstName;

    @NotBlank(message = "Enter a your Lastname")
    private String lastName;

    @NotBlank(message = "Enter an Email-Address")
    @Email(message = "Email is not valid")
    private String email;

    @Pattern(regexp = "^(?=.*[a-z])(?=.*\\d)(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$)$",
            message = "Password must contain at least one lowercase letter & one uppercase letter."
                    + "One number and one special character")
    @Size(min = 12, message = "Password must be at least 12 characters long")
    @NotBlank(message = "Enter a Password")
    private String password;

    private String countryId;

    private Role role;

    @ToString.Exclude
    @Builder.Default
    // TODO: need to be with ids
    private List<Event> joinedEvents = new ArrayList<>();

    @ToString.Exclude
    @Builder.Default
    private List<String> mediaIds = new ArrayList<>();

}
