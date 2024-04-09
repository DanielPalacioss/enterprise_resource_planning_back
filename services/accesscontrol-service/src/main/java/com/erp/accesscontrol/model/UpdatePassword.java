package com.erp.accesscontrol.model;

public record UpdatePassword(String username, String newPassword, String password, String token) {
}
