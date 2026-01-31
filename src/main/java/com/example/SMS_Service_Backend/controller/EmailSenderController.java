package com.example.SMS_Service_Backend.controller;


import com.example.SMS_Service_Backend.dto.emailSendDTO;
import com.example.SMS_Service_Backend.dto.userOTP;
import com.example.SMS_Service_Backend.service.EmailService;
import com.example.SMS_Service_Backend.service.OTP_check_service;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/email")
public class EmailSenderController {

    @Autowired
    private EmailService emailService;
    @Autowired
    private OTP_check_service otpCheckService;


    @PostMapping("/send")
    public String getOtpEmail(@RequestBody Map<String,String> body, HttpSession httpSession){
        String email= body.get("email");
        if(email.isBlank()||email==null){
            return "Email is required";
        }
        String getOTP = emailService.sendMail(email);

        httpSession.setAttribute("otp",getOTP);
        httpSession.setAttribute("otpTime", System.currentTimeMillis());

        return "OTP sent successfully.";
    }

    @PostMapping("/validate")
    public String checkOTP(@RequestBody Map<String, String> body,HttpSession httpSession){
        String userOTP = body.get("otp");

        if(userOTP.isBlank()|| userOTP==null){
            return "Enter otp..";
        }


        String savedOTP = (String)httpSession.getAttribute("otp");
        Long otpTime = (Long)httpSession.getAttribute("otpTime");

        if(savedOTP==null || otpTime==null){
            return "OTP expired. Please resend.";
        }

        long now = System.currentTimeMillis();
        long diff = now - otpTime;

        if (diff > 5 * 60 * 1000) { // 5 minutes
            httpSession.removeAttribute("otp");
            httpSession.removeAttribute("otpTime");
            return "OTP expired. Please resend.";
        }

        boolean isValid = otpCheckService.validateOTP(userOTP, savedOTP);

        if(isValid==true){
            // Clear OTP after success
            httpSession.removeAttribute("otp");
            httpSession.removeAttribute("otpTime");

            return "OTP verified successfully ✅";
        }
        return "Invalid OTP ❌";
    }

}
