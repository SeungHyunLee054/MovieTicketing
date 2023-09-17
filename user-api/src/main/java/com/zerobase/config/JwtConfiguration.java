package com.zerobase.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JwtConfiguration {
    @Bean
    public TokenProvider tokenProvider() {
        return new TokenProvider();
    }
}
