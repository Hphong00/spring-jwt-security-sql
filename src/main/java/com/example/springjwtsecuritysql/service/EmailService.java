package com.example.springjwtsecuritysql.service;

import com.example.springjwtsecuritysql.service.email.EmailDetails;

public interface EmailService {
    String sendSimpleMail(EmailDetails details);
    String sendMailWithAttachment(EmailDetails details);
    String sendEmailToMultipleRecipients(String email,String to);
}