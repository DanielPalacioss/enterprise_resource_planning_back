package com.erp.gateway.controller;

import com.erp.gateway.model.AuthenticationRequest;
import com.erp.gateway.model.AuthenticationResponse;
import com.erp.gateway.repository.PermissionRepository;
import com.erp.gateway.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private PermissionRepository permissionRepository;

    @GetMapping("/authenticate")
    public AuthenticationResponse login(@RequestBody AuthenticationRequest authRequest)
    {
        return authenticationService.login(authRequest);
    }

    @GetMapping("/public")
    public String publico()
    {
        /*List<String> permisos =new ArrayList<>();
        permisos.add("ALL_SALESMANAGEMENT");
        permisos.add("READ_CUSTOMER");
        List<Permission> permissions = permissionRepository.findAllByName(permisos);
        System.out.println(permissions);*/
        return "aaa";

    }
    @GetMapping("/publico")
    public String publicos()
    {
        return "prueba de seguridad";
    }
}
