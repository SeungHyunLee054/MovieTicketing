package com.zerobase.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JwtConfig {
    @Bean
    public TokenProvider provider(@Value("${jwt.secret}") String secretKey) {
        return new TokenProvider(secretKey);
    }
}
