package at.technikum.parkpalbackend.controller;

import at.technikum.parkpalbackend.dto.userdtos.CreateUserDto;
import at.technikum.parkpalbackend.dto.userdtos.LoginRequest;
import at.technikum.parkpalbackend.mapper.UserMapper;
import at.technikum.parkpalbackend.model.User;
import at.technikum.parkpalbackend.security.jwt.JwtDecoder;
import at.technikum.parkpalbackend.security.jwt.JwtIssuer;
import at.technikum.parkpalbackend.security.principal.UserPrincipal;
import at.technikum.parkpalbackend.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
public class AuthControllerTest {

    @Mock
    private JwtIssuer jwtIssuer;
    private JwtDecoder jwtDecoder;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private UserService userService;

    @Mock
    private HttpServletResponse response;

    @Mock
    private Authentication authentication;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private AuthController authController;

    @Test
    public void registerNewUser_() {
        // Arrange
        CreateUserDto createUserDto = CreateUserDto.builder()
                .userName("testUser")
                .password("testPassword434322!")
                .build();
        User user = User.builder()
                .userName("testUser")
                .password("testPassword241!34")
                .build();
        when(userMapper.toEntity(any(CreateUserDto.class))).thenReturn(user);
        when(userService.create(any(User.class))).thenReturn(user);
        // Act
        authController.register(createUserDto);
        // Assert
        verify(userService, times(1)).create(any(User.class));
    }

    @Test
    public void loginUser() {
        // Arrange
        LoginRequest loginRequest = LoginRequest.builder()
                .username("testUser")
                .password("testPassword353!")
                .build();

        User user = User.builder()
                .userName(loginRequest.getUsername())
                .password("testPassword353!")
                .build();

        UserPrincipal userPrincipal = new UserPrincipal(
                "1",
                user.getUserName(),
                loginRequest.getPassword(),
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"))
        );
        when(authenticationManager.authenticate(any())).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(userPrincipal);
        when(jwtIssuer.issue(anyString(), anyString(), anyList())).thenReturn("token");
        when(userService.findByUserNameOrEmail(anyString())).thenReturn(user);
        // Act
        authController.login(loginRequest, response);
        // Assert
        verify(authenticationManager, times(1)).authenticate(any());
        verify(jwtIssuer, times(1)).issue(anyString(), anyString(), anyList());
    }
}