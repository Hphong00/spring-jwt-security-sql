package com.example.springjwtsecuritysql.web;

import com.example.springjwtsecuritysql.core.Constants;
import com.example.springjwtsecuritysql.service.EmailService;
import com.example.springjwtsecuritysql.service.email.EmailDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = Constants.Api.Path.PREFIX)
public class EmailResource {
    @Autowired
    private EmailService emailService;

    // Sending a simple Email
    @PostMapping("/send-mail")
    public String
    sendMail(@RequestBody EmailDetails details) {
        String status = emailService.sendSimpleMail(details);
        return status;
    }

    // Sending email with attachment
    @PostMapping("/send-mail-with-attachment")
    public String sendMailWithAttachment(@RequestBody EmailDetails details) {
        String status = emailService.sendMailWithAttachment(details);
        return status;
    }

    @PostMapping("/send-email-multiple-recipients")
    public String sendEmailToMultipleRecipients(String to, String email) {
        String status = emailService.sendEmailToMultipleRecipients(to,email);
        return status;
    }
}
