package com.events_and_stalls_management_system.esm.repository;

import com.events_and_stalls_management_system.esm.dto.EventResponse;
import com.events_and_stalls_management_system.esm.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface EventRepo extends JpaRepository<Event, UUID> {
}
