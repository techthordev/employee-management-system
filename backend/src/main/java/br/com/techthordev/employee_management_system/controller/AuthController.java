package br.com.techthordev.employee_management_system.controller;

import br.com.techthordev.employee_management_system.dto.AuthRequest;
import br.com.techthordev.employee_management_system.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
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

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest authRequest) {
        // 1. Authentifizierung versuchen
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authRequest.username(),
                        authRequest.password()
                )
        );

        // 2. UserDetails extrahieren
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        // 3. Token generieren
        String token = jwtTokenProvider.generateToken(userDetails);

        // 4. Token als JSON zurückgeben
        return ResponseEntity.ok(Map.of("token", token));
    }
}