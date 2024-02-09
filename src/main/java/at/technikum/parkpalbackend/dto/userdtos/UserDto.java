package at.technikum.parkpalbackend.dto.userdtos;

import at.technikum.parkpalbackend.model.Country;
import at.technikum.parkpalbackend.model.Event;
import at.technikum.parkpalbackend.model.Salutation;
import at.technikum.parkpalbackend.model.User;
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

    private String authToken;

    private Country country;

    private boolean isAdmin;

    @ToString.Exclude
    private List<Event> joinedEvents = new ArrayList<>();

    /*public UserDto(String salutation, String username, String firstName, String lastName, String email, String password, String authToken, Country country, Boolean isAdmin) {
        this.salutation = salutation;
        this.userName = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.authToken = authToken;
        this.country = country;
        this.isAdmin = isAdmin;
    }*/

    /*public UserDto(String salutation, String username, String firstName, String lastName, String email, String password, String authToken, Country country) {
        this(salutation,username,firstName, lastName,email,password,authToken,country,false);
    }*/





}
