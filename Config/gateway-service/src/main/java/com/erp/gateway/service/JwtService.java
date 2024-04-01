package com.erp.gateway.service;

import com.erp.gateway.model.UserModel;
import io.jsonwebtoken.Claims;

import java.util.Map;

public interface JwtService {

    String generateToken(UserModel user, Map<String,Object> extraClaims);

    String extractUsername(String jwt);
    Claims extractAllClaims(String jwt);
}
