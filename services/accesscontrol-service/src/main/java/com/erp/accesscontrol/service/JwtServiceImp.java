package com.erp.accesscontrol.service;

import com.erp.accesscontrol.error.exceptions.RequestException;
import com.erp.accesscontrol.model.UserModel;
import com.erp.accesscontrol.repository.UserRepository;
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

    @Value("${service.security.jwt.expirationMinutes}")
    private long expirationMinutes;

    @Value("${service.security.jwt.secretKey}")
    private String secretKey;

    private final UserRepository userRepository;

    private static final Logger logger = LoggerFactory.getLogger(JwtServiceImp.class);

    public JwtServiceImp(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

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
    public Boolean validate(String jwt) {
        try {
            if(userRepository.findByUsername(extractUsername(jwt)).isEmpty()) throw new RequestException("Username not found","404-Not found");
            Jwts.parser()
                    .verifyWith(generateKey())
                    .build()
                    .parseSignedClaims(jwt)
                    .getPayload()
                    .getSubject();
            return true;
        } catch (ExpiredJwtException e) {
            throw new RequestException("jwt expired","400-Bad Request");
        } catch (UnsupportedJwtException e) {
            throw new RequestException("jwt unsupported","400-Bad Request");
        } catch (MalformedJwtException e) {
            throw new RequestException("jwt malformed","400-Bad Request");
        } catch (SignatureException e) {
            throw new RequestException("bad signature","400-Bad Request");
        } catch (IllegalArgumentException e) {
            throw new RequestException("illegal args","400-Bad Request");
        }
    }

    @Override
    public SecretKey generateKey() {
        byte[] secretAsBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(secretAsBytes);
    }
}
