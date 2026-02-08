package br.com.techthordev.employee_management_system.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
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
@EnableMethodSecurity(jsr250Enabled = true)
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthFilter;

    public SecurityConfig(JwtAuthenticationFilter jwtAuthFilter) {
        this.jwtAuthFilter = jwtAuthFilter;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)

                // 1. Enable Form Login for the Browser
                .formLogin(form -> form
                        // Spring Boot provides a default /login page
                        .defaultSuccessUrl("/admin/")
                        .permitAll()
                )

                .logout(logout -> logout
                        .logoutUrl("/v1/auth/logout") // Fixed typo: authh -> auth
                        .deleteCookies("jwt_token", "JSESSIONID")
                        .logoutSuccessUrl("/login")
                        .permitAll()
                )

                // 2. IMPORTANT: Change to IF_REQUIRED to allow Form Login to work
                // while still supporting JWT for the API.
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                )

                .authorizeHttpRequests(auth -> auth
                        // Public paths
                        .requestMatchers("/v1/auth/**", "/login").permitAll()
                        .requestMatchers("/v3/api-docs/**", "/api-docs/**", "/swagger-ui/**", "/swagger-ui.html", "/docs/**").permitAll()
                        .requestMatchers("/VAADIN/**", "/favicon.ico", "/robots.txt", "/*.js", "/*.css").permitAll()
                        .requestMatchers("/", "/index.html").permitAll()

                        // Protected paths
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .requestMatchers("/v1/admin/**").hasRole("ADMIN")
                        .requestMatchers("/v1/employees/**").authenticated()
                        .requestMatchers("/v1/**").authenticated()

                        .anyRequest().authenticated()
                )

                // 3. Add the JWT filter
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