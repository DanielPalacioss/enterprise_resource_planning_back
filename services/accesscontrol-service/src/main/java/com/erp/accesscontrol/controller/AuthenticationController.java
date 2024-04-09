package com.erp.accesscontrol.controller;

import com.erp.accesscontrol.model.AuthenticationRequest;
import com.erp.accesscontrol.model.AuthenticationResponse;
import com.erp.accesscontrol.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("ac/auth")
@CrossOrigin(origins = "http://localhost:8095")
public class AuthenticationController {

    @Autowired
    private AuthenticationService authenticationService;

    @GetMapping("/authenticate")
    public AuthenticationResponse login(@RequestBody AuthenticationRequest authRequest) {
        return authenticationService.login(authRequest);
    }

    @GetMapping("/public")
    public String publico() {
        /*List<String> permisos =new ArrayList<>();
        permisos.add("ALL_SALESMANAGEMENT");
        permisos.add("READ_CUSTOMER");
        List<Permission> permissions = permissionRepository.findAllByName(permisos);
        System.out.println(permissions);*/
        return "aaa";

    }

    @GetMapping("/publico")
    public String publicos() {
        return "prueba de seguridad";
    }
}
