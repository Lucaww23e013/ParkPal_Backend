package at.technikum.parkpalbackend.security;

import at.technikum.parkpalbackend.security.principal.UserPrincipal;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.stereotype.Component;

import java.util.function.Supplier;

@Component
public class CustomAuthorizationManager
        implements AuthorizationManager<RequestAuthorizationContext> {

    @Override
    public AuthorizationDecision check(
            Supplier<Authentication> authenticationSupplier,
            RequestAuthorizationContext object) {

        Authentication authentication = authenticationSupplier.get();
        if (authentication == null || !authentication.isAuthenticated()) {
            // If not authenticated, deny access
            return new AuthorizationDecision(false);
        }

        String userId = object.getVariables().get("userId");

        boolean isAuthorized = isCurrentUserOrAdmin(authentication, userId);
        return new AuthorizationDecision(isAuthorized);
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
