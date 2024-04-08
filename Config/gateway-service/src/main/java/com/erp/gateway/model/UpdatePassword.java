package com.erp.gateway.model;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public record UpdatePassword(String username, String newPassword, String password, String token) {
}
