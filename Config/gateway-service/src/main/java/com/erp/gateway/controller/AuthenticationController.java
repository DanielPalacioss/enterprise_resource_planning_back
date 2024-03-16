package com.erp.gateway.controller;

import com.erp.gateway.model.AuthenticationRequest;
import com.erp.gateway.model.AuthenticationResponse;
import com.erp.gateway.repository.PermissionRepository;
import com.erp.gateway.service.AuthenticationService;
import com.erp.gateway.util.Permission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/auth")
@CrossOrigin()
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
    public List<Permission> publico()
    {
        List<String> permisos =new ArrayList<>();
        permisos.add("ALL_SALESMANAGEMENT");
        permisos.add("READ_CUSTOMER");
        List<Permission> permissions = permissionRepository.findAllByName(permisos);
        System.out.println(permissions);
        return permissionRepository.findAllByName(permisos);

    }

}
