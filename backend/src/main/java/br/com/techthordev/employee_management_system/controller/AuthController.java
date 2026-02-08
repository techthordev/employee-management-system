package br.com.techthordev.employee_management_system.controller;

import br.com.techthordev.employee_management_system.dto.AuthRequest;
import br.com.techthordev.employee_management_system.security.JwtTokenProvider;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping(value = "/login", consumes = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_FORM_URLENCODED_VALUE
    })
    public ResponseEntity<?> login(
            @RequestParam(required = false) String username, // Used by Swagger (Form Data)
            @RequestParam(required = false) String password, // Used by Swagger (Form Data)
            @RequestBody(required = false) AuthRequest authRequest // Used by Angular (JSON)
    ) {
        // Determine which source to use
        String finalUsername = (authRequest != null) ? authRequest.username() : username;
        String finalPassword = (authRequest != null) ? authRequest.password() : password;

        // 1. Authenticate
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(finalUsername, finalPassword)
        );

        // 2. Generate Token
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String token = jwtTokenProvider.generateToken(userDetails);

        // 3. Return in a format Swagger understands
        // Swagger looks for "access_token" to automatically fill the Bearer header
        return ResponseEntity.ok(Map.of(
                "access_token", token,
                "token_type", "Bearer"
        ));
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletResponse response) {
        // Clear the JWT cookie by setting maxAge to 0
        ResponseCookie cookie = ResponseCookie.from("jwt_token", "")
                .httpOnly(true)
                .path("/")
                .maxAge(0)
                .build();
        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());

        return ResponseEntity.ok(Map.of("message", "Logged out successfully"));
    }
}