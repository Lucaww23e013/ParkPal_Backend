package at.technikum.parkpalbackend.security.config;

import at.technikum.parkpalbackend.security.CustomAuthorizationManager;
import at.technikum.parkpalbackend.security.CustomUserDetailService;
import at.technikum.parkpalbackend.security.filter.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {


    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final CustomUserDetailService customUserDetailService;
    private final CustomAuthorizationManager customAuthorizationManager;

    public WebSecurityConfig(
            JwtAuthenticationFilter jwtAuthenticationFilter,
            CustomUserDetailService customUserDetailService,
            CustomAuthorizationManager customAuthorizationManager
    ) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
        this.customUserDetailService = customUserDetailService;
        this.customAuthorizationManager = customAuthorizationManager;
    }

    /*
     * This method is used to create a security filter chain
     * that is used to configure the security settings.
     * The security filter chain is used to configure the security settings.
     * In this case, we are disabling the CORS, CSRF, and form login.
     * We are also setting the session creation policy to STATELESS.
     * We are also configuring the authorization rules for the requests.
     * @param http The HttpSecurity object that is used to configure the security settings.
     * @return The SecurityFilterChain object that is used to
     * configure the security settings.
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        configureBasicSecurity(http);
        configureSessionManagement(http);


        // configure Authorization
        configureSwaggerAndApiEndpoints(http);
        configureUserEndpoints(http, customAuthorizationManager);
        configureEventEndpoints(http, customAuthorizationManager);
        configureEventTagEndpoints(http);
        configureParkEndpoints(http);
        configureFileEndpoints(http, customAuthorizationManager);
        configureCountryEndpoints(http);
        configureAuthEndpoints(http);
        configureAdminEndpoints(http);
        configureErrorEndpoints(http);

        return http.build();
    }

    private void configureBasicSecurity(HttpSecurity http) throws Exception {
        http.cors(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable);
    }

    private void configureSessionManagement(HttpSecurity http) throws Exception {
        http.sessionManagement(session ->
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
    }

    private void configureSwaggerAndApiEndpoints(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(registry -> registry
                .requestMatchers("/swagger-ui/**", "/api/**").permitAll()
        );
    }

    private void configureUserEndpoints(
            HttpSecurity http,
            AuthorizationManager<RequestAuthorizationContext> customAuthorizationManager
    ) throws Exception {
        http.authorizeHttpRequests(registry -> registry
                .requestMatchers(HttpMethod.PUT, "/users/{userId}")
                .access(customAuthorizationManager)
                .requestMatchers(HttpMethod.DELETE, "/users/{userId}")
                .access(customAuthorizationManager)
                .requestMatchers("/users/**").permitAll()
        );
    }

    private void configureEventEndpoints(
            HttpSecurity http,
            AuthorizationManager<RequestAuthorizationContext> customAuthorizationManager
    ) throws Exception {
        http.authorizeHttpRequests(registry -> registry
                .requestMatchers(HttpMethod.POST, "/events").authenticated()
                .requestMatchers(HttpMethod.PUT, "/events/{eventId}")
                .access(customAuthorizationManager)
                .requestMatchers(HttpMethod.DELETE, "/events/{eventId}")
                .access(customAuthorizationManager)
                .requestMatchers(HttpMethod.POST, "/events/{eventId}/participation").authenticated()
                .requestMatchers("/events/**").permitAll()
        );
    }

    private void configureEventTagEndpoints(
            HttpSecurity http
    ) throws Exception {
        http.authorizeHttpRequests(registry -> registry
                .requestMatchers(HttpMethod.GET, "/event-tags").permitAll()
                .requestMatchers(HttpMethod.GET, "/event-tags/{eventTagId}").permitAll()
                .requestMatchers(HttpMethod.POST, "/event-tags").authenticated()
                .requestMatchers(HttpMethod.PUT, "/event-tags/{eventTagId}").authenticated()
                .requestMatchers(HttpMethod.DELETE, "/event-tags/{eventTagId}").authenticated()
                .requestMatchers("/event-tags/**").permitAll()
        );
    }

    private void configureParkEndpoints(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(registry -> registry
                .requestMatchers(HttpMethod.POST, "/parks").hasAuthority("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/parks/{parkId}").hasAuthority("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/parks/{parkId}").hasAuthority("ADMIN")
                .requestMatchers("/parks/**").permitAll()
        );
    }

    private void configureFileEndpoints(
            HttpSecurity http,
            AuthorizationManager<RequestAuthorizationContext> customAuthorizationManager
    ) throws Exception {
        http.authorizeHttpRequests(registry -> registry
                .requestMatchers(HttpMethod.DELETE, "/files/{externalId}")
                .access(customAuthorizationManager)
                .requestMatchers(HttpMethod.POST, "/files").authenticated()
                .requestMatchers("/files/**").permitAll()
        );
    }

    private void configureCountryEndpoints(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(registry -> registry
                .requestMatchers(HttpMethod.PUT, "/countries/{countryId}").hasAuthority("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/countries/{countryId}").hasAuthority("ADMIN")
                .requestMatchers("/countries/**").permitAll()
        );
    }

    private void configureAuthEndpoints(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(registry -> registry
                .requestMatchers("/auth/logout").authenticated()
                .requestMatchers("/auth/refresh").authenticated()
                .requestMatchers("/auth/me").authenticated()
                .requestMatchers("/auth/**").permitAll()
        );
    }

    private void configureAdminEndpoints(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(registry -> registry
                .requestMatchers("/admin/**").hasAuthority("ADMIN")
        );
    }

    private void configureErrorEndpoints(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(registry -> registry
                .requestMatchers("/error").permitAll()
                .anyRequest().authenticated()
        );
    }

    /*
     * This method is used to create a password encoder that is used to
     *  encode the password before storing it in the database.
     * The password encoder is used to encode the password before storing it in the database.
     * @return The PasswordEncoder object that is used to encode the password.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /*
     * This method is used to configure the AuthenticationManager
     * so that it knows how to authenticate a user.
     * The AuthenticationManager is used by the Spring Security to authenticate a user.
     * In this case, we are using the custom user detail service to authenticate a user.
     * The custom user detail service is used to load the user from the database
     * and then authenticate the user.
     * The password encoder is used to encode the password before storing it in the database.
     * @param http The HttpSecurity object that is used to configure the security settings.
     * @return The AuthenticationManager object that is used to authenticate a user.
     */
    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authManagerBuilder = http
                .getSharedObject(AuthenticationManagerBuilder.class);
        authManagerBuilder.userDetailsService(customUserDetailService)
                .passwordEncoder(passwordEncoder());
        return authManagerBuilder.build();
    }

}
