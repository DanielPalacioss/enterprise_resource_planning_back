package com.erp.gateway.service;

import com.erp.gateway.model.UserModel;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Map;

@Service
public class JwtServiceImp implements JwtService {

    @Value("${security.jwt.expirationMinutes}")
    private long expirationMinutes;

    @Value("${security.jwt.secretKey}")
    private String secretKey;

    private static final Logger logger = LoggerFactory.getLogger(JwtServiceImp.class);

    @Override
    public String generateToken(UserModel user, Map<String, Object> extraClaims) {

        Date issuedAt = new Date(System.currentTimeMillis());
        Date expiration = new Date(issuedAt.getTime() + expirationMinutes * 60 * 1000);
        return Jwts.builder()
                .claims(extraClaims)
                .subject(user.getUsername())
                .issuedAt(issuedAt)
                .expiration(expiration)
                .signWith(generateKey())
                .compact();
    }

    @Override
    public String extractUsername(String jwt) {
        return extractAllClaims(jwt).getSubject();
    }

    @Override
    public Claims extractAllClaims(String jwt) {
        return Jwts.parser()
                .verifyWith(generateKey())
                .build()
                .parseSignedClaims(jwt)
                .getPayload();
    }

    @Override
    public Boolean validate(String jwt) {
        try {
            Jwts.parser()
                    .verifyWith(generateKey())
                    .build()
                    .parseSignedClaims(jwt)
                    .getPayload()
                    .getSubject();
            return true;
        } catch (ExpiredJwtException e) {
            logger.error("jwt expired");
        } catch (UnsupportedJwtException e) {
            logger.error("jwt unsupported");
        } catch (MalformedJwtException e) {
            logger.error("jwt malformed");
        } catch (SignatureException e) {
            logger.error("bad signature");
        } catch (IllegalArgumentException e) {
            logger.error("illegal args");
        }
        return false;
    }

    @Override
    public SecretKey generateKey() {
        byte[] secretAsBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(secretAsBytes);
    }
}
