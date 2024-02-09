package at.technikum.parkpalbackend.model;

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

@Entity
@Table(name = "users", uniqueConstraints = {
        @UniqueConstraint(name = "unique_username", columnNames = "user_name"),
        @UniqueConstraint(name = "unique_email", columnNames = "email")


})
public class User {

    @Id
    @UuidGenerator // the default is a random generation
    //@UuidGenerator(style = UuidGenerator.Style.RANDOM)
    @Column(name = "user_id")
    private String userId;


    @Enumerated(EnumType.STRING)
    private Salutation salutation;

    @NotBlank(message = "Enter a Username")
    @Column(unique = true, name = "user_name")
    private String userName;

    @NotBlank(message = "Enter a your Firstname")
    private String firstName;

    @NotBlank(message = "Enter a your Lastname")
    private String lastName;

    @NotBlank(message = "Enter an Email-Address")
    @Email(message = "Email is not valid")
    @Column(unique = true, name = "email")
    private String email;

    @Pattern(regexp = "^(?=.*[a-z])(?=.*\\d)(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).+$",
            message = "Password must contain at least one lowercase letter and one uppercase letter. One number and one special character")
    @Size(min = 12, message = "Password must be at least 12 characters long")
    @NotBlank(message = "Enter a Password")
    private String password;


    private String authToken;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    private Country country;

    private boolean isAdmin;

    @OneToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @ToString.Exclude
    private List<Event> joinedEvents = new ArrayList<>();

    @OneToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    private List<User> users = new ArrayList<>();


//    public User(String salutation, String username, String firstName, String lastName, String email, String password, String authToken, Boolean isAdmin) {
//        this.salutation = salutation;
//        this.userName = username;
//        this.firstName = firstName;
//        this.lastName = lastName;
//        this.email = email;
//        this.password = password;
//        this.authToken = authToken;
//       /* this.country = country;*/
//        this.isAdmin = isAdmin;
//    }

    /*public User(String salutation, String username, String firstName, String lastName, String email, String password, String authToken, Country country) {
        this(salutation,username,firstName, lastName,email,password,authToken,country,false);
    }

    public User(String userId) {
        this.userId = userId;
    }*/
    public User addJoinedEvents(Event... events) {
        Arrays.stream(events).forEach(event -> this.joinedEvents.add(event));
        return this;
    }

}
