package com.crai.ia.dropoutpredictor.models;

import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class AuthResponse {
private String accessToken;
private String tokenType;
private long expiresInSeconds;
}