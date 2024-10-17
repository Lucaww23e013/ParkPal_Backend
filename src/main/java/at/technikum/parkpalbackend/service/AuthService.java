package at.technikum.parkpalbackend.service;

import at.technikum.parkpalbackend.dto.userdtos.CreateUserDto;
import at.technikum.parkpalbackend.dto.userdtos.LoginRequest;
import at.technikum.parkpalbackend.dto.userdtos.TokenResponse;
import at.technikum.parkpalbackend.dto.userdtos.UserResponseDto;
import at.technikum.parkpalbackend.exception.InvalidJwtTokenException;
import at.technikum.parkpalbackend.exception.UserWithUserNameOrEmailAlreadyExists;
import at.technikum.parkpalbackend.mapper.UserMapper;
import at.technikum.parkpalbackend.model.User;
import at.technikum.parkpalbackend.security.jwt.JwtDecoder;
import at.technikum.parkpalbackend.security.jwt.JwtIssuer;
import at.technikum.parkpalbackend.security.principal.UserPrincipal;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthService {
    private final JwtIssuer jwtIssuer;
    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final FileService fileService;
    private final UserMapper userMapper;
    private final JwtDecoder jwtDecoder;

    public AuthService(
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

    public CreateUserDto register(CreateUserDto createUserDto) {
        if (userService.userNameExists(createUserDto.getUserName())) {
            throw new UserWithUserNameOrEmailAlreadyExists(("A user with this username %s" +
                    " already exists.").formatted(createUserDto.getUserName()));
        }

        if (userService.userEmailExists(createUserDto.getEmail())) {
            throw new UserWithUserNameOrEmailAlreadyExists(("A user with this email %s" +
                    " already exists.").formatted(createUserDto.getEmail()));
        }

        User user = userMapper.toEntity(createUserDto);

        // Assign profile picture before saving the user
        String profilePictureId = createUserDto.getProfilePictureId();
        fileService.assignProfilePicture(user, profilePictureId, false);

        user = userService.save(user);
        return userMapper.toCreateUserDto(user);
    }

    public TokenResponse login(LoginRequest loginRequest, HttpServletResponse response) {
        User user = userService.findByUserNameOrEmail(loginRequest.getUsername());
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        user.getUserName(),
                        loginRequest.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(auth);
        UserPrincipal principal = (UserPrincipal) auth.getPrincipal();
        return getTokenResponse(response, principal);
    }

    public TokenResponse refresh(HttpServletResponse response) {
        UserPrincipal principal = (UserPrincipal) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();
        return getTokenResponse(response, principal);
    }

    public UserResponseDto getUserInfo(HttpServletRequest request) {
        String token = extractTokenFromCookie(request);
        if (token == null || token.isEmpty()) {
            throw new InvalidJwtTokenException("Token is missing or invalid");
        }
        DecodedJWT decodedJWT = jwtDecoder.decode(token);
        String userId = decodedJWT.getSubject();
        User user = userService.findByUserId(userId);
        if (user == null) {
            throw new InvalidJwtTokenException("User not found");
        }
        return userMapper.toUserResponseDto(user);
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
        cookie.setPath("/");
        response.addCookie(cookie);
        return new TokenResponse(token);
    }

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
}
