package com.lakshankawshalya.job_portal.controller;

import com.lakshankawshalya.job_portal.dto.AuthRequest;
import com.lakshankawshalya.job_portal.dto.AuthResponse;
import com.lakshankawshalya.job_portal.dto.RegisterRequest;
import com.lakshankawshalya.job_portal.security.JwtUtil;
import com.lakshankawshalya.job_portal.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    // Register a new user
    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody RegisterRequest request) {
        String response = userService.registerUser(request);
        return ResponseEntity.ok(response);
    }

    // Login a user
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest request) {
        String token = userService.authenticate(request.getUsername(), request.getPassword());
        return ResponseEntity.ok(new AuthResponse(token));
    }
}
