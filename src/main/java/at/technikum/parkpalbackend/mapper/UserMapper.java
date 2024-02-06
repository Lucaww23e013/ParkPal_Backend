package at.technikum.parkpalbackend.mapper;

import at.technikum.parkpalbackend.dto.UserDto;
import at.technikum.parkpalbackend.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserDto toDto(User user) {
        return new UserDto(
                user.getUserId(),
                user.getUserName(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getPassword(),
                user.getAuthToken(),
                user.getCountry(),
                user.getIsAdmin()
        );
    }

    public User toEntity(UserDto userDto) {
            return new User(
                    userDto.getSalutation(),
                    userDto.getUserName(),
                    userDto.getFirstName(),
                    userDto.getLastName(),
                    userDto.getEmail(),
                    userDto.getPassword(),
                    userDto.getAuthToken(),
                    userDto.getCountry(),
                    userDto.getIsAdmin()
                    );
    }
}
