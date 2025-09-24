package com.crai.ia.dropoutpredictor.controllers;

import com.crai.ia.dropoutpredictor.entity.AppUser;
import com.crai.ia.dropoutpredictor.models.AuthRequest;
import com.crai.ia.dropoutpredictor.models.AuthResponse;
import com.crai.ia.dropoutpredictor.models.RegisterRequest;
import com.crai.ia.dropoutpredictor.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    /*
     * Se inabilita el regitro
     * 
     * @PostMapping("/register")
     * public ResponseEntity<AppUser> register(@Valid @RequestBody RegisterRequest
     * request) {
     * return ResponseEntity.ok(authService.register(request));
     * }
     */

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody AuthRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }
}