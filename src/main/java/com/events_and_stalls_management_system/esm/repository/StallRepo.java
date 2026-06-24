package com.events_and_stalls_management_system.esm.repository;

import com.events_and_stalls_management_system.esm.entity.Stall;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface StallRepo extends JpaRepository<Stall, UUID> {
    List<Stall> findByEventId(UUID event_id);

    Optional<Stall> findByIdAndEventId(UUID id, UUID eventId);
}
