package com.events_and_stalls_management_system.esm.service;

import com.events_and_stalls_management_system.esm.dto.LoginRequest;
import com.events_and_stalls_management_system.esm.dto.LoginResponse;
import com.events_and_stalls_management_system.esm.entity.Admin;
import com.events_and_stalls_management_system.esm.entity.UserPrinciple;
import com.events_and_stalls_management_system.esm.repository.AdminRepo;
import com.events_and_stalls_management_system.esm.repository.EventRepo;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class AdminService {

    private final AuthenticationManager authenticationManager;
    private final AdminRepo adminRepo;
    private final JWTService jWTService;
    private final EventRepo eventRepo;

    public AdminService(AdminRepo adminRepo, AuthenticationManager authenticationManager, JWTService jWTService, EventRepo eventRepo){
        this.adminRepo = adminRepo;
        this.authenticationManager = authenticationManager;
        this.jWTService = jWTService;
        this.eventRepo = eventRepo;
    }

    public LoginResponse login(LoginRequest loginRequest) {

        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        UserPrinciple userPrinciple = (UserPrinciple) authentication.getPrincipal();

        assert userPrinciple != null;

        Admin admin = userPrinciple.getAdmin();

        LoginResponse loginResponse = new LoginResponse(admin);

        String token = jWTService.generateTokenWithUsername(admin.getUsername());
        loginResponse.setJWTToken(token);

        return loginResponse;
    }
}
