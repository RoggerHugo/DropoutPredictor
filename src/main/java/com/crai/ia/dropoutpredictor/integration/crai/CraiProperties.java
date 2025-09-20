package com.crai.ia.dropoutpredictor.integration.crai;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "crai.api")
public record CraiProperties(String baseUrl, String apiKey) {
}
