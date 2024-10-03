package at.technikum.parkpalbackend.controller;

import at.technikum.parkpalbackend.model.enums.Role;
import at.technikum.parkpalbackend.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admins")
public class AdminController {

    private final UserService userService;

    public AdminController(UserService userService) {
        this.userService = userService;
    }

    // TODO: missing Authorization checks
    @PostMapping("/{userId}/status")
    public ResponseEntity<String> updateUserStatus(@PathVariable String userId,
                                                   @RequestParam(required = false) Boolean locked,
                                                   @RequestParam(required = false) Role role) {
        if (locked == null && role == null) {
            return ResponseEntity.badRequest()
                    .body("At least one of 'locked' or 'role' parameters must be provided.");
        }
        userService.updateUserStatus(userId, locked, role);
        return ResponseEntity.ok("User status updated successfully.");
    }

    // TODO: missing Authorization checks
    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable String userId) {
        userService.delete(userId);
        return ResponseEntity.noContent().build();
    }
}
