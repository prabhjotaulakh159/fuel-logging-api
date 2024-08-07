package dev.prabhjotaulakh.fuel.api.services;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;

import dev.prabhjotaulakh.fuel.api.exceptions.JwtException;
import dev.prabhjotaulakh.fuel.api.models.User;

@Service
public class TokenService {
    @Value("${jwt.secret}")
    private String secretKey;

    public String generateToken(User user) {
        final long EXPIRATION_TIME = 1000 * 30 * 60; // 30 minutes

        var jwsHeader = new JWSHeader(new JWSAlgorithm("HS256"));
        var jwtClaims = new JWTClaimsSet.Builder()
            .subject(user.getUsername())
            .issueTime(new Date())
            .expirationTime(new Date(new Date().getTime() + EXPIRATION_TIME))
            .build();

        var jwt = new SignedJWT(jwsHeader, jwtClaims);
        
        try {
            var signer = new MACSigner(secretKey);
            jwt.sign(signer);
        } catch (JOSEException e) {
            throw new JwtException(e.getMessage());
        }

        return jwt.serialize();
    }
}
