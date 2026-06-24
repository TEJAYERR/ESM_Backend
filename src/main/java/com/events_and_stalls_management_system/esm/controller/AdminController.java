package com.events_and_stalls_management_system.esm.controller;

import com.events_and_stalls_management_system.esm.dto.EventRequest;
import com.events_and_stalls_management_system.esm.dto.EventUpdateRequest;
import com.events_and_stalls_management_system.esm.dto.LoginRequest;
import com.events_and_stalls_management_system.esm.service.AdminService;
import com.events_and_stalls_management_system.esm.service.EventService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService){
        this.adminService = adminService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest){
        return new ResponseEntity<>(adminService.login(loginRequest), HttpStatus.OK);
    }
}
