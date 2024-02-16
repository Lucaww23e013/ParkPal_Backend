package at.technikum.parkpalbackend.mapper;

import at.technikum.parkpalbackend.dto.userdtos.CreateUserDto;
import at.technikum.parkpalbackend.dto.userdtos.DeleteUserDto;
import at.technikum.parkpalbackend.dto.userdtos.LoginUserDto;
import at.technikum.parkpalbackend.dto.userdtos.UserDto;
import at.technikum.parkpalbackend.model.Country;
import at.technikum.parkpalbackend.model.Salutation;
import at.technikum.parkpalbackend.model.User;
import org.h2.command.ddl.CreateUser;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

import static org.assertj.core.api.Assumptions.assumeThat;
import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class UserMapperTest {


    @Autowired
    private UserMapper userMapper;

    public static Country austria = Country.builder().name("Austria").iso2Code("AT").build();
    public static User entityUser = buildUser("r221", "raul@gmail.com", "Raul", "Gonzo", false);
    public static UserDto dtoUser = buildUserDto("r221", "raul@gmail.com", "Raul", "Gonzo", false);
    public static CreateUserDto dtoCreateUser = buildCreateUserDto("r221", "raul@gmail.com", "Raul", "Gonzo");
    public static DeleteUserDto dtoDeleteUser = buildDeleteUserDto();
    public static LoginUserDto dtoLoginUser = buildLoginUserDto("raul@gmail.com");



    private static User buildUser(String userName, String firstName, String lastName, String email, boolean isAdmin) {
        return User.builder()
                .userId(UUID.randomUUID().toString())
                .salutation(Salutation.MALE)
                .userName(userName)
                .firstName(firstName)
                .lastName(lastName)
                .email(email)
                .password("eyJhbGciOiJIUzI1NiIsInR5cCI32!")
                .authToken("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c")
                .country(austria)
                .isAdmin(isAdmin)
                .build();
    }
    private static UserDto buildUserDto(String userName, String firstName, String lastName, String email, boolean isAdmin) {
        return UserDto.builder()
                .userId(UUID.randomUUID().toString())
                .salutation(Salutation.MALE)
                .userName(userName)
                .firstName(firstName)
                .lastName(lastName)
                .email(email)
                .password("eyJhbGciOiJIUzI1NiIsInR5cCI32!")
                .authToken("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c")
                .country(austria)
                .isAdmin(isAdmin)
                .build();
    }

    private static CreateUserDto buildCreateUserDto(String userName, String firstName, String lastName, String email) {
        return CreateUserDto.builder()
                .salutation(Salutation.MALE)
                .userName(userName)
                .firstName(firstName)
                .lastName(lastName)
                .email(email)
                .password("eyJhbGciOiJIUzI1NiIsInR5cCI32!")
                .build();
    }

    private static DeleteUserDto buildDeleteUserDto() {
        return DeleteUserDto.builder()
                .userId(UUID.randomUUID().toString())
                .build();
    }

    private static LoginUserDto buildLoginUserDto(String email) {
        return LoginUserDto.builder()
                .email(email)
                .build();
    }


    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    //Tests User
    @Test
    void whenEntityValue_thenDtoValue() {
        // Create an instance of UserMapper
        UserMapper userMapper = new UserMapper();

        User user = entityUser;

        // Call the toDto method with the normalUser instance
        UserDto userDto = userMapper.toDto(user);

        // Verify that the returned UserDto object has the expected attributes/values
        assertEquals(user.getUserId(), userDto.getUserId());
        assertEquals(user.getSalutation(), userDto.getSalutation());
        assertEquals(user.getUserName(), userDto.getUserName());
        assertEquals(user.getFirstName(), userDto.getFirstName());
        assertEquals(user.getLastName(), userDto.getLastName());
        assertEquals(user.getEmail(), userDto.getEmail());
        assertEquals(user.getPassword(), userDto.getPassword());
        assertEquals(user.getCountry(), userDto.getCountry());
        assertEquals(user.getAuthToken(), userDto.getAuthToken());
        assertEquals(user.isAdmin(), userDto.isAdmin());
        /*assertEquals(user.getJoinedEvents(), userDto.getJoinedEvents());*/
    }

    //Tests UserDto
    @Test
    void whenDtoValue_thenEntityValue() {
        // Create an instance of UserMapper
        UserMapper userMapper = new UserMapper();

        UserDto userDto = dtoUser;

        // Call the toEntity method with the UserDto instance
        User user = userMapper.toEntity(userDto);

        // Verify that the returned User entity has the expected attributes/values
        assertEquals(userDto.getUserId(), user.getUserId());
        assertEquals(userDto.getSalutation(), user.getSalutation());
        assertEquals(userDto.getUserName(), user.getUserName());
        assertEquals(userDto.getFirstName(), user.getFirstName());
        assertEquals(userDto.getLastName(), user.getLastName());
        assertEquals(userDto.getEmail(), user.getEmail());
        assertEquals(userDto.getPassword(), user.getPassword());
        assertEquals(userDto.getCountry().getName(), user.getCountry().getName());
        assertEquals(userDto.getAuthToken(), user.getAuthToken());
        assertEquals(userDto.isAdmin(), user.isAdmin());
        /*assertEquals(userDto.getJoinedEvents(), user.getJoinedEvents());*/
    }

    //Tests CreateUser
    @Test
    void whenCreateEntityValue_thenCreateDtoValue() {
        // Create an instance of UserMapper
        UserMapper userMapper = new UserMapper();

        User user = entityUser;

        // Call the toDto method with the normalUser instance
        CreateUserDto createUserDto = userMapper.toCreateUserDto(user);

        // Verify that the returned UserDto object has the expected attributes/values
        assertEquals(user.getUserId(), createUserDto.getUserId());
        assertEquals(user.getSalutation(), createUserDto.getSalutation());
        assertEquals(user.getUserName(), createUserDto.getUserName());
        assertEquals(user.getFirstName(), createUserDto.getFirstName());
        assertEquals(user.getLastName(), createUserDto.getLastName());
        assertEquals(user.getEmail(), createUserDto.getEmail());
        assertEquals(user.getPassword(), createUserDto.getPassword());
    }

    //Tests CreateUserDto
    @Test
    void whenCreateDtoValue_thenCreateEntityValue() {
        // Create an instance of UserMapper
        UserMapper userMapper = new UserMapper();

        CreateUserDto createUserDto = dtoCreateUser;

        // Call the toEntity method with the UserDto instance
        User user = userMapper.toEntity(createUserDto);

        // Verify that the returned User entity has the expected attributes/values
        assertEquals(createUserDto.getUserId(), user.getUserId());
        assertEquals(createUserDto.getSalutation(), user.getSalutation());
        assertEquals(createUserDto.getUserName(), user.getUserName());
        assertEquals(createUserDto.getFirstName(), user.getFirstName());
        assertEquals(createUserDto.getLastName(), user.getLastName());
        assertEquals(createUserDto.getEmail(), user.getEmail());
        assertEquals(createUserDto.getPassword(), user.getPassword());
    }


    //Tests DeleteUser
    @Test
    void whenDeleteEntityValue_thenDeleteDtoValue() {
        // Create an instance of UserMapper
        UserMapper userMapper = new UserMapper();

        User user = entityUser;

        // Call the toDto method with the normalUser instance
        DeleteUserDto deleteUserDto = userMapper.toDeleteUserDto(user);

        // Verify that the returned UserDto object has the expected attributes/values
        assertEquals(user.getUserId(), deleteUserDto.getUserId());
    }

    //Tests DeleteUserDto
    @Test
    void whenDeleteDtoValue_thenDeleteEntityValue() {
        // Create an instance of UserMapper
        UserMapper userMapper = new UserMapper();

        DeleteUserDto deleteUserDto = dtoDeleteUser;

        // Call the toEntity method with the UserDto instance
        User user = userMapper.toEntity(deleteUserDto);

        // Verify that the returned User entity has the expected attributes/values
        assertEquals(deleteUserDto.getUserId(), user.getUserId());
    }

    //Tests LoginUser
    @Test
    void whenLoginEntityValue_thenLoginDtoValue() {
        // Create an instance of UserMapper
        UserMapper userMapper = new UserMapper();

        User user = entityUser;

        // Call the toDto method with the normalUser instance
        LoginUserDto loginUserDto = userMapper.toLoginUserDto(user);

        // Verify that the returned UserDto object has the expected attributes/values
        assertEquals(user.getEmail(), loginUserDto.getEmail());
    }

    //Tests LoginUserDto
    @Test
    void whenLoginDtoValue_thenLoginEntityValue() {
        // Create an instance of UserMapper
        UserMapper userMapper = new UserMapper();

        LoginUserDto loginUserDto = dtoLoginUser;

        // Call the toEntity method with the UserDto instance
        User user = userMapper.toEntity(loginUserDto);

        // Verify that the returned User entity has the expected attributes/values
        assertEquals(loginUserDto.getEmail(), user.getEmail());
    }
}