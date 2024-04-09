package com.erp.mailsender.dto;

import org.springframework.web.multipart.MultipartFile;

public record EmailWithFileDTO(String toUser, String subject, String message, MultipartFile file) {
}
