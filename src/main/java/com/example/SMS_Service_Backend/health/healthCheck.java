package com.example.SMS_Service_Backend.health;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/check")
public class healthCheck {
    @GetMapping("/")
    public String health(){
        return "OK";
    }
}
