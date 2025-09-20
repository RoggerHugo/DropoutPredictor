package com.crai.ia.dropoutpredictor.config;

import com.crai.ia.dropoutpredictor.integration.crai.CraiProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(CraiProperties.class)
public class CraiConfig {
}
