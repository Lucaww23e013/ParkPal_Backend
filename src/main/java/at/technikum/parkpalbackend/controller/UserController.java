package at.technikum.parkpalbackend.controller;

import at.technikum.parkpalbackend.dto.eventdtos.EventDto;
import at.technikum.parkpalbackend.dto.userdtos.UserDto;
import at.technikum.parkpalbackend.mapper.EventMapper;
import at.technikum.parkpalbackend.mapper.UserMapper;
import at.technikum.parkpalbackend.model.Event;
import at.technikum.parkpalbackend.model.User;
import at.technikum.parkpalbackend.service.EventService;
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


public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;
    private final EventService eventService;
    private final EventMapper eventMapper;


    public UserController(UserService userService, EventService eventService,
                          UserMapper userMapper, EventMapper eventMapper) {
        this.userService = userService;
        this.eventService = eventService;
        this.userMapper = userMapper;
        this.eventMapper = eventMapper;
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

    @GetMapping("/{userId}/events")
    @ResponseStatus(HttpStatus.OK)
    public List<EventDto> getAllEventsByUserId(@PathVariable String userId) {
        List<Event> events = eventService.findAllEventsCreatedByUser(userId);
        return events.stream()
                .map(event -> eventMapper.toDto(event))
                .toList();
    }

    @PutMapping("/{userId}")
    public UserDto updateUser(@PathVariable String id, @RequestBody @Valid UserDto userDto) {
        User user = userMapper.toEntity(userDto);
        user = userService.update(id, user);

        return userMapper.toDto(user);
    }
    @DeleteMapping("/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteUser(@PathVariable String userId) {
        userService.delete(userId);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({MethodArgumentNotValidException.class,
        DataIntegrityViolationException.class})
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
                    errorMessages.add(cause.getMessage()); cause = cause.getCause();
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
