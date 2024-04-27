package com.erp.accesscontrol.service;

import com.erp.accesscontrol.model.UserModel;
import io.jsonwebtoken.Claims;

import javax.crypto.SecretKey;
import java.util.Map;

public interface JwtService {

    String generateToken(UserModel user, Map<String, Object> extraClaims);

    Claims extractAllClaims(String jwt);

    SecretKey generateKey();

}
