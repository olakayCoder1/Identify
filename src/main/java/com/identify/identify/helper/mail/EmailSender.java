package com.identify.identify.helper.mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class EmailSender {

    @Autowired
    private JavaMailSender emailSender;


    public String sendAccountVerificationMial(String receiver, String code){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("your_email@example.com");
        message.setTo(receiver);
        message.setSubject("Account activation mail");
        message.setText("Your acount activation code is " + code);

        emailSender.send(message);
        return "";
    }

}
