package com.erp.gateway.service;

import com.erp.gateway.model.AuthenticationRequest;
import com.erp.gateway.model.AuthenticationResponse;
import com.erp.gateway.model.UserModel;
import com.erp.gateway.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AuthenticationServiceImp implements AuthenticationService{

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserRepository userRepository;

    @Override
    public AuthenticationResponse login(AuthenticationRequest authRequest) {
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(authRequest.getUsername(),
                authRequest.getPassword());
        authenticationManager.authenticate(authToken);
        UserModel user = userRepository.findByUsername(authRequest.getUsername()).get();
        String jwt = jwtService.generateToken(user, generateExtraClaims(user));
        return new AuthenticationResponse(jwt);
    }

    private Map<String,Object> generateExtraClaims(UserModel user) {

        Map<String,Object> extraClaims = new HashMap<>();
        extraClaims.put("name", user.getFullName());
        extraClaims.put("role", user.getRole().getName());
        return extraClaims;
    }

}
