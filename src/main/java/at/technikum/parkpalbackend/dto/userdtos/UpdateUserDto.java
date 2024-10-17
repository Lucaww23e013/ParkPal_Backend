package at.technikum.parkpalbackend.dto.userdtos;

import at.technikum.parkpalbackend.model.enums.Gender;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Valid
public class UpdateUserDto {

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

    @NotBlank(message = "Enter a valid CountryId")
    private String countryId;

    @ToString.Exclude
    @Builder.Default
    private List<String> joinedEventsIds = new ArrayList<>();

    private String profilePictureId;

}
