package com.barak.lifeOS.habit;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.barak.lifeOS.user.User;

public interface HabitLogRepository extends JpaRepository<HabitLog, UUID>{
    
    List<HabitLog> findByHabitOrderByLogDateDesc(Habit habit);

    Optional<HabitLog> findByHabitAndLogDate(Habit habit, LocalDate logDate);

    @Query("""
        SELECT hl FROM HabitLog hl
        JOIN hl.habit h
        WHERE h.user = :user
        AND hl.logDate = :date
    """)
    List<HabitLog> findByUserAndLogDate(
        @Param("user") User user,
        @Param("date") LocalDate date
    );

    @Query("""
        SELECT hl FROM HabitLog hl
        WHERE hl.habit = :habit
        AND hl.logDate BETWEEN :from AND :to
        ORDER BY hl.logDate ASC
    """)
    List<HabitLog> findByHabitAndDateRange(
        @Param("habit") Habit habit,
        @Param("from") LocalDate from,
        @Param("to") LocalDate to
    );
}
