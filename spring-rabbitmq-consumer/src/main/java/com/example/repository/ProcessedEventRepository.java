package com.example.repository;

import com.example.entity.ProcessedEvent;
import com.example.entity.constant.EventType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProcessedEventRepository extends JpaRepository<ProcessedEvent, Integer> {

    Optional<ProcessedEvent> findByEventIdAndEventType(String eventId, EventType eventType);
}