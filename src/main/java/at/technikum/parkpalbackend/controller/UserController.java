package at.technikum.parkpalbackend.controller;

import at.technikum.parkpalbackend.dto.userdtos.LoginUserDto;
import at.technikum.parkpalbackend.dto.userdtos.UserDto;
import at.technikum.parkpalbackend.dto.userdtos.CreateUserDto;
import at.technikum.parkpalbackend.mapper.UserMapper;
import at.technikum.parkpalbackend.model.User;
import at.technikum.parkpalbackend.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
@CrossOrigin

//@AllArgsConstructor

public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    public UserController(UserService userService, UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }
    @GetMapping
    public List<UserDto> readAll() {
        return userService.findAll().stream()
               .map(userMapper::toDto)
               .collect(Collectors.toList());
    }

    @GetMapping("/email/{email}")
    public UserDto getUserByEmail(@PathVariable String email) {
        User user = userService.findByUserEmail(email);

        return userMapper.toDto(user);
    }

    @GetMapping("/userid/{userId}")
    public UserDto getUserById(@PathVariable String userId) {
        User user = userService.findByUserId(userId);

        return userMapper.toDto(user);
    }

    @GetMapping("/userName/{userName}")
    public UserDto getUserByUserName(@PathVariable String userName) {
        User user = userService.findByUserName(userName);

        return userMapper.toDto(user);
    }

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public CreateUserDto createUser(@RequestBody @Valid CreateUserDto createUserDto) {
        User user = userMapper.toEntity(createUserDto);
        user = userService.create(user);
        return userMapper.toCreateUserDto(user);
    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public LoginUserDto loginUser(String email, String password) {
        User user = userService.login(email, password);
        return userMapper.toLoginUserDto(user);
    }

    @DeleteMapping("/delete/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteUser(@PathVariable String userId) {
        userService.delete(userId);
    }

    @PutMapping("/update/{userId}")
    public UserDto updateUser(@PathVariable String id, @RequestBody @Valid UserDto userDto) {
        User user = userMapper.toEntity(userDto);
        user = userService.update(id, user);

        return userMapper.toDto(user);
    }
}
