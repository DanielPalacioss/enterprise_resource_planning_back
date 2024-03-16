package com.erp.gateway.service;

import com.erp.gateway.model.UserModel;

import java.util.Map;

public interface JwtService {

    String generateToken(UserModel user, Map<String,Object> extraClaims);
}
