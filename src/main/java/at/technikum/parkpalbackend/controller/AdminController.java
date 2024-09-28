package at.technikum.parkpalbackend.controller;

import at.technikum.parkpalbackend.model.enums.Role;
import at.technikum.parkpalbackend.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admins")
public class AdminController {

    private final UserService userService;

    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @DeleteMapping("/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteUser(@PathVariable String userId) {
        userService.delete(userId);
    }

    @PostMapping("/{userId}/lock")
    @ResponseStatus(HttpStatus.OK)
    public void updateUserLockStatus(@PathVariable String userId, @RequestParam boolean isLocked) {
        userService.setUserLockStatus(userId, isLocked);
    }

    @PostMapping("/{userId}/role")
    @ResponseStatus(HttpStatus.OK)
    public void updateUserRole(@PathVariable String userId, @RequestParam Role role) {
        userService.updateUserRole(userId, role);
    }
}
