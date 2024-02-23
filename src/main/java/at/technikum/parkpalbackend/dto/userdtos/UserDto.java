package at.technikum.parkpalbackend.dto.userdtos;

import at.technikum.parkpalbackend.model.*;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder

public class UserDto {

    private String userId;

    @Enumerated(EnumType.STRING)
    private Salutation salutation;


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
            message = "Password must contain at least one lowercase letter and one uppercase letter. One number and one special character")
    @Size(min = 12, message = "Password must be at least 12 characters long")
    @NotBlank(message = "Enter a Password")
    private String password;

    private String countryId;

    private Role role;

    @ToString.Exclude
    private List<Event> joinedEvents = new ArrayList<>();






}
