package com.barak.lifeOS.event;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import jakarta.validation.constraints.NotBlank;

public class EventDto {
    public record CreateRequest(
       @NotBlank String title,
       String description,
       Instant startDateTime,
       Instant endDateTime,
       LocalDate startDate,
       LocalDate endDate,
       boolean allDay,
       String location,
       @NotBlank String color,
       String recurrenceRule,
       Status status,
       List<Integer> reminderMinutes
    ) {}

    public record UpdateRequest(
        String title,
        String description,
        Instant startDateTime,
        Instant endDateTime,
        LocalDate startDate,
        LocalDate endDate,
        Boolean allDay,
        String location,
        String color,
        String recurrenceRule,          // new rule — replaces existing
        Boolean removeRecurrence,       // true = strip rule, make one-time
        List<Integer> reminderMinutes,  // null = don't touch, empty = remove all
        RecurrenceEditScope editScope   // ALL (only supported for now)
    ) {}

    public record Response(
        UUID id,
        String title,
        String description,
        Instant startTime,
        Instant endTime,
        boolean allDay,
        String location,
        String color,
        Status status,
        EventStatus eventStatus,
        String recurrenceRule,
        Instant createdAt,
        Instant updatedAt,
        List<EventReminder> reminders
    ) {
        public static Response fromEntity(Event event){
            return new Response(
                event.getId(),
                event.getTitle(),
                event.getDescription(),
                event.getStartDateTime(),
                event.getEndDateTime(),
                event.isAllDay(),
                event.getLocation(),
                event.getColor(),
                event.getStatus(),
                event.getEventStatus(),
                event.getRecurrenceRule(),
                event.getCreatedAt(),
                event.getUpdatedAt(),
                event.getReminders()
            );
        }
    }

    public record SummaryResponse(
        UUID id,
        String title,
        Instant startTime,
        Instant endTime,
        String color,
        boolean allDay
    ) {}

    public record OccurrenceResponse(
        UUID seriesId,          // parent event id — same for all occurrences in a series
        String title,
        String description,
        Instant startDateTime,
        Instant endDateTime,
        boolean allDay,
        String location,
        String color,
        boolean isRecurring,
        EventStatus eventStatus
    ) {}
}
