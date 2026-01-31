package com.example.SMS_Service_Backend.service;


import org.springframework.stereotype.Service;

@Service
public class OTP_check_service {

    public boolean validateOTP(String userOTP,String savedOTP){
        return userOTP.equals(savedOTP);
    }

}
