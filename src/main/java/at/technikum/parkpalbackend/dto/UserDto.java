package at.technikum.parkpalbackend.dto;

import at.technikum.parkpalbackend.model.Country;
import at.technikum.parkpalbackend.model.Event;
import at.technikum.parkpalbackend.model.User;
import jakarta.persistence.CascadeType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
//@Builder

public class UserDto {
    private String userId;

    @NotBlank(message = "Salutation not found. User must add a Salutation")
    private String salutation;

    @NotBlank(message = "Username not found. User must add a Username")
    private String userName;

    private String firstName;

    private String lastName;

    @NotBlank(message = "Email-Address not found. User must add an Email-Address")
    @Email(message = "Email must be valid Email-Address")
    private String email;

    @NotNull(message = "Password not found. User must add a Password")
    private String password;

    @NotNull(message = "Authentication Failed")
    private String authToken;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "country_id")
    private Country country;

    private Boolean isAdmin;

    @OneToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @ToString.Exclude
    private List<Event> joinedEvents = new ArrayList<>();

    public UserDto(String salutation, String username, String firstName, String lastName, String email, String password, String authToken, Country country, Boolean isAdmin) {
        this.salutation = salutation;
        this.userName = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.authToken = authToken;
        this.country = country;
        this.isAdmin = isAdmin;
    }

    public UserDto(String salutation, String username, String firstName, String lastName, String email, String password, String authToken, Country country) {
        this(salutation,username,firstName, lastName,email,password,authToken,country,false);
    }

    public UserDto addJoinedEvents(Event... events) {
        Arrays.stream(events).forEach(event -> this.joinedEvents.add(event));
        return this;
    }

}
