package at.technikum.parkpalbackend.mapper;

import at.technikum.parkpalbackend.TestFixtures;
import at.technikum.parkpalbackend.dto.userdtos.CreateUserDto;
import at.technikum.parkpalbackend.dto.userdtos.LoginUserDto;
import at.technikum.parkpalbackend.dto.userdtos.UserDto;
import at.technikum.parkpalbackend.model.User;
import at.technikum.parkpalbackend.service.CountryService;
import at.technikum.parkpalbackend.service.FileService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.Assert.assertThrows;

//@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class UserMapperTest {

    @Mock
    CountryService countryService;

    @Mock
    FileService fileService;

    @InjectMocks
    private UserMapper userMapper;

    @Test
    public void whenEntity_thenToDto() {
        // Arrange
        User user = TestFixtures.adminUser;
        // Act
        UserDto userDto = userMapper.toDto(user);
        // Assert
        Assertions.assertEquals(user.getId(), userDto.getId());
        Assertions.assertEquals(user.getGender(), userDto.getGender());
        Assertions.assertEquals(user.getSalutation(), userDto.getSalutation());
        Assertions.assertEquals(user.getUserName(), userDto.getUserName());
        Assertions.assertEquals(user.getFirstName(), userDto.getFirstName());
        Assertions.assertEquals(user.getLastName(), userDto.getLastName());
        Assertions.assertEquals(user.getEmail(), userDto.getEmail());
        Assertions.assertEquals(user.getPassword(), userDto.getPassword());
        Assertions.assertEquals(user.getCountry().getId(), userDto.getCountryId());
        Assertions.assertEquals(user.getJoinedEvents(), userDto.getJoinedEvents());
    }

    @Test
    public void whenEntityNull_toDto_thenThrowIllegalArgumentException() {
        // Arrange
        User user = null;

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> userMapper.toDto(user));
    }

    @Test
    public void whenDTO_thenToEntity() {
        // Arrange
        UserDto userDto = TestFixtures.adminUserDto;
        userDto.setId(UUID.randomUUID().toString());

        // Act
        User user = userMapper.toEntity(userDto);

        //Assert
        Assertions.assertEquals(userDto.getId(), user.getId());
        Assertions.assertEquals(userDto.getGender(), user.getGender());
        Assertions.assertEquals(userDto.getSalutation(), user.getSalutation());
        Assertions.assertEquals(userDto.getUserName(), user.getUserName());
        Assertions.assertEquals(userDto.getFirstName(), user.getFirstName());
        Assertions.assertEquals(userDto.getLastName(), user.getLastName());
        Assertions.assertEquals(userDto.getEmail(), user.getEmail());
        Assertions.assertEquals(userDto.getPassword(), user.getPassword());
        Assertions.assertEquals(countryService.findCountryByCountryId(userDto.getCountryId()), user.getCountry());
        Assertions.assertEquals(userDto.getJoinedEvents(), user.getJoinedEvents());
    }

    @Test
    public void whenDTONull_toEntity_thenThrowIllegalArgumentException() {
        // Arrange
        UserDto userDto = null;

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> userMapper.toEntity(userDto));
    }

    @Test
    public void whenUserEntity_thenCreateUserDto() {
        // Arrange
        User user = TestFixtures.adminUser;

        // Act
        CreateUserDto createUserDto = userMapper.toCreateUserDto(user);

        // Assert
        Assertions.assertEquals(user.getId(), createUserDto.getId());
        Assertions.assertEquals(user.getSalutation(), createUserDto.getSalutation());
        Assertions.assertEquals(user.getUserName(), createUserDto.getUserName());
        Assertions.assertEquals(user.getFirstName(), createUserDto.getFirstName());
        Assertions.assertEquals(user.getLastName(), createUserDto.getLastName());
        Assertions.assertEquals(user.getEmail(), createUserDto.getEmail());
        Assertions.assertEquals(user.getPassword(), createUserDto.getPassword());
        Assertions.assertEquals(user.getCountry().getId(), createUserDto.getCountryId());
    }

    @Test
    public void whenEntityNull_toCreateParkDto_thenThrowIllegalArgumentException() {
        // Arrange
        User user = null;

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> userMapper.toCreateUserDto(user));
    }

    @Test
    public void whenCreateUserDTO_thenToUserEntity() {
        // Arrange
        CreateUserDto createUserDto = TestFixtures.adminCreateUserDto;

        // Act
        User user = userMapper.toEntity(createUserDto);

        // Assert
        Assertions.assertEquals(createUserDto.getId(), user.getId());
        Assertions.assertEquals(createUserDto.getSalutation(), user.getSalutation());
        Assertions.assertEquals(createUserDto.getUserName(), user.getUserName());
        Assertions.assertEquals(createUserDto.getFirstName(), user.getFirstName());
        Assertions.assertEquals(createUserDto.getLastName(), user.getLastName());
        Assertions.assertEquals(createUserDto.getEmail(), user.getEmail());
        Assertions.assertNotEquals(createUserDto.getPassword(), user.getPassword());
        Assertions.assertEquals(countryService.findCountryByCountryId(createUserDto.getCountryId()), user.getCountry());
    }

    @Test
    public void whenCreateUserDTONull_toEntity_thenThrowIllegalArgumentException() {
        // Arrange
        CreateUserDto createUserDto = null;

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> userMapper.toEntity(createUserDto));
    }

    @Test
    public void whenUserEntity_thenLoginUserDto() {
        // Arrange
        User user = TestFixtures.adminUser;

        // Act
        LoginUserDto loginUserDto = userMapper.toLoginUserDto(user);

        // Assert
        Assertions.assertEquals(user.getEmail(), loginUserDto.getEmail());
    }

    @Test
    public void whenEntityNull_toLoginParkDto_thenThrowIllegalArgumentException() {
        // Arrange
        User user = null;

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> userMapper.toLoginUserDto(user));
    }

    @Test
    public void whenLoginUserDTO_thenToUserEntity() {
        // Arrange
        LoginUserDto loginUserDto = TestFixtures.adminLoginUserDto;

        // Act
        User user = userMapper.toEntity(loginUserDto);
        // Assert
        Assertions.assertEquals(loginUserDto.getEmail(), user.getEmail());
    }

    @Test
    public void whenLoginUserDTONull_toEntity_thenThrowIllegalArgumentException() {
        // Arrange
        LoginUserDto loginUserDto = null;

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> userMapper.toEntity(loginUserDto));
    }
}
