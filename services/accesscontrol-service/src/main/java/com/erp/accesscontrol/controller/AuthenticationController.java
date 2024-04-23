package com.erp.accesscontrol.controller;

import com.erp.accesscontrol.dto.AuthenticationRequestDTO;
import com.erp.accesscontrol.dto.AuthenticationResponseDTO;
import com.erp.accesscontrol.service.AuthenticationService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("ac/auth")
@CrossOrigin(origins = "http://localhost:8095")
@AllArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @GetMapping("/authenticate")
    public AuthenticationResponseDTO login(@RequestBody AuthenticationRequestDTO authRequest) {
        return authenticationService.login(authRequest);
    }
}
