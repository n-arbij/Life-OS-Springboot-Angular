package com.barak.lifeOS.habit;

import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(
    name = "habit_log",
    uniqueConstraints = @UniqueConstraint(
        name = "uq_habit_log_date",
        columnNames = {"habit_id", "log_date"}
    )
)
public class HabitLog {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "habit_id", nullable = false)
    private Habit habit;

    @Column(nullable = false, name = "log_date")
    private LocalDate logDate;

    @Column(nullable = false)
    private boolean completed;

    private Double value;

    private String notes;

    @Column(nullable = false)
    private Instant loggedAt = Instant.now();
}
