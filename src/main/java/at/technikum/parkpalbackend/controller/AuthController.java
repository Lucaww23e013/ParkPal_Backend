package at.technikum.parkpalbackend.controller;

import at.technikum.parkpalbackend.dto.userdtos.CreateUserDto;
import at.technikum.parkpalbackend.dto.userdtos.LoginRequest;
import at.technikum.parkpalbackend.dto.userdtos.TokenResponse;
import at.technikum.parkpalbackend.mapper.UserMapper;
import at.technikum.parkpalbackend.model.User;
import at.technikum.parkpalbackend.security.jwt.JwtIssuer;
import at.technikum.parkpalbackend.security.principal.UserPrincipal;
import at.technikum.parkpalbackend.service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final JwtIssuer jwtIssuer;
    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final UserMapper userMapper;

    public AuthController(
            JwtIssuer jwtIssuer,
            AuthenticationManager authenticationManager,
            UserService userService,
            UserMapper userMapper
    ) {
        this.jwtIssuer = jwtIssuer;
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Valid CreateUserDto createUserDto) {
        User user = userMapper.toEntity(createUserDto);
        user = userService.create(user);

        return ResponseEntity.ok(userMapper.toCreateUserDto(user));
    }

    @PostMapping("/login")
    public TokenResponse login(@RequestBody @Valid LoginRequest loginRequest,
                               HttpServletResponse response) {
        User user = userService.findByUserNameOrEmail(loginRequest.getUsername());


        // pass the username and password to springs in-build security manager
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        user.getUserName(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(auth);
        UserPrincipal principal = (UserPrincipal) auth.getPrincipal();
        // issue the jwt with the user information
        return getTokenResponse(response, principal);
    }

    @PostMapping("/refresh")
    public TokenResponse refresh(HttpServletResponse response) {
        UserPrincipal principal = (UserPrincipal) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();
        return getTokenResponse(response, principal);
    }

    private TokenResponse getTokenResponse(HttpServletResponse response, UserPrincipal principal) {
        List<String> roles = principal.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .toList();

        String token = jwtIssuer.issue(
                principal.getId(),
                principal.getUsername(),
                roles
        );

        Cookie cookie = new Cookie("token", token);
        cookie.setHttpOnly(true);
        cookie.setPath("/"); // for all paths is the cookie available
        response.addCookie(cookie);

        return new TokenResponse(token);
    }
}
