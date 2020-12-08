package com.javatechie.springsecurityjwtexample.services;

import com.javatechie.springsecurityjwtexample.services.logService.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.logging.Level;

@Service
public class SendEmailService {
    @Autowired
    private JavaMailSender javaMailSender;

    public void sendEmail(String strTo, String strFrom, String strSubject, String strText) {
        try {
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setTo(strTo);
            mailMessage.setFrom(strFrom);
            mailMessage.setSubject(strSubject);
            mailMessage.setText(strText);
            javaMailSender.send(mailMessage);
        } catch (Exception e) {
            LogService.log(Level.SEVERE, e.toString(), e);
        }
    }

}
