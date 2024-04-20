package at.technikum.parkpalbackend.mapper;

import at.technikum.parkpalbackend.dto.userdtos.DeleteUserDto;
import at.technikum.parkpalbackend.dto.userdtos.LoginUserDto;
import at.technikum.parkpalbackend.dto.userdtos.UserDto;
import at.technikum.parkpalbackend.dto.userdtos.CreateUserDto;
import at.technikum.parkpalbackend.model.User;
import at.technikum.parkpalbackend.service.CountryService;
import org.springframework.stereotype.Component;


@Component
public class UserMapper {
    private final CountryService countryService;

    public UserMapper(CountryService countryService) {
        this.countryService = countryService;
    }

    public UserDto toDto(User user) {
        return UserDto.builder()
                .id(user.getId())
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
        return User.builder()
                .id(userDto.getId())
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
        return CreateUserDto.builder()
                .id(user.getId())
                .salutation(user.getSalutation())
                .userName(user.getUserName())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .password(user.getPassword())
                .build();
    }

    public User toEntity(CreateUserDto createUserDto) {
        return User.builder()
                .id(createUserDto.getId())
                .salutation(createUserDto.getSalutation())
                .userName(createUserDto.getUserName())
                .firstName(createUserDto.getFirstName())
                .lastName(createUserDto.getLastName())
                .email(createUserDto.getEmail())
                .password(createUserDto.getPassword())
                .country(countryService.findCountryByCountryId(createUserDto.getCountryId()))
                .build();
    }

    public DeleteUserDto toDeleteUserDto(User user) {
        return DeleteUserDto.builder()
                .userId(user.getId())
                .build();
    }

    public User toEntity(DeleteUserDto deleteUserDto) {
        return User.builder()
                .id(deleteUserDto.getUserId())
                .build();
    }

    public LoginUserDto toLoginUserDto(User user) {
        return LoginUserDto.builder()
                .email(user.getEmail())
                .build();

    }

    public User toEntity(LoginUserDto loginUserDto) {
        return User.builder()
                .email(loginUserDto.getEmail())
                .build();
    }

}
