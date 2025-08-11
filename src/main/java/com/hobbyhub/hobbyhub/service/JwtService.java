package com.hobbyhub.hobbyhub.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

/**
 * Simple JWT utility to create and validate tokens.
 */
@Service
public class JwtService {

    private final Algorithm algorithm;
    private final JWTVerifier verifier;
    private final long expirationMinutes;

    public JwtService(@Value("${jwt.secret:dev-secret}") String secret,
                      @Value("${jwt.expMinutes:120}") long expirationMinutes) {
        this.algorithm = Algorithm.HMAC256(secret);
        this.verifier = JWT.require(this.algorithm).build();
        this.expirationMinutes = expirationMinutes;
    }

    public String generateToken(String userId) {
        Instant now = Instant.now();
        return JWT.create()
                .withSubject(userId)
                .withIssuedAt(Date.from(now))
                .withExpiresAt(Date.from(now.plus(expirationMinutes, ChronoUnit.MINUTES)))
                .sign(algorithm);
    }

    public String validate(String token) {
        try {
            DecodedJWT jwt = verifier.verify(token);
            return jwt.getSubject();
        } catch (JWTVerificationException ex) {
            return null;
        }
    }
}

