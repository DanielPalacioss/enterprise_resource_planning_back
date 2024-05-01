package com.erp.gateway.service;

import com.erp.gateway.error.exceptions.RequestException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;

@Service
public class JwtServiceImp implements JwtService {

    @Value("${service.security.jwt.secretKey}")
    private String secretKey;


    @Override
    public Claims extractAllClaims(String jwt) {
        try {
            return Jwts.parser()
                    .verifyWith(generateKey())
                    .build()
                    .parseSignedClaims(jwt)
                    .getPayload();
        }catch (ExpiredJwtException e) {
            throw new RequestException("jwt expired","400-Bad Request");
        }
    }

    @Override
    public SecretKey generateKey() {
        byte[] secretAsBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(secretAsBytes);
    }
}
