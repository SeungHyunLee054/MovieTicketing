package com.zerobase.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JwtConfig {
    @Bean
    public TokenProvider provider() {
        return new TokenProvider();
    }
}
