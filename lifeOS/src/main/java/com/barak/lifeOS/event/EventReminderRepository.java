package com.barak.lifeOS.event;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface EventReminderRepository extends JpaRepository<EventReminder, UUID>{
    void deleteByEvent(Event event);

    boolean existsByEventAndRemindBeforeMinutes(Event event, int minutes);

    List<EventReminder> findByEvent(Event event);
}
