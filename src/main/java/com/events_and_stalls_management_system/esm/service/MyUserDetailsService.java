package com.events_and_stalls_management_system.esm.service;

import com.events_and_stalls_management_system.esm.entity.Admin;
import com.events_and_stalls_management_system.esm.entity.UserPrinciple;
import com.events_and_stalls_management_system.esm.repository.AdminRepo;
import org.jspecify.annotations.NullMarked;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService {

    AdminRepo adminRepo;

    public MyUserDetailsService(AdminRepo adminRepo){
        this.adminRepo = adminRepo;
    }

    @Override
    @NullMarked
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Admin admin = adminRepo.findByUsername(username);

        if(admin == null){
            throw new UsernameNotFoundException("user not found!");
        }

        return new UserPrinciple(admin);
    }
}
