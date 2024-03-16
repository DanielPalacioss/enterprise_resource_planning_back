package com.erp.gateway.service;

import com.erp.gateway.model.UserModel;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.Map;

@Service
public class JwtServiceImp implements JwtService{

    @Value("${security.jwt.expirationMinutes}")
    private long expirationMinutes;

    @Value("${security.jwt.secretKey}")
    private String secretKey;

    @Override
    public String generateToken(UserModel user, Map<String, Object> extraClaims) {

        Date issuedAt = new Date(System.currentTimeMillis());
        Date expiration = new Date(issuedAt.getTime() + expirationMinutes*60*1000);

        Jwts.builder().claims(extraClaims)
                .subject(user.getUsername())
                .issuedAt(issuedAt)
                .expiration(expiration)
                .signWith(generateKey(), SignatureAlgorithm.HS256)
                .header().add("typ","JWT").and()
                .compact();
        return null;
    }

    private Key generateKey()
    {
        byte[] secretAsBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(secretAsBytes);
    }
}
