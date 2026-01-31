package com.example.SMS_Service_Backend.service;

import com.example.SMS_Service_Backend.entity.User;
import com.example.SMS_Service_Backend.repository.UserRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;


@Service
public class EmailService {
    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private UserRespository userRespository;

    public String otp(){
        SecureRandom secureRandom = new SecureRandom();
        int min=100000;
        int max=999999;
        int otp = secureRandom.nextInt(max-min+1)+min;
        return Integer.toString(otp);
    }


    public String sendMail(String to){
        try{
            String otp = otp();
            String s="Verification for email.";
            String b=otp+" is your otp to verify your email.Validate for 10 minutes";
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setTo(to);
            mailMessage.setSubject(s);
            mailMessage.setText(b);

            User user = new User();

            user.setEmail(to);
            user.setOtp(otp);

            userRespository.save(user);

            javaMailSender.send(mailMessage);
            return otp;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
