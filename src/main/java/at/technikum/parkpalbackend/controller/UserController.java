package at.technikum.parkpalbackend.controller;

import at.technikum.parkpalbackend.dto.UserDto;
import at.technikum.parkpalbackend.mapper.UserMapper;
import at.technikum.parkpalbackend.model.User;
import at.technikum.parkpalbackend.service.UserService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/{userName}")
    public UserDto getUserByName(@PathVariable String userName) {
        User user = userService.findByUserName(userName);

        return userMapper.toDto(user);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto createUser(@RequestBody @Valid UserDto userDto) {
        User user = userMapper.toEntity(userDto);
        user = userService.save(user);

        return userMapper.toDto(user);

    }


}
