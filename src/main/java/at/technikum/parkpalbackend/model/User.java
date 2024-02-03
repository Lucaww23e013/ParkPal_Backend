package at.technikum.parkpalbackend.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder

@Entity
public class User {
    @Id
    @UuidGenerator
    @NotNull(message = "userId not found")
    private String userId;

    @NotBlank(message = "Salutation not found. User must add a Salutation")
    private String salutation;

    @NotBlank(message = "Username not found. User must add a Username")
    private String username;


    private String firstName;

    private String lastName;

    @NotBlank(message = "Email-Address not found. User must add an Email-Address")
    @Email(message = "Email must be valid Email-Address")
    private String email;

    @NotNull(message = "Password not found. User must add a Passowrd")
    private String password;

    @NotNull(message = "Authentication Failed")
    private String authToken;

    @ManyToOne
    @JoinColumn(name = "country_iso_2_code")
    private Country country;


}
