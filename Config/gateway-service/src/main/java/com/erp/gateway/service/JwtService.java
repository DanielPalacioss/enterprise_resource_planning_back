package com.erp.gateway.service;

import io.jsonwebtoken.Claims;

import javax.crypto.SecretKey;


public interface JwtService {

    String extractUsername(String jwt);
    Claims extractAllClaims(String jwt);
    SecretKey generateKey();

}
