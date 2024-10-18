package at.technikum.parkpalbackend.mapper;

import at.technikum.parkpalbackend.dto.userdtos.*;
import at.technikum.parkpalbackend.model.Event;
import at.technikum.parkpalbackend.model.File;
import at.technikum.parkpalbackend.model.User;
import at.technikum.parkpalbackend.model.enums.FileType;
import at.technikum.parkpalbackend.model.enums.Role;
import at.technikum.parkpalbackend.service.CountryService;
import at.technikum.parkpalbackend.service.EventService;
import at.technikum.parkpalbackend.service.FileService;
import at.technikum.parkpalbackend.service.UserService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


@Component
public class UserMapper {
    private final CountryService countryService;
    private final EventService eventService;
    private final FileService fileService;
    private final UserService userService;

    public UserMapper(CountryService countryService,
                      EventService eventService,
                      FileService fileService,
                      UserService userService) {
        this.countryService = countryService;
        this.eventService = eventService;
        this.fileService = fileService;
        this.userService = userService;
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
                .countryId(user.getCountry().getId())
                .role(user.getRole())
                .joinedEventsIds(getJoinedEventsIds(user))
                .mediaIds(getMediaIds(user))
                .profilePictureId(getProfilePictureId(user))
                .build();
    }

    public UpdateUserDto toUpdateDto(User user) {
        if (user == null) {
            throw new IllegalArgumentException("User entity or its fields cannot be null");
        }
        return UpdateUserDto.builder()
                .gender(user.getGender())
                .salutation(user.getSalutation())
                .userName(user.getUserName())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .countryId(user.getCountry().getId())
                .profilePictureId(user.getMedia().isEmpty()
                        ? null : user.getMedia()
                        .stream()
                        .filter(f -> f.getFileType() == FileType.PROFILE_PICTURE)
                        .findFirst()
                        .map(File::getExternalId)
                        .orElse(null))
                .joinedEventsIds(getJoinedEventsIds(user))
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
                .joinedEvents(eventService.findAllEventsJoinedByUser(userDto.getId()))
                .build();
    }

    public User toEntity(UpdateUserDto updateUserDto, String userid) {
        if (updateUserDto == null) {
            throw new IllegalArgumentException("updateUserDto or its fields cannot be null");
        }
        List<File> existingMedia = new ArrayList<>();

        User existingUser = userService.findByUserId(userid);

        if (existingUser != null && existingUser.getMedia() != null) {
            existingMedia.addAll(existingUser.getMedia());
        }

        User user = User.builder()
                .id(userid)
                .gender(updateUserDto.getGender())
                .salutation(updateUserDto.getSalutation())
                .userName(updateUserDto.getUserName())
                .firstName(updateUserDto.getFirstName())
                .lastName(updateUserDto.getLastName())
                .email(updateUserDto.getEmail())
                .country(countryService.findCountryByCountryId(updateUserDto.getCountryId()))
                .joinedEvents(eventService.findAllEventsJoinedByUser(userid))
                .media(existingMedia)
                .build();

        fileService.assignProfilePicture(user, updateUserDto.getProfilePictureId(), false);
        return user;
    }


    public CreateUserDto toCreateUserDto(User user) {
        if (user == null) {
            throw new IllegalArgumentException("User entity or its fields cannot be null");
        }
        return CreateUserDto.builder()
                .id(user.getId())
                .gender(user.getGender())
                .salutation(user.getSalutation())
                .userName(user.getUserName())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .countryId(user.getCountry().getId())
                .password(user.getPassword())
                .profilePictureId(user.getMedia().isEmpty()
                        ? null : user.getMedia().getFirst().getExternalId())
                .build();
    }
    public User toEntity(CreateUserDto createUserDto) {
        if (createUserDto == null) {
            throw new IllegalArgumentException("CreateUserDto or its fields cannot be null");
        }

        return User.builder()
                .id(createUserDto.getId())
                .gender(createUserDto.getGender())
                .salutation(createUserDto.getSalutation())
                .userName(createUserDto.getUserName().toLowerCase())
                .firstName(createUserDto.getFirstName())
                .lastName(createUserDto.getLastName())
                .email(createUserDto.getEmail().toLowerCase())
                .role(Role.USER)
                .password(new BCryptPasswordEncoder().encode(createUserDto.getPassword()))
                .country(countryService.findCountryByCountryId(createUserDto.getCountryId()))
                .media(new ArrayList<>())
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

    // TODO: check for a null User ??
    public UserResponseDto toUserResponseDto(User user) {
        return UserResponseDto.builder()
                .id(user.getId())
                .role(user.getRole())
                .build();
    }

    private static List<String> getMediaIds(User user) {
        if (user.getMedia() == null) {
            return new ArrayList<>();
        }
        return user.getMedia().isEmpty()
                ? new ArrayList<>() : user.getMedia().stream()
                .filter(f -> f.getFileType() != FileType.PROFILE_PICTURE)
                .map(File::getExternalId)
                .toList();
    }

    private String getProfilePictureId(User user) {
        return user.getMedia().stream()
                .filter(f -> f.getFileType() == FileType.PROFILE_PICTURE)
                .map(File::getExternalId)
                .findFirst()
                .orElse(null);
    }

    private static List<String> getJoinedEventsIds(User user) {
        if (user.getJoinedEvents() == null) {
            return new ArrayList<>();
        }
        return user.getJoinedEvents().stream()
                .map(Event::getId)
                .toList();
    }
}
