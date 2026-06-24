package com.events_and_stalls_management_system.esm.repository;

import com.events_and_stalls_management_system.esm.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AdminRepo extends JpaRepository<Admin, UUID> {
    Admin findByUsername(String username);
}
