package com.erp.accesscontrol.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record UpdatePasswordDTO(String username, @NotBlank(message = "password cannot be blank or null") @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%^&*()_-])[a-zA-Z0-9!@#$%^&*()_-]{12,}$",
        message = "The password must have at least 12 characters, one lowercase letter, one uppercase letter, one number, and a special symbol.") String newPassword, String password) {}
