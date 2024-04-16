package com.erp.accesscontrol.controller;

import com.erp.accesscontrol.error.DataValidation;
import com.erp.accesscontrol.error.exceptions.RequestException;
import com.erp.accesscontrol.repository.UserRepository;
import com.erp.accesscontrol.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("ac/user")
@CrossOrigin(origins = "http://localhost:8095")
public class UserController {


}
