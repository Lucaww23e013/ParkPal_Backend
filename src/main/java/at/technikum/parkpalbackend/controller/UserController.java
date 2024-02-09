package at.technikum.parkpalbackend.controller;

import at.technikum.parkpalbackend.dto.userdtos.LoginUserDto;
import at.technikum.parkpalbackend.dto.userdtos.UserDto;
import at.technikum.parkpalbackend.dto.userdtos.CreateUserDto;
import at.technikum.parkpalbackend.mapper.UserMapper;
import at.technikum.parkpalbackend.model.User;
import at.technikum.parkpalbackend.service.UserService;
import jakarta.validation.Valid;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
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
    public CreateUserDto createUser(@RequestBody @Valid CreateUserDto userEssentialDto) {
        User user = userMapper.toEntity(userEssentialDto);
        user = userService.create(user);

        return userMapper.toEssentialDto(user);
    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public LoginUserDto LoginUser(String email, String password) {

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

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({MethodArgumentNotValidException.class, DataIntegrityViolationException.class})
    public Map<String, String> handleValidationException(Exception exception) {

        Map<String, String> errors = new HashMap<>();

        if (exception instanceof MethodArgumentNotValidException validationException) {
            validationException.getBindingResult().getAllErrors().forEach((error) -> {
                String fieldName = ((FieldError) error).getField();
                String errorMessage = error.getDefaultMessage();
                errors.put(fieldName, errorMessage);
            });
        } else if (exception instanceof DataIntegrityViolationException) {
            String errorMessage = exception.getMessage();
            ArrayList<String> errorMessages = new ArrayList<>();

            Throwable cause = exception.getCause();
            if (cause != null) {
                while (cause != null) {
                    errorMessages.add(cause.getMessage());
                    cause = cause.getCause();
                }
            } else {
                errorMessages.add(errorMessage);
            }

            for (String error : errorMessages) {
                if (errorMessage.contains(error)) {
                    errors.put("email", "Email already exists, please choose another one");
                }
                if (errorMessage.contains(error)) {
                    errors.put("username", "Username already exists, please choose another one");
                }
            }
        }
        return errors;
    }

}
