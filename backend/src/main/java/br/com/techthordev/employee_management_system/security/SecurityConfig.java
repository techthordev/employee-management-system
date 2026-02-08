package br.com.techthordev.employee_management_system.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
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
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthFilter;

    public SecurityConfig(JwtAuthenticationFilter jwtAuthFilter) {
        this.jwtAuthFilter = jwtAuthFilter;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // 1. Disable CSRF for REST APIs using JWT
                .csrf(AbstractHttpConfigurer::disable)

                // 2. Configure request authorization
                .authorizeHttpRequests(auth -> auth
                        // Permit login endpoint explicitly (This was missing)
                        .requestMatchers("/v1/auth/**").permitAll()

                        // Publicly accessible paths for Swagger and OpenAPI
                        .requestMatchers("/v3/api-docs/**", "/api-docs/**", "/swagger-ui/**", "/swagger-ui.html", "/docs/**").permitAll()

                        // Vaadin specific internal requests and static resources
                        .requestMatchers("/VAADIN/**", "/favicon.ico", "/robots.txt", "/*.js", "/*.css").permitAll()
                        .requestMatchers("/admin/**").permitAll()

                        // Public Angular routes and landing pages
                        .requestMatchers("/", "/index.html", "/login").permitAll()

                        // Protected Business API endpoints
                        .requestMatchers("/v1/admin/**").hasRole("ADMIN")
                        .requestMatchers("/v1/**").authenticated()

                        // All other requests require authentication
                        .anyRequest().authenticated()
                )

                // 3. Set session management to stateless for JWT
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )

                // 4. Add custom JWT filter before standard authentication filter
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }
}