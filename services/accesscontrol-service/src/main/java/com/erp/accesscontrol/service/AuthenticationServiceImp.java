package com.erp.accesscontrol.service;

import com.erp.accesscontrol.error.exceptions.RequestException;
import com.erp.accesscontrol.dto.AuthenticationRequestDTO;
import com.erp.accesscontrol.dto.AuthenticationResponseDTO;
import com.erp.accesscontrol.model.UserModel;
import com.erp.accesscontrol.repository.UserRepository;
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
    public AuthenticationResponseDTO login(AuthenticationRequestDTO authRequest) {
        UserModel user = userRepository.findByUsername(authRequest.username()).orElseThrow(() -> new RequestException("User not found","404-Not Found"));
        if(!user.getIsEnabled()) throw new RequestException("User is not enabled","400-Bad Request");
        else if(!user.getIsAccountNonLocked()) throw new RequestException("The user is blocked","400-Bad Request");
        else if(!user.getValidationQuestionsCompleted()) throw new RequestException("The user has not completed the validation questions","400-Bad Request");
        else if (!passwordEncoder.matches(authRequest.password(), user.getPassword())) throw new RequestException("Bad credentials","400-Bad Request");
        String jwt = jwtService.generateToken(user, generateExtraClaims(user));
        return new AuthenticationResponseDTO(jwt);
    }

    private Map<String, Object> generateExtraClaims(UserModel user) {
        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("name", user.getFullName());
        extraClaims.put("role", user.getRole().getName());
        return extraClaims;
    }

}
