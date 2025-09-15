package com.crai.ia.dropoutpredictor.beans;

import com.crai.ia.dropoutpredictor.entity.AppUser;
import com.crai.ia.dropoutpredictor.entity.Role;
import com.crai.ia.dropoutpredictor.repository.AppUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class DataSeeder {

    private final AppUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Bean
    ApplicationRunner seedAdmin() {
        return args -> {
            if (userRepository.existsByUsername("admin"))
                return;
            AppUser admin = AppUser.builder()
                    .username("admin")
                    .password(passwordEncoder.encode("admin123"))
                    .role(Role.ADMIN)
                    .build();
            userRepository.save(admin);
        };
    }
}