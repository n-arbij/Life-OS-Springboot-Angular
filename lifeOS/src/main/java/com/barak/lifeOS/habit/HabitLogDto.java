package com.barak.lifeOS.habit;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import jakarta.validation.constraints.NotNull;

public class HabitLogDto {
    public record  LogRequest(
        @NotNull LocalDate logDate,
        Boolean completed,
        Double value,
        String notes
    ) {}

    public record  Response(
        UUID id,
        UUID habitId,
        LocalDate logDate,
        boolean completed,
        Double value,
        String notes,
        Instant loggedAt
    ) {
        public static Response fromEntity(HabitLog habitLog){
            return new Response(
                habitLog.getId(),
                habitLog.getHabit().getId(),
                habitLog.getLogDate(),
                habitLog.isCompleted(),
                habitLog.getValue(),
                habitLog.getNotes(),
                habitLog.getLoggedAt()
            );
        }
    }

    public record WeekSummary(
        LocalDate weekStart,
        LocalDate weekEnd,
        List<DaySummary> days
    ){}


    public record DaySummary(
        LocalDate date,
        boolean scheduled,
        boolean completed,
        Double value
    ) {}
}
