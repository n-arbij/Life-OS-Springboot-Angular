package com.barak.lifeOS.event;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.barak.lifeOS.user.User;

public interface EventRepository extends JpaRepository<Event, UUID>{
    List<Event> findByUserAndStatus(User user, Status status);

    Optional<Event> findByIdAndUser(UUID id, User user);

    @Query("""
        SELECT e FROM Event e
        WHERE e.user = :user
        AND e.status = 'ACTIVE'
        AND e.startDateTime <= :rangeEnd
        AND (e.recurrenceRule IS NOT NULL
            OR e.endDateTime >= :rangeStart)
    """)
    List<Event> findCandidateEvents(
        @Param("user") User user,
        @Param("rangeEnd") Instant rangeEnd,
        @Param("rangeStart") Instant rangeStart
    );
}
