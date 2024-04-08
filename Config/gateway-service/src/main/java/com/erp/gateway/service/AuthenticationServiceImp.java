package com.erp.gateway.service;

import com.erp.gateway.error.exceptions.RequestException;
import com.erp.gateway.model.AuthenticationRequest;
import com.erp.gateway.model.AuthenticationResponse;
import com.erp.gateway.model.UserModel;
import com.erp.gateway.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AuthenticationServiceImp implements AuthenticationService {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public AuthenticationResponse login(AuthenticationRequest authRequest) {
        UserModel user = userRepository.findByUsername(authRequest.getUsername()).orElseThrow(() -> new RequestException("User not found","404-Not Found"));
        if (!passwordEncoder.matches(authRequest.getPassword(), user.getPassword())) {
            throw new RequestException("Bad credentials","400-Bad Request");
        }
        String jwt = jwtService.generateToken(user, generateExtraClaims(user));
        return new AuthenticationResponse(jwt);
    }

    private Map<String, Object> generateExtraClaims(UserModel user) {
        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("name", user.getFullName());
        extraClaims.put("role", user.getRole().getName());
        return extraClaims;
    }

}
