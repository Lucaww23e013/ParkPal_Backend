package at.technikum.parkpalbackend.security;

import at.technikum.parkpalbackend.exception.BadRequestException;
import at.technikum.parkpalbackend.model.File;
import at.technikum.parkpalbackend.security.principal.UserPrincipal;
import at.technikum.parkpalbackend.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.stereotype.Component;
import at.technikum.parkpalbackend.service.EventService;

import java.util.NoSuchElementException;
import java.util.function.Supplier;

@Component
public class CustomAuthorizationManager
        implements AuthorizationManager<RequestAuthorizationContext> {

    private final EventService eventService;
    private final FileService fileService;

    @Autowired
    public CustomAuthorizationManager(EventService eventService, FileService fileService) {
        this.eventService = eventService;
        this.fileService = fileService;
    }

    @Override
    public AuthorizationDecision check(
            Supplier<Authentication> authenticationSupplier,
            RequestAuthorizationContext requestBody) {
        Authentication authentication = authenticationSupplier.get();
        if (authentication == null || !authentication.isAuthenticated()) {
            // If not authenticated, deny access
            return new AuthorizationDecision(false);
        }

        String userId = resolveUserIdFromContext(requestBody);

        boolean isAuthorized = isCurrentUserOrAdmin(authentication, userId);

        return new AuthorizationDecision(isAuthorized);
    }

    public String resolveUserIdFromContext(RequestAuthorizationContext context) {
        if (context.getVariables() == null) {
            throw new IllegalArgumentException("Variables in Request cannot be null.");
        }
        String userId = context.getVariables().get("userId");
        String eventId = context.getVariables().get("eventId");
        String externalId = context.getVariables().get("externalId");
        if (userId != null) {
            return userId;
        }
        if (eventId != null) {
            String eventCreatorId = eventService.findEventCreatorUserId(eventId);
            if (eventCreatorId == null) {
                throw new NoSuchElementException("No event creator found for eventId: " + eventId);
            }
            return eventCreatorId;
        }
        if (externalId != null) {
            File file = fileService.findFileByExternalId(externalId);
            if (file == null) {
                throw new NoSuchElementException(
                        "No file found with externalId: " + externalId);
            }
            if (file.getUser() == null) {
                return null;
            }
            return file.getUser().getId();
        }
        throw new BadRequestException(
                "No valid user-related identifier found in request: " + context);
    }

    public boolean isCurrentUserOrAdmin(Authentication authentication, String userId) {

        if (!(authentication.getPrincipal() instanceof UserPrincipal)) {
            // If the principal is not UserPrincipal, it could be anonymous or invalid
            return false;
        }
        String currentUserId = ((UserPrincipal) authentication.getPrincipal()).getId();

        // Check if user has the ADMIN role
        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ADMIN"));

        // Return true if current user is either admin or the user themselves
        return currentUserId.equals(userId) || isAdmin;
    }
}
