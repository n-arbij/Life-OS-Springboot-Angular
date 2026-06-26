package com.barak.lifeOS.habit;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.barak.lifeOS.user.User;

public interface HabitRepository extends JpaRepository<Habit, UUID>{

    List<Habit> findByUserAndActiveTrue(User user);

    Optional<Habit> findByIdAndUserAndDeletedAtNull(UUID id, User user);

}
