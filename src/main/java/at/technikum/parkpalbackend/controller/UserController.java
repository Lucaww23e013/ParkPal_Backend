package at.technikum.parkpalbackend.controller;

import at.technikum.parkpalbackend.dto.userdtos.UpdateUserDto;
import at.technikum.parkpalbackend.dto.userdtos.UserDto;
import at.technikum.parkpalbackend.mapper.UserMapper;
import at.technikum.parkpalbackend.model.User;
import at.technikum.parkpalbackend.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
@CrossOrigin


public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    public UserController(UserService userService,
                          UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @GetMapping
    public List<UserDto> readAll() {
        return userService.findAll().stream()
               .map(userMapper::toDto)
               .collect(Collectors.toList());
    }

    @GetMapping("/{userId}")
    public UserDto getUserById(@PathVariable String userId) {
        User user = userService.findByUserId(userId);
        return userMapper.toDto(user);
    }

    @PutMapping("/{userId}")
    public UpdateUserDto updateUser(@PathVariable String userId,
                              @RequestBody @Valid UpdateUserDto updateUserDto) {
        User user = userMapper.toEntity(updateUserDto, userId);
        user = userService.update(userId, user);
        return userMapper.toUpdateDto(user);
    }
    // TODO: only user itself and admin
    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable String userId) {
        userService.delete(userId);
        return ResponseEntity.noContent().build();
    }

}
