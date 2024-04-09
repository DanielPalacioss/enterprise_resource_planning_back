package com.erp.mailsender.service;

import com.erp.mailsender.dto.EmailDTO;
import jakarta.mail.MessagingException;

import java.io.File;

public interface EmailService {

    void sendEmail(EmailDTO emailDTO);
    void sendEmailWithFile(String toUser, String subject, String message, File file) throws MessagingException;

}
