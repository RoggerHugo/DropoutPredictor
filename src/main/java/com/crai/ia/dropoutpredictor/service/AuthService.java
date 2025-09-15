package com.crai.ia.dropoutpredictor.service;

import com.crai.ia.dropoutpredictor.entity.AppUser;
import com.crai.ia.dropoutpredictor.models.AuthRequest;
import com.crai.ia.dropoutpredictor.models.AuthResponse;
import com.crai.ia.dropoutpredictor.models.RegisterRequest;

public interface AuthService {
    AppUser register(RegisterRequest request);

    AuthResponse login(AuthRequest request);
}