package com.erp.accesscontrol.controller;

import com.erp.accesscontrol.service.JwtService;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("ac/jwt")
@CrossOrigin(origins = "http://localhost:8095")
public class JwtController {

    @Autowired
    private JwtService jwtService;

    @GetMapping("getClaims/{token}")
    public ResponseEntity<Claims> getClaims(@PathVariable String token)
    {
        return ResponseEntity.ok(jwtService.extractAllClaims(token));
    }

}
