package com.barak.lifeOS.event;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.barak.lifeOS.event.EventDto.OccurrenceResponse;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/events")
public class EventController {
    private final EventService eventService;

    @GetMapping
    public ResponseEntity<List<EventDto.Response>> getAll(){
        return ResponseEntity.ok(eventService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventDto.Response> getById(@PathVariable UUID id){
        return ResponseEntity.ok(eventService.getById(id));
    }

    @GetMapping("/range")
    public ResponseEntity<List<OccurrenceResponse>> getByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to) {
        return ResponseEntity.ok(eventService.getByDateRange(from, to));
    }

    @GetMapping("/reminders/{eventId}")
    public ResponseEntity<List<EventReminderDto.Response>> getRemindersByEvent(@PathVariable UUID eventId){
        return ResponseEntity.ok(eventService.getRemindersByEvent(eventId));
    }

    @PostMapping("/create")
    public ResponseEntity<EventDto.Response> create(@Valid @RequestBody EventDto.CreateRequest request){
        return ResponseEntity.status(HttpStatus.CREATED).body(eventService.createEvent(request));
    }

    @PostMapping("/{id}/reminders")
    public ResponseEntity<EventReminderDto.Response> addReminder(
            @PathVariable UUID id,
            @RequestParam int minutes) {
        return ResponseEntity.ok(eventService.addReminder(id, minutes));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EventDto.Response> update(@PathVariable UUID id, @Valid @RequestBody EventDto.UpdateRequest request){
        return ResponseEntity.ok(eventService.updateEvent(id, request));
    }

    @PutMapping("/{id}/cancel")
    public ResponseEntity<EventDto.Response> cancel(@PathVariable UUID id){
        eventService.cancelEvent(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/reminders/{id}")
    public ResponseEntity<EventReminderDto.Response> removeReminder(@PathVariable UUID id){
        eventService.cancelEvent(id);
        return ResponseEntity.noContent().build();
    }
}
