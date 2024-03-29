package at.technikum.parkpalbackend.mapper;

import at.technikum.parkpalbackend.dto.userdtos.DeleteUserDto;
import at.technikum.parkpalbackend.dto.userdtos.LoginUserDto;
import at.technikum.parkpalbackend.dto.userdtos.UserDto;
import at.technikum.parkpalbackend.dto.userdtos.CreateUserDto;
import at.technikum.parkpalbackend.model.User;
import org.springframework.stereotype.Component;


@Component
public class UserMapper {

    public UserDto toDto(User user) {
        return UserDto.builder()
                .userId(user.getUserId())
                .salutation(user.getSalutation())
                .userName(user.getUserName())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .password(user.getPassword())
                .country(user.getCountry())
                .authToken(user.getAuthToken())
                .isAdmin(user.isAdmin())
                .joinedEvents(user.getJoinedEvents())
                .build();
    }

    public User toEntity(UserDto userDto) {
        return User.builder()
                .userId(userDto.getUserId())
                .salutation(userDto.getSalutation())
                .userName(userDto.getUserName())
                .firstName(userDto.getFirstName())
                .lastName(userDto.getLastName())
                .email(userDto.getEmail())
                .password(userDto.getPassword())
                .country(userDto.getCountry())
                .authToken(userDto.getAuthToken())
                .isAdmin(userDto.isAdmin())
                .joinedEvents(userDto.getJoinedEvents())
                .build();
    }

    public CreateUserDto toCreateUserDto(User user) {
        return CreateUserDto.builder()
                .userId(user.getUserId())
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
                .userId(createUserDto.getUserId())
                .salutation(createUserDto.getSalutation())
                .userName(createUserDto.getUserName())
                .firstName(createUserDto.getFirstName())
                .lastName(createUserDto.getLastName())
                .email(createUserDto.getEmail())
                .password(createUserDto.getPassword())
                .build();
    }

    public DeleteUserDto toDeleteUserDto(User user) {
        return DeleteUserDto.builder()
                .userId(user.getUserId())
                .build();
    }

    public User toEntity(DeleteUserDto deleteUserDto) {
        return User.builder()
                .userId(deleteUserDto.getUserId())
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
