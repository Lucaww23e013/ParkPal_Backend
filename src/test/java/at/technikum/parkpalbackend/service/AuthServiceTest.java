package at.technikum.parkpalbackend.service;

import at.technikum.parkpalbackend.TestFixtures;
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
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private JwtIssuer jwtIssuer;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private UserService userService;

    @Mock
    private FileService fileService;

    @Mock
    private UserMapper userMapper;

    @Mock
    private JwtDecoder jwtDecoder;

    @InjectMocks
    private AuthService authService;

    @Test
    void register_throwsExceptionWhenUsernameExists() {
        CreateUserDto createUserDto = CreateUserDto.builder().userName("existingUser").build();
        when(userService.userNameExists(createUserDto.getUserName())).thenReturn(true);

        assertThrows(UserWithUserNameOrEmailAlreadyExists.class, () -> authService.register(createUserDto));
        verify(userService, never()).save(any(User.class));
    }

    @Test
    void register_throwsExceptionWhenEmailExists() {
        CreateUserDto createUserDto = CreateUserDto.builder().email("existingEmail@test.com").build();
        when(userService.userEmailExists(createUserDto.getEmail())).thenReturn(true);

        assertThrows(UserWithUserNameOrEmailAlreadyExists.class, () -> authService.register(createUserDto));
        verify(userService, never()).save(any(User.class));
    }

    @Test
    void register_createsUserSuccessfully() {
        CreateUserDto createUserDto = CreateUserDto.builder().userName("newUser").email("newEmail@test.com").build();
        User user = User.builder().build();
        when(userService.userNameExists(createUserDto.getUserName())).thenReturn(false);
        when(userService.userEmailExists(createUserDto.getEmail())).thenReturn(false);
        when(userMapper.toEntity(createUserDto)).thenReturn(user);
        when(userService.save(user)).thenReturn(user);
        when(userMapper.toCreateUserDto(user)).thenReturn(createUserDto);

        CreateUserDto result = authService.register(createUserDto);

        assertEquals(createUserDto, result);
        verify(fileService).assignProfilePicture(user, createUserDto.getProfilePictureId(), true);
    }

    @Test
    void login_authenticatesUserSuccessfully() {
        User user = TestFixtures.normalUser;
        LoginRequest loginRequest = LoginRequest.builder()
                .username(user.getUserName())
                .password(user.getPassword())
                .build();

        UserPrincipal principal = new UserPrincipal(
                user.getId(),
                user.getUserName(),
                user.getPassword(),
                user.isLocked(),
                Collections.emptyList() // or appropriate authorities
        );
        Authentication authentication = new UsernamePasswordAuthenticationToken(principal, null, Collections.emptyList());

        when(userService.findByUserNameOrEmail(loginRequest.getUsername())).thenReturn(user);
        when(authenticationManager.authenticate(any())).thenReturn(authentication);

        HttpServletResponse response = mock(HttpServletResponse.class);
        TokenResponse tokenResponse = authService.login(loginRequest, response);

        assertNotNull(tokenResponse);
        verify(response).addCookie(any(Cookie.class));
    }

    @Test
    void refresh_returnsNewToken() {
        User user = TestFixtures.adminUser;
        UserPrincipal principal = new UserPrincipal(
                user.getId(),
                user.getUserName(),
                user.getPassword(),
                user.isLocked(),
                Collections.emptyList() // or appropriate authorities
        );
        Authentication authentication = new UsernamePasswordAuthenticationToken(principal, null, Collections.emptyList());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        HttpServletResponse response = mock(HttpServletResponse.class);
        TokenResponse tokenResponse = authService.refresh(response);

        assertNotNull(tokenResponse);
        verify(response).addCookie(any(Cookie.class));
    }

    @Test
    void getUserInfo_returnsUserInfo() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        Cookie cookie = new Cookie("token", "validToken");
        when(request.getCookies()).thenReturn(new Cookie[]{cookie});
        DecodedJWT decodedJWT = mock(DecodedJWT.class);
        when(jwtDecoder.decode("validToken")).thenReturn(decodedJWT);
        when(decodedJWT.getSubject()).thenReturn("userId");
        User user = TestFixtures.adminUser;
        when(userService.findByUserId("userId")).thenReturn(user);
        UserResponseDto userResponseDto = UserResponseDto.builder().build();
        when(userMapper.toUserResponseDto(user)).thenReturn(userResponseDto);

        UserResponseDto result = authService.getUserInfo(request);

        assertEquals(userResponseDto, result);
    }

    @Test
    void getUserInfo_throwsExceptionWhenTokenIsInvalid() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getCookies()).thenReturn(new Cookie[]{});

        assertThrows(InvalidJwtTokenException.class, () -> authService.getUserInfo(request));
    }

    @Test
    void getUserInfo_throwsExceptionWhenTokenIsMissing() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getCookies()).thenReturn(null);

        assertThrows(InvalidJwtTokenException.class, () -> authService.getUserInfo(request));
    }

    @Test
    void getUserInfo_throwsExceptionWhenTokenIsEmpty() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        Cookie cookie = new Cookie("token", "");
        when(request.getCookies()).thenReturn(new Cookie[]{cookie});

        assertThrows(InvalidJwtTokenException.class, () -> authService.getUserInfo(request));
    }

    @Test
    void getUserInfo_throwsExceptionWhenUserNotFound() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        Cookie cookie = new Cookie("token", "validToken");
        when(request.getCookies()).thenReturn(new Cookie[]{cookie});
        DecodedJWT decodedJWT = mock(DecodedJWT.class);
        when(jwtDecoder.decode("validToken")).thenReturn(decodedJWT);
        when(decodedJWT.getSubject()).thenReturn("userId");
        when(userService.findByUserId("userId")).thenReturn(null);

        assertThrows(InvalidJwtTokenException.class, () -> authService.getUserInfo(request));
    }

}