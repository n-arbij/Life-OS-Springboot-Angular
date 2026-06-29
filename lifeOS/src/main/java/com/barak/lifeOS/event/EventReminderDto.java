package com.barak.lifeOS.event;

import java.util.UUID;

public class EventReminderDto {

    public record Request(
        int minutes
    ) {}

    public record Response(
        UUID id,
        Integer reminderBeforeMinutes,
        boolean notified
    ) {
        public static Response fromEntity(EventReminder reminder){
            return new Response(
                reminder.getId(),
                reminder.getRemindBeforeMinutes(),
                reminder.isNotified()
            );
        }
    }
}
