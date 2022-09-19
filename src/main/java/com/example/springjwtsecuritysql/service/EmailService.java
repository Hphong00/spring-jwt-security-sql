package com.example.springjwtsecuritysql.service;

import com.example.springjwtsecuritysql.service.email.EmailDetails;

import java.util.List;

public interface EmailService {
    String sendSimpleMail(EmailDetails details);
    String sendMailWithAttachment(EmailDetails details);
    String sendEmailToMultipleRecipients(String email,String to);
    List<String> getAllEmail();
}