package com.events_and_stalls_management_system.esm.dto;

import com.events_and_stalls_management_system.esm.entity.Admin;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Setter
@Getter
public class LoginResponse {

    UUID id;
    String username;
    String JWTToken;

    public LoginResponse(Admin admin){
        this.id = admin.getId();
        this.username = admin.getUsername();
    }
}
