package at.technikum.parkpalbackend.mapper;

import at.technikum.parkpalbackend.dto.userdtos.DeleteUserDto;
import at.technikum.parkpalbackend.dto.userdtos.LoginUserDto;
import at.technikum.parkpalbackend.dto.userdtos.UserDto;
import at.technikum.parkpalbackend.dto.userdtos.CreateUserDto;
import at.technikum.parkpalbackend.model.User;
import at.technikum.parkpalbackend.model.enums.Role;
import at.technikum.parkpalbackend.service.CountryService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;


@Component
public class UserMapper {
    private final CountryService countryService;

    public UserMapper(CountryService countryService) {
        this.countryService = countryService;
    }
    public UserDto toDto(User user) {
        if (user == null) {
            throw new IllegalArgumentException("User entity or its fields cannot be null");
        }
        return UserDto.builder()
                .id(user.getId())
                .gender(user.getGender())
                .salutation(user.getSalutation())
                .userName(user.getUserName())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .password(user.getPassword())
                .countryId(user.getCountry().getId())
                .role(user.getRole())
                .joinedEvents(user.getJoinedEvents())
                .build();
    }

    public User toEntity(UserDto userDto) {
        if (userDto == null) {
            throw new IllegalArgumentException("UserDto  or its fields cannot be null");
        }
        return User.builder()
                .id(userDto.getId())
                .gender(userDto.getGender())
                .salutation(userDto.getSalutation())
                .userName(userDto.getUserName())
                .firstName(userDto.getFirstName())
                .lastName(userDto.getLastName())
                .email(userDto.getEmail())
                .password(userDto.getPassword())
                .country(countryService.findCountryByCountryId(userDto.getCountryId()))
                .role(userDto.getRole())
                .joinedEvents(userDto.getJoinedEvents())
                .build();
    }
    public CreateUserDto toCreateUserDto(User user) {
        if (user == null) {
            throw new IllegalArgumentException("User entity or its fields cannot be null");
        }
        return CreateUserDto.builder()
                .id(user.getId())
                .salutation(user.getSalutation())
                .userName(user.getUserName())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .role(user.getRole())
                .countryId(user.getCountry().getId())
                .password(user.getPassword())
                .build();
    }
    public User toEntity(CreateUserDto createUserDto) {
        if (createUserDto == null) {
            throw new IllegalArgumentException("CreateUserDto or its fields cannot be null");
        }
        return User.builder()
                .id(createUserDto.getId())
                .salutation(createUserDto.getSalutation())
                .userName(createUserDto.getUserName())
                .firstName(createUserDto.getFirstName())
                .lastName(createUserDto.getLastName())
                .email(createUserDto.getEmail())
                .role(Role.USER)
                .password(new BCryptPasswordEncoder().encode(createUserDto.getPassword()))
                .country(countryService.findCountryByCountryId(createUserDto.getCountryId()))
                .build();
    }

    public DeleteUserDto toDeleteUserDto(User user) {
        if (user == null) {
            throw new IllegalArgumentException("User entity or its fields cannot be null");
        }
        return DeleteUserDto.builder()
                .userId(user.getId())
                .build();
    }

    public User toEntity(DeleteUserDto deleteUserDto) {
        if (deleteUserDto == null) {
            throw new IllegalArgumentException("DeleteUserDto or its fields cannot be null");
        }
        return User.builder()
                .id(deleteUserDto.getUserId())
                .build();
    }

    public LoginUserDto toLoginUserDto(User user) {
        if (user == null) {
            throw new IllegalArgumentException("LoginUserDto or its fields cannot be null");
        }
        return LoginUserDto.builder()
                .email(user.getEmail())
                .build();

    }

    public User toEntity(LoginUserDto loginUserDto) {
        if (loginUserDto == null) {
            throw new IllegalArgumentException("LoginUserDto or its fields cannot be null");
        }
        return User.builder()
                .email(loginUserDto.getEmail())
                .build();
    }

}
