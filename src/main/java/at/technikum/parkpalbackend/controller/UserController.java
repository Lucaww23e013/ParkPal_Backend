package at.technikum.parkpalbackend.controller;

import at.technikum.parkpalbackend.dto.eventdtos.EventDto;
import at.technikum.parkpalbackend.dto.userdtos.UpdateUserDto;
import at.technikum.parkpalbackend.dto.userdtos.UserDto;
import at.technikum.parkpalbackend.mapper.EventMapper;
import at.technikum.parkpalbackend.mapper.UserMapper;
import at.technikum.parkpalbackend.model.Event;
import at.technikum.parkpalbackend.model.User;
import at.technikum.parkpalbackend.service.EventService;
import at.technikum.parkpalbackend.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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
    public UserDto updateUser(@PathVariable String userId,
                              @RequestBody @Valid UpdateUserDto updateUserDto) {
        User user = userMapper.toEntity(updateUserDto);

        user = userService.update(userId, user);

        return userMapper.toDto(user);
    }
    @DeleteMapping("/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteUser(@PathVariable String userId) {
        userService.delete(userId);
    }

    @PostMapping("/{userId}/lock")
    @ResponseStatus(HttpStatus.OK)
    public void lockUser(@PathVariable String userId) {
        userService.lockUser(userId);
    }

    @PostMapping("/{userId}/unlock")
    @ResponseStatus(HttpStatus.OK)
    public void unlockUser(@PathVariable String userId) {
        userService.unlockUser(userId);
    }

}
