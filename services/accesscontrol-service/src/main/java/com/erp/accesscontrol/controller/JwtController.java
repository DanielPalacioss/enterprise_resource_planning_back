package com.erp.accesscontrol.controller;

import com.erp.accesscontrol.error.exceptions.RequestException;
import com.erp.accesscontrol.repository.UserRepository;
import com.erp.accesscontrol.service.JwtService;
import io.jsonwebtoken.Claims;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("ac/jwt")
@CrossOrigin(origins = "http://localhost:8095")
@AllArgsConstructor
public class JwtController {

    private JwtService jwtService;
    private UserRepository userRepository;

    @GetMapping("getClaims/{token}")
    public ResponseEntity<Claims> getClaims(@PathVariable String token)
    {
        return ResponseEntity.ok(jwtService.extractAllClaims(token));
    }

    @GetMapping("getAuthorities/{username}")
    public ResponseEntity<Collection<? extends GrantedAuthority>> getAuthorities(@PathVariable String username)
    {
        return ResponseEntity
                .ok(userRepository.findByUsername(username)
                        .orElseThrow(
                                () -> new RequestException("Username "+username+" not found","404-Not Found"))
                        .getAuthorities());
    }

}
