package com.erp.gateway.model;

import jakarta.validation.constraints.Email;

public record UpdateEmail(String username, @Email(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$") String newEmail, String token) {
}
