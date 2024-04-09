package com.erp.mailsender.controller;

import com.erp.mailsender.dto.EmailDTO;
import com.erp.mailsender.dto.EmailWithFileDTO;
import com.erp.mailsender.error.exceptions.RequestException;
import com.erp.mailsender.service.EmailService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@RestController
@RequestMapping("ms")
@CrossOrigin(origins = "http://localhost:8090")
@AllArgsConstructor
public class emailController {

    private final EmailService emailService;

    @PostMapping("sendEmail")
    public ResponseEntity<?> sendEmail(@RequestBody EmailDTO emailDTO)
    {
        emailService.sendEmail(emailDTO);
        return ResponseEntity.status(HttpStatus.OK).body("The email has been sent successfully.");
    }

    @PostMapping("sendEmailWithFile")
    public ResponseEntity<?> sendEmailWithFile(@ModelAttribute EmailWithFileDTO emailWithFileDTO) {
        try {
            String fileName = emailWithFileDTO.file().getOriginalFilename();
            Path path = Paths.get("src/mail/resources/files/"+ fileName);
            Files.createDirectories(path.getParent());
            Files.copy(emailWithFileDTO.file().getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
            File file = path.toFile();
            emailService.sendEmailWithFile(emailWithFileDTO.toUser(), emailWithFileDTO.subject(), emailWithFileDTO.message(), file);
            return ResponseEntity.status(HttpStatus.OK).body("The email has been sent successfully.");
        }catch (Exception e){
            throw new RequestException("Error with sent file","400-Bad Request");
        }
    }
}
