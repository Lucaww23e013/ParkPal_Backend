package at.technikum.parkpalbackend.security.filter;

import at.technikum.parkpalbackend.security.jwt.JwtDecoder;
import at.technikum.parkpalbackend.security.principal.JwtToPrincipalConverter;
import at.technikum.parkpalbackend.security.principal.UserPrincipalAuthenticationToken;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtDecoder jwtDecoder;
    private final JwtToPrincipalConverter jwtToPrincipalConverter;

    // List of routes to exclude from JWT validation
    private static final List<String> EXCLUDED_ROUTES = Arrays.asList(
            "/files"
    );

    public JwtAuthenticationFilter(JwtDecoder jwtDecoder,
                                   JwtToPrincipalConverter jwtToPrincipalConverter) {
        this.jwtDecoder = jwtDecoder;
        this.jwtToPrincipalConverter = jwtToPrincipalConverter;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        // Bypass JWT validation for example /files routes
        if (EXCLUDED_ROUTES.stream()
                .noneMatch(request.getRequestURI()::startsWith)) {
            extractTokenFromRequest(request)
                    .map(jwtDecoder::decode)
                    .map(jwtToPrincipalConverter::convert)
                    .map(UserPrincipalAuthenticationToken::new)
                    .ifPresent(auth -> SecurityContextHolder.getContext().setAuthentication(auth));
        }

        filterChain.doFilter(request, response);
    }

    private Optional<String> extractTokenFromRequest(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("token")) {
                    return Optional.of(cookie.getValue());
                }
            }
        }

        return Optional.empty();
    }
}
