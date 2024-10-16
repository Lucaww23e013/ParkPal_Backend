package at.technikum.parkpalbackend.controller;

import at.technikum.parkpalbackend.dto.userdtos.CreateUserDto;
import at.technikum.parkpalbackend.dto.userdtos.LoginRequest;
import at.technikum.parkpalbackend.exception.InvalidJwtTokenException;
import at.technikum.parkpalbackend.service.AuthService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Valid CreateUserDto createUserDto) {
        return ResponseEntity.ok(authService.register(createUserDto));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid LoginRequest loginRequest,
                                   HttpServletResponse response) {
        try {
            return ResponseEntity.ok(authService.login(loginRequest, response));
        } catch (InvalidJwtTokenException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMessage());
        }
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refresh(HttpServletResponse response) {
        try {
            return ResponseEntity.ok(authService.refresh(response));
        } catch (InvalidJwtTokenException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMessage());
        }
    }

    @GetMapping("/me")
    public ResponseEntity<?> getUserInfo(HttpServletRequest request) {
        try {
            return ResponseEntity.ok(authService.getUserInfo(request));
        } catch (InvalidJwtTokenException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMessage());
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpServletResponse response) {
        Cookie jwtToken = new Cookie("token", null);
        jwtToken.setHttpOnly(true);
        jwtToken.setPath("/");
        jwtToken.setMaxAge(0);
        response.addCookie(jwtToken);
        SecurityContextHolder.clearContext();
        return ResponseEntity.ok().build();
    }

}

