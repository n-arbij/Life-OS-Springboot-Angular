package com.barak.lifeOS.event;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface EventReminderRepository extends JpaRepository<EventReminder, UUID>{
    void deleteByEvent(Event event);
}
