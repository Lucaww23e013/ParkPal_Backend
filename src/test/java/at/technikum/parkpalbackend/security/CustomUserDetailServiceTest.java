package at.technikum.parkpalbackend.security;

import at.technikum.parkpalbackend.model.User;
import at.technikum.parkpalbackend.model.enums.Role;
import at.technikum.parkpalbackend.security.principal.UserPrincipal;
import at.technikum.parkpalbackend.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomUserDetailServiceTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private CustomUserDetailService customUserDetailService;

    @Test
    void loadUserByUsername_whenUserExists_thenReturnUserPrincipal() {
        // Arrange
        String username = "testUser";
        User user = User.builder()
                .id("123")
                .userName(username)
                .password("password")
                .locked(false)
                .role(Role.USER)
                .build();

        when(userService.findByUserName(username)).thenReturn(user);

        // Act
        UserDetails userDetails = customUserDetailService.loadUserByUsername(username);

        // Assert
        assertNotNull(userDetails);
        assertTrue(userDetails instanceof UserPrincipal);
        assertEquals("123", ((UserPrincipal) userDetails).getId());
        assertEquals(username, userDetails.getUsername());
        assertEquals("password", userDetails.getPassword());
        assertTrue(userDetails.isAccountNonLocked());  // Corrected expectation
        assertTrue(userDetails.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals("USER")));
    }

    @Test
    void loadUserByUsername_whenUserDoesNotExist_thenThrowUsernameNotFoundException() {
        // Arrange
        String username = "nonExistentUser";
        when(userService.findByUserName(username)).thenThrow(new UsernameNotFoundException("User not found: " + username));

        // Act & Assert
        assertThrows(UsernameNotFoundException.class, () -> customUserDetailService.loadUserByUsername(username));

        // Verify that userService was called with the correct argument
        verify(userService).findByUserName(username);
    }


    @Test
    void loadUserByUsername_whenUserRoleIsNull_thenThrowException() {
        // Arrange
        String username = "userWithNoRole";
        User user = User.builder()
                .id("456")
                .userName(username)
                .password("password")
                .locked(false)
                .role(null)  // Role is not set
                .build();

        when(userService.findByUserName(username)).thenReturn(user);

        // Act & Assert
        assertThrows(NullPointerException.class, () -> customUserDetailService.loadUserByUsername(username));

        // Verify that userService was called with the correct argument
        verify(userService).findByUserName(username);
    }
}