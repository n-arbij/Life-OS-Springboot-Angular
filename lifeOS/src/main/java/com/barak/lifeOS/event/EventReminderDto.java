package com.barak.lifeOS.event;

import java.util.UUID;

public class EventReminderDto {

    public record Response(
        UUID id,
        String eventTitle,
        Integer reminderBeforeMinutes,
        boolean notified
    ) {
        public static Response fromEntity(EventReminder reminder){
            return new Response(
                reminder.getId(),
                reminder.getEvent().getTitle(),
                reminder.getRemindBeforeMinutes(),
                reminder.isNotified()
            );
        }
    }
}
