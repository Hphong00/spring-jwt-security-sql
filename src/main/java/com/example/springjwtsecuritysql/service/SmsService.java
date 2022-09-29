package com.example.springjwtsecuritysql.service;

import com.example.springjwtsecuritysql.service.sms.SmsRequest;
import com.example.springjwtsecuritysql.service.sms.SmsSender;
import com.example.springjwtsecuritysql.service.sms.TwilioSmsSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

@org.springframework.stereotype.Service
public class SmsService {

    private final SmsSender smsSender;

    @Autowired
    public SmsService(@Qualifier("twilio") TwilioSmsSender smsSender) {
        this.smsSender = smsSender;
    }

    public void sendSms(SmsRequest smsRequest) {
        smsSender.sendSms(smsRequest);
    }
}