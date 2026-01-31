package com.example.SMS_Service_Backend.repository;

import com.example.SMS_Service_Backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRespository extends JpaRepository<User,Long> {
}
