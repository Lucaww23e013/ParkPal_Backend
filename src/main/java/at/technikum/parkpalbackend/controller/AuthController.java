package at.technikum.parkpalbackend.controller;

import at.technikum.parkpalbackend.dto.userdtos.CreateUserDto;
import at.technikum.parkpalbackend.dto.userdtos.LoginRequest;
import at.technikum.parkpalbackend.dto.userdtos.TokenResponse;
import at.technikum.parkpalbackend.dto.userdtos.UserResponseDto;
import at.technikum.parkpalbackend.exception.InvalidJwtTokenException;
import at.technikum.parkpalbackend.mapper.UserMapper;
import at.technikum.parkpalbackend.model.User;
import at.technikum.parkpalbackend.security.jwt.JwtDecoder;
import at.technikum.parkpalbackend.security.jwt.JwtIssuer;
import at.technikum.parkpalbackend.security.principal.UserPrincipal;
import at.technikum.parkpalbackend.service.FileService;
import at.technikum.parkpalbackend.service.UserService;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final JwtIssuer jwtIssuer;
    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final FileService fileService;
    private final UserMapper userMapper;
    private final JwtDecoder jwtDecoder;

    public AuthController(
            JwtIssuer jwtIssuer,
            AuthenticationManager authenticationManager,
            UserService userService, FileService fileService,
            UserMapper userMapper,
            JwtDecoder jwtDecoder) {
        this.jwtIssuer = jwtIssuer;
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.fileService = fileService;
        this.userMapper = userMapper;
        this.jwtDecoder = jwtDecoder;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Valid CreateUserDto createUserDto) {
        User user = userMapper.toEntity(createUserDto);
        user = userService.save(user);

        String profilePictureId = createUserDto.getProfilePictureId();
        fileService.assignProfilePicture(user, profilePictureId, true);

        return ResponseEntity.ok(userMapper.toCreateUserDto(user));
    }

    @PostMapping("/login")
    public ResponseEntity<?>  login(@RequestBody @Valid LoginRequest loginRequest,
                               HttpServletResponse response) {
        try {
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
            return ResponseEntity.ok(getTokenResponse(response, principal));
        } catch (InvalidJwtTokenException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMessage());
        }
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refresh(HttpServletResponse response) {
        try {
            UserPrincipal principal = (UserPrincipal) SecurityContextHolder
                    .getContext()
                    .getAuthentication()
                    .getPrincipal();
            return ResponseEntity.ok(getTokenResponse(response, principal));
        } catch (InvalidJwtTokenException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMessage());
        }
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

    // for authorization debugging handled in WebSecurityConfig
    @GetMapping("/userOrAdmin/{userId}")
    public String currentUserOrAdmin(@PathVariable String userId) {
        return "Your are logged in as Current User or Admin";
    }

    // for authorization debugging handled in WebSecurityConfig
    @GetMapping("/user")
    public String user() {
        return "Your are logged in as an authenticated User";
    }

    // for authorization debugging handled in WebSecurityConfig
    @GetMapping("/admin")
    public String admin() {
        return "Your are logged in as Admin";
    }

    // for authentication debugging
    @GetMapping("/me")
    public ResponseEntity<?> getUserInfo(HttpServletRequest request) {
        try {
            // Get the JWT token from the cookies (HttpOnly)
            String token = extractTokenFromCookie(request);
            if (token == null || token.isEmpty()) {
                return ResponseEntity.status(401).body("Token is missing or invalid");
            }

            // Decode the JWT token
            DecodedJWT decodedJWT = jwtDecoder.decode(token);
            String userId = decodedJWT.getSubject();

            // Fetch the user details from the database
            User user = userService.findByUserId(userId);
            if (user == null) {
                return ResponseEntity.status(404).body("User not found");
            }

            // Map to DTO (UserResponseDto) and return
            UserResponseDto userResponseDto = userMapper.toUserResponseDto(user);
            return ResponseEntity.ok(userResponseDto);

        } catch (Exception e) {
            return ResponseEntity.status(401).body("Invalid or expired token");
        }
    }

    // Utility method to extract JWT from cookies
    private String extractTokenFromCookie(HttpServletRequest request) {
        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if ("token".equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

    // TODO: only authenticated can logout
    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpServletResponse response) {
        Cookie jwtToken = new Cookie("token", null);
        jwtToken.setHttpOnly(true);
        jwtToken.setPath("/"); // Match the path where the cookie is used
        jwtToken.setMaxAge(0); // Immediately expire the cookie

        response.addCookie(jwtToken);

        // Clear security context for logging out user on the server
        SecurityContextHolder.clearContext();
        return ResponseEntity.ok().build();
    }
}

