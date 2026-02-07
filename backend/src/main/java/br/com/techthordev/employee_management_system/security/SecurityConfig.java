package br.com.techthordev.employee_management_system.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

/**
 * Spring Security Configuration - Best Practice Spring Boot 4.0
 *
 * Schema Separation:
 * - auth.users + auth.authorities → Authentication (Spring Security Standard)
 * - public.employee → Business Logic
 *
 * Authentication: HTTP Basic (stateless)
 * Authorization: Role-Based Access Control (RBAC)
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(Customizer.withDefaults())
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authorizeHttpRequests(auth -> auth
                        // Public Endpoints
                        .requestMatchers("/error").permitAll()
                        .requestMatchers("/actuator/health/**").permitAll()
                        .requestMatchers("/actuator/info").permitAll()

                        // Vaadin Admin UI - Admin only
                        .requestMatchers("/admin/**").hasRole("ADMIN")

                        // Employee API v1 - RBAC by HTTP Method
                        .requestMatchers(HttpMethod.GET, "/v1/employees/**")
                        .hasAnyRole("EMPLOYEE", "MANAGER", "ADMIN")

                        .requestMatchers(HttpMethod.POST, "/v1/employees/**")
                        .hasAnyRole("MANAGER", "ADMIN")

                        .requestMatchers(HttpMethod.PUT, "/v1/employees/**")
                        .hasAnyRole("MANAGER", "ADMIN")

                        .requestMatchers(HttpMethod.PATCH, "/v1/employees/**")
                        .hasAnyRole("MANAGER", "ADMIN")

                        .requestMatchers(HttpMethod.DELETE, "/v1/employees/**")
                        .hasRole("ADMIN")

                        // Lock down everything else
                        .anyRequest().authenticated()
                )
                .httpBasic(Customizer.withDefaults())
                .build();
    }

    /**
     * UserDetailsService backed by PostgreSQL auth schema.
     * Uses Spring Security's standard JDBC implementation.
     */
    @Bean
    public UserDetailsService userDetailsService(DataSource dataSource) {
        JdbcUserDetailsManager manager = new JdbcUserDetailsManager(dataSource);

        // Custom queries to read from auth schema
        manager.setUsersByUsernameQuery(
                "SELECT username, password, enabled FROM auth.users WHERE username = ?"
        );

        manager.setAuthoritiesByUsernameQuery(
                "SELECT username, authority FROM auth.authorities WHERE username = ?"
        );

        return manager;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return web -> web.ignoring().requestMatchers(
                "/favicon.ico",
                "/api-docs/**",
                "/swagger-ui/**",
                "/v3/api-docs/**",
                "/docs/**"
        );
    }
}