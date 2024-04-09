package com.erp.mailsender.service;

import com.erp.mailsender.dto.EmailDTO;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.File;
import java.nio.charset.StandardCharsets;

@Service
public class EmailServiceImp implements EmailService{
    private static final Logger logger = LoggerFactory.getLogger(EmailServiceImp.class);
    private final JavaMailSender mailSender;

    @Value("${service.mail.user.name}")
    private String senderEmail;

    public EmailServiceImp(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }
    @Override
    public void sendEmail(EmailDTO emailDTO) {

        SimpleMailMessage mailMessage = new SimpleMailMessage();

        mailMessage.setFrom(senderEmail);
        mailMessage.setTo(emailDTO.toUser());
        mailMessage.setSubject(emailDTO.subject());
        mailMessage.setText(emailDTO.message());
        logger.info("Starting mail sending");
        mailSender.send(mailMessage);
    }

    @Override
    public void sendEmailWithFile(String toUser, String subject, String message, File file) throws MessagingException {

        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true, StandardCharsets.UTF_8.name());

        messageHelper.setFrom(senderEmail);
        messageHelper.setTo(toUser);
        messageHelper.setSubject(subject);
        messageHelper.setText(message);
        messageHelper.addAttachment(file.getName(), file);
        logger.info("Starting mail sending");
        mailSender.send(mimeMessage);
    }
}
