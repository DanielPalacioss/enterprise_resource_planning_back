package com.erp.gateway.controller;

import com.erp.gateway.model.AuthenticationRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin()
public class AuthenticationController {

    @PostMapping("/authenticate")
    public ResponseEntity<?> login(@RequestBody @Valid AuthenticationRequest authRequest)
    {
        return null;
    }

    @GetMapping("/public")
    public String publico()
    {
        return "holaa";
    }

}
