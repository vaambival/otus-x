package ru.otus.otusx.logic.service.impl;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import ru.otus.otusx.logic.service.JwtService;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

@Service
public class JwtServiceImpl implements JwtService {
    @Value("${otus-x.token.key}")
    private String secretKey;
    @Value("${otus-x.token.expiration-in-minutes}")
    private Long expiredInMinutes;

    @Override
    public String extractUser(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    @Override
    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        if (userDetails instanceof User user) {
            claims.put("uuid", user.getUsername());
            claims.put("authorities", user.getAuthorities());
        }
        return generateToken(claims, userDetails);
    }

    @Override
    public boolean isTokenValid(String token, UserDetails userDetails) {
        var uuid = extractUser(token);
        return (Objects.equals(uuid, userDetails.getUsername())) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private String generateToken(Map<String, Object> claims, UserDetails userDetails) {
        return Jwts.builder()
                .claims(claims)
                .subject(userDetails.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expiredInMinutes * 1_000))
                .signWith(getSignIn(), Jwts.SIG.HS256).compact();
    }

    private <T> T extractClaim(String token, Function<Claims, T> resolver) {
        final var claims = extractAllClaims(token);
        return resolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser().verifyWith(getSignIn()).build()
                .parseSignedClaims(token).getPayload();
    }

    private SecretKey getSignIn() {
        return Keys.hmacShaKeyFor(secretKey.getBytes());
    }
}
