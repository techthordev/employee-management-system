package br.com.techthordev.backend.controller;

import org.springframework.web.bind.annotation.RequestMapping;

import br.com.techthordev.backend.config.ApiVersionController;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;


@ApiVersionController
@RequestMapping("/test")
public class TestController {

    @GetMapping("/public")
    public String getEndpoint() {
        return "Everybody can see this";
    }

    @GetMapping("/user")
    @PreAuthorize("hasRole('EMPLOYEE')")
    public String userEndpoint() {
        return "Hello Employee";
    }
    
    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public String adminEndpoint() {
        return "Hello Admin! You have full access";
    }
        
}
