package at.technikum.parkpalbackend.unitTests.controller;

import at.technikum.parkpalbackend.controller.AuthController;
import at.technikum.parkpalbackend.dto.userdtos.CreateUserDto;
import at.technikum.parkpalbackend.dto.userdtos.LoginRequest;
import at.technikum.parkpalbackend.dto.userdtos.TokenResponse;
import at.technikum.parkpalbackend.dto.userdtos.UserResponseDto;
import at.technikum.parkpalbackend.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthControllerTest {

    @Mock
    private AuthService authService;

    @Mock
    private HttpServletResponse response;

    @Mock
    private HttpServletRequest request;

    @InjectMocks
    private AuthController authController;

    @Test
    public void registerNewUser() {
        // Arrange
        CreateUserDto createUserDto = CreateUserDto.builder()
                .userName("testUser")
                .password("testPassword434322!")
                .build();
        when(authService.register(any(CreateUserDto.class))).thenReturn(createUserDto);

        // Act
        ResponseEntity<?> responseEntity = authController.register(createUserDto);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        verify(authService, times(1)).register(any(CreateUserDto.class));
    }

    @Test
    public void loginUser() {
        // Arrange
        LoginRequest loginRequest = LoginRequest.builder()
                .username("testUser")
                .password("testPassword353!")
                .build();
        TokenResponse tokenResponse = new TokenResponse("token");
        when(authService.login(any(LoginRequest.class), any(HttpServletResponse.class))).thenReturn(tokenResponse);

        // Act
        ResponseEntity<?> responseEntity = authController.login(loginRequest, response);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        verify(authService, times(1)).login(any(LoginRequest.class), any(HttpServletResponse.class));
    }

    @Test
    public void refreshUserToken() {
        // Arrange
        TokenResponse tokenResponse = new TokenResponse("token");
        when(authService.refresh(any(HttpServletResponse.class))).thenReturn(tokenResponse);

        // Act
        ResponseEntity<?> responseEntity = authController.refresh(response);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        verify(authService, times(1)).refresh(any(HttpServletResponse.class));
    }

    @Test
    public void getUserInfo() {
        // Arrange
        UserResponseDto userResponseDto = UserResponseDto.builder().build();
        when(authService.getUserInfo(any(HttpServletRequest.class))).thenReturn(userResponseDto);

        // Act
        ResponseEntity<?> responseEntity = authController.getUserInfo(request);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        verify(authService, times(1)).getUserInfo(any(HttpServletRequest.class));
    }

    @Test
    public void logoutUser() {
        // Act
        ResponseEntity<Void> responseEntity = authController.logout(response);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        verify(response, times(1)).addCookie(any());
        SecurityContextHolder.clearContext();
    }
}