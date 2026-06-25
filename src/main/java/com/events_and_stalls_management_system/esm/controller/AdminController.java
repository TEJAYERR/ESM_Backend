package com.events_and_stalls_management_system.esm.controller;

import com.events_and_stalls_management_system.esm.dto.EventRequest;
import com.events_and_stalls_management_system.esm.dto.EventUpdateRequest;
import com.events_and_stalls_management_system.esm.dto.LoginRequest;
import com.events_and_stalls_management_system.esm.entity.Admin;
import com.events_and_stalls_management_system.esm.repository.AdminRepo;
import com.events_and_stalls_management_system.esm.service.AdminService;
import com.events_and_stalls_management_system.esm.service.EventService;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private final AdminService adminService;
    private final AdminRepo adminRepo;

    public AdminController(AdminService adminService, AdminRepo adminRepo){
        this.adminService = adminService;
        this.adminRepo = adminRepo;
    }

    @Setter
    @Getter
    static class Register{
        String name;
        String password;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Register register){
        Admin admin = new Admin();
        admin.setUsername(register.getName());
        admin.setPassword(new BCryptPasswordEncoder().encode(register.password));
        adminRepo.save(admin);

        return new ResponseEntity<>(register, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest){
        return new ResponseEntity<>(adminService.login(loginRequest), HttpStatus.OK);
    }
}
