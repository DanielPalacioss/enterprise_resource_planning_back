package com.erp.mailsender.dto;

public record EmailDTO(String toUser, String subject, String message) {
}
