package com.erp.accesscontrol.config.security.jwt;

import com.erp.accesscontrol.error.exceptions.RequestException;
import com.erp.accesscontrol.model.UserModel;
import com.erp.accesscontrol.repository.UserRepository;
import com.erp.accesscontrol.service.JwtService;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
public class JwtAuthenticationManager implements AuthenticationManager {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserRepository userRepository;

    @Override
    public Authentication authenticate(Authentication authentication) {
        String token = authentication.getCredentials().toString();
        Claims claims = jwtService.extractAllClaims(token);
        String username = claims.getSubject();
        UserModel user = userRepository.findByUsername(username).orElseThrow(() -> new RequestException("Invalid username", "400-Bad Request"));
        return new UsernamePasswordAuthenticationToken(
                username,
                null,
                user.getAuthorities()
        );
    }
}