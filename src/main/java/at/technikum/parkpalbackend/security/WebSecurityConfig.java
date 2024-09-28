package at.technikum.parkpalbackend.security;

import at.technikum.parkpalbackend.security.filter.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {


    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    private final CustomUserDetailService customUserDetailService;

    public WebSecurityConfig(
            JwtAuthenticationFilter jwtAuthenticationFilter,
            CustomUserDetailService customUserDetailService
    ) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
        this.customUserDetailService = customUserDetailService;
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
        //  Add JWT filter to the security chain
        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        http.cors(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable);
        http.sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        http.authorizeHttpRequests(registry -> registry
                // Swagger UI access
                .requestMatchers("/api/**").permitAll()
                .requestMatchers("/swagger-ui/**").permitAll()

                .requestMatchers("/users/{userId}/**").hasAuthority("ADMIN")
                .requestMatchers("/users/**").permitAll()
                .requestMatchers("/countries/**").permitAll()
                .requestMatchers("/events/**").permitAll()
                .requestMatchers("/parks/**").permitAll()
                .requestMatchers("/pictures/**").permitAll()
                .requestMatchers("/videos/**").permitAll()
                .requestMatchers("/upload/**").permitAll()
                .requestMatchers("/files/**").permitAll()
                .requestMatchers("/auth/login", "/auth/register", "auth/logout").permitAll()
                // allow errors so that @ResponseStatus() will show and not 401
                .requestMatchers("/error").permitAll()
                .anyRequest().authenticated());
        return http.build();
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
