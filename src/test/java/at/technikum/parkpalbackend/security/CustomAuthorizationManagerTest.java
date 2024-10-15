package at.technikum.parkpalbackend.security;

import at.technikum.parkpalbackend.exception.BadRequestException;
import at.technikum.parkpalbackend.model.File;
import at.technikum.parkpalbackend.model.User;
import at.technikum.parkpalbackend.security.principal.UserPrincipal;
import at.technikum.parkpalbackend.service.EventService;
import at.technikum.parkpalbackend.service.FileService;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomAuthorizationManagerTest {

    @Mock
    private EventService eventService;

    @Mock
    private FileService fileService;

    @Mock
    private HttpServletRequest request;

    @InjectMocks
    private CustomAuthorizationManager authorizationManager;

    @BeforeEach
    void setUp() {
    }

    @Test
    void check_whenNotAuthenticated_thenDenyAccess() {
        // Arrange
        Authentication authentication = new TestingAuthenticationToken(null, null);
        authentication.setAuthenticated(false);
        RequestAuthorizationContext context = new RequestAuthorizationContext(request);

        // Act
        AuthorizationDecision decision = authorizationManager.check(() -> authentication, context);

        // Assert
        assertFalse(decision.isGranted());
    }

    @Test
    void check_whenUserIsAdmin_thenGrantAccess() {
        // Arrange
        UserPrincipal userPrincipal = new UserPrincipal("123", "user@test.com", "password",
                false, Collections.singletonList(() -> "ROLE_ADMIN"));
        Authentication authentication = new TestingAuthenticationToken(userPrincipal, null,
                "ROLE_ADMIN");
        authentication.setAuthenticated(true);

        // Set up SecurityContext
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Mock request with necessary headers/attributes
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("Authorization", "Bearer some-valid-jwt-token");
        request.setRequestURI("/some-protected-resource");

        Map<String, String> variables = new HashMap<>();
        variables.put("userId", "123");
        RequestAuthorizationContext context = new RequestAuthorizationContext(request, variables);

        // Act
        AuthorizationDecision decision = authorizationManager.check(() -> authentication, context);

        // Assert
        assertTrue(decision.isGranted());
    }

    @Test
    void check_whenUserIdMatches_thenGrantAccess() {
        // Arrange
        String userId = "123";
        UserPrincipal userPrincipal = new UserPrincipal(userId, "admin@test.com", "password",
                true, Collections.singletonList(() -> "ROLE_USER"));
        Authentication authentication = new TestingAuthenticationToken(userPrincipal, null,
                "ROLE_USER");
        authentication.setAuthenticated(true);

        Map<String, String> variables = new HashMap<>();
        variables.put("userId", userId);
        RequestAuthorizationContext context = new RequestAuthorizationContext(request, variables);

        // Act
        AuthorizationDecision decision = authorizationManager.check(() -> authentication, context);

        // Assert
        assertTrue(decision.isGranted());
    }

    @Test
    void check_whenEventIdMatchesEventCreator_thenGrantAccess() {
        // Arrange
        String eventId = "event123";
        String eventCreatorId = "creator123";

        UserPrincipal userPrincipal = new UserPrincipal(eventCreatorId, "user@test.com", "password",
                true, Collections.singletonList(() -> "ROLE_USER"));
        Authentication authentication = new TestingAuthenticationToken(userPrincipal, null,
                "ROLE_USER");
        authentication.setAuthenticated(true);

        when(eventService.findEventCreatorUserId(eventId)).thenReturn(eventCreatorId);

        Map<String, String> variables = new HashMap<>();
        variables.put("eventId", eventId);
        RequestAuthorizationContext context = new RequestAuthorizationContext(request, variables);

        // Act
        AuthorizationDecision decision = authorizationManager.check(() -> authentication, context);

        // Assert
        assertTrue(decision.isGranted());
        verify(eventService).findEventCreatorUserId(eventId);
    }

    @Test
    void check_whenExternalIdMatchesFileUser_thenGrantAccess() {
        // Arrange
        String externalId = "file123";
        String fileOwnerId = "user123";

        UserPrincipal userPrincipal = new UserPrincipal(fileOwnerId, "user@test.com", "password",
                true, Collections.singletonList(() -> "ROLE_USER"));
        Authentication authentication = new TestingAuthenticationToken(userPrincipal, null,
                "ROLE_USER");
        authentication.setAuthenticated(true);

        User user = User.builder().id(fileOwnerId).email("user@test.com").build();
        File file = File.builder().user(user).build();

        when(fileService.findFileByExternalId(externalId)).thenReturn(file);

        Map<String, String> variables = new HashMap<>();
        variables.put("externalId", externalId);
        RequestAuthorizationContext context = new RequestAuthorizationContext(request, variables);

        // Act
        AuthorizationDecision decision = authorizationManager.check(() -> authentication, context);

        // Assert
        assertTrue(decision.isGranted());
        verify(fileService).findFileByExternalId(externalId);
    }

    @Test
    void check_whenFileNotFound_thenThrowException() {
        // Arrange
        String externalId = "file123";
        when(fileService.findFileByExternalId(externalId)).thenReturn(null);

        // Create a UserPrincipal for authentication
        UserPrincipal userPrincipal = new UserPrincipal("user123", "user@test.com", "password",
                true, Collections.singletonList(() -> "ROLE_USER"));
        Authentication authentication = new TestingAuthenticationToken(userPrincipal, null,
                "ROLE_USER");
        authentication.setAuthenticated(true);

        // Set up the RequestAuthorizationContext with the externalId
        Map<String, String> variables = new HashMap<>();
        variables.put("externalId", externalId);
        RequestAuthorizationContext context = new RequestAuthorizationContext(request, variables);

        // Act & Assert
        assertThrows(NoSuchElementException.class, () -> authorizationManager.check(() -> authentication, context));
    }

    @Test
    void check_whenFileHasNoUser_thenReturnFalse() {
        // Arrange
        String externalId = "file123";

        // Create a File object with no associated User
        File file = File.builder().user(null).build();
        when(fileService.findFileByExternalId(externalId)).thenReturn(file);

        // Create a UserPrincipal for authentication
        UserPrincipal userPrincipal = new UserPrincipal("user123", "user@test.com", "password",
                true, Collections.singletonList(() -> "ROLE_USER"));
        Authentication authentication = new TestingAuthenticationToken(userPrincipal, null,
                "ROLE_USER");
        authentication.setAuthenticated(true);

        // Set up the RequestAuthorizationContext with the externalId
        Map<String, String> variables = new HashMap<>();
        variables.put("externalId", externalId);
        RequestAuthorizationContext context = new RequestAuthorizationContext(request, variables);

        // Act
        AuthorizationDecision decision = authorizationManager.check(() -> authentication, context);

        // Assert
        assertFalse(decision.isGranted());

        // Verify that the fileService.findFileByExternalId was called
        verify(fileService).findFileByExternalId(externalId);
    }

    @Test
    void check_whenPrincipalIsNotUserPrincipal_thenReturnFalse() {
        // Arrange
        Authentication authentication = new TestingAuthenticationToken("NotAUserPrincipal", null,
                "ROLE_USER");
        authentication.setAuthenticated(true);

        Map<String, String> variables = new HashMap<>();
        variables.put("userId", "123");
        RequestAuthorizationContext context = new RequestAuthorizationContext(request, variables);

        // Act
        AuthorizationDecision decision = authorizationManager.check(() -> authentication, context);

        // Assert
        assertFalse(decision.isGranted());
    }

    @Test
    void check_whenNoMatchingId_thenThrowException() {
        // Arrange
        RequestAuthorizationContext context = new RequestAuthorizationContext(request);

        // Act & Assert
        assertThrows(BadRequestException.class, () -> authorizationManager.resolveUserIdFromContext(context));
    }

    @Test
    void resolveUserIdFromContext_whenVariablesAreNull_thenThrowIllegalArgumentException() {
        // Arrange
        RequestAuthorizationContext context = new RequestAuthorizationContext(request, null);

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> authorizationManager.resolveUserIdFromContext(context));
    }

    @Test
    void resolveUserIdFromContext_whenEventCreatorNotFound_thenThrowNoSuchElementException() {
        // Arrange
        String eventId = "event123";
        when(eventService.findEventCreatorUserId(eventId)).thenReturn(null);

        Map<String, String> variables = new HashMap<>();
        variables.put("eventId", eventId);
        RequestAuthorizationContext context = new RequestAuthorizationContext(request, variables);

        // Act & Assert
        assertThrows(NoSuchElementException.class, () -> authorizationManager.resolveUserIdFromContext(context));

        // Verify that the eventService was called with the correct eventId
        verify(eventService).findEventCreatorUserId(eventId);
    }

    @Test
    void resolveUserIdFromContext_whenFileNotFound_thenThrowNoSuchElementException() {
        // Arrange
        String externalId = "file123";
        when(fileService.findFileByExternalId(externalId)).thenReturn(null);

        Map<String, String> variables = new HashMap<>();
        variables.put("externalId", externalId);
        RequestAuthorizationContext context = new RequestAuthorizationContext(request, variables);

        // Act & Assert
        assertThrows(NoSuchElementException.class, () -> authorizationManager.resolveUserIdFromContext(context));

        // Verify that the fileService was called with the correct externalId
        verify(fileService).findFileByExternalId(externalId);
    }

}
