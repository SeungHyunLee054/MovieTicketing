package com.zerobase.config;

import com.zerobase.common.UserType;
import com.zerobase.common.UserVo;
import com.zerobase.util.Aes256Util;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class TokenProvider {
    private final String secretKey = "SECRETKEYSECRETKEYSECRETKEYSECRETKEYSECRETKEYSECRETKEY";
    private final long TOKEN_VALID_TIME = 1000L * 60 * 60 * 24;

    public String createToken(String email, Long id, boolean adminYn) {
        Claims claims = Jwts.claims()
                .setSubject(Aes256Util.encrypt(email))
                .setId(Aes256Util.encrypt(id.toString()));
        claims.put("adminYn", adminYn);
        Date now = new Date();
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + TOKEN_VALID_TIME))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    public Authentication getAuthentication(String token) {
        List<SimpleGrantedAuthority> grantedAuthorities = getRoles(token).stream()
                .map(SimpleGrantedAuthority::new)
                .toList();

        UserVo userVo = getUserVo(token);

        return new UsernamePasswordAuthenticationToken(
                userVo, token, grantedAuthorities);
    }

    private List<String> getRoles(String token) {
        Claims claims = parseClaims(token);

        Boolean adminYn = claims.get("adminYn", Boolean.class);

        List<String> roles = new ArrayList<>();
        if (adminYn) {
            roles.add(UserType.ADMIN.name());
        }

        return roles;
    }

    public boolean validateToken(String token) {
        Claims claims = parseClaims(token);
        return !claims.getExpiration().before(new Date());
    }

    private Claims parseClaims(String token) {
        try {
            return Jwts.parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }

    public UserVo getUserVo(String token) {
        Claims claims = parseClaims(token);
        return new UserVo(Long.valueOf(
                Objects.requireNonNull(Aes256Util.decrypt(claims.getId()))),
                Aes256Util.decrypt(claims.getSubject()),
                claims.get("adminYn", Boolean.class));
    }
}
