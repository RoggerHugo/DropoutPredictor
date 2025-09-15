package com.crai.ia.dropoutpredictor.service.implement;

import com.crai.ia.dropoutpredictor.beans.JwtService;
import com.crai.ia.dropoutpredictor.entity.AppUser;
import com.crai.ia.dropoutpredictor.models.AuthRequest;
import com.crai.ia.dropoutpredictor.models.AuthResponse;
import com.crai.ia.dropoutpredictor.models.RegisterRequest;
import com.crai.ia.dropoutpredictor.repository.AppUserRepository;
import com.crai.ia.dropoutpredictor.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AppUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @Override
    public AppUser register(RegisterRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new IllegalArgumentException("El usuario ya existe");
        }
        AppUser user = AppUser.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole())
                .build();
        return userRepository.save(user);
    }

    @Override
    public AuthResponse login(AuthRequest request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        } catch (AuthenticationException ex) {
            throw new IllegalArgumentException("Credenciales inválidas");
        }

        var user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));

        String token = jwtService.generateToken(user);
        // ttl-minutes puede venir de properties; exponemos en segundos
        return new AuthResponse(token, "Bearer", 60L * Long.parseLong(System.getProperty("app.jwt.ttl-minutes", "60")));
    }
}