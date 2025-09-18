package com.gamertools.controller;

import com.gamertools.model.GamingSchedule;
import com.gamertools.service.GamingScheduleService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/schedules")
@CrossOrigin(origins = "*")
public class GamingScheduleController {
    
    @Autowired
    private GamingScheduleService scheduleService;
    
    @GetMapping
    public ResponseEntity<List<GamingSchedule>> getAllUpcomingSchedules() {
        List<GamingSchedule> schedules = scheduleService.getAllUpcomingSchedules();
        return ResponseEntity.ok(schedules);
    }
    
    @GetMapping("/today")
    public ResponseEntity<List<GamingSchedule>> getSchedulesForToday() {
        List<GamingSchedule> schedules = scheduleService.getSchedulesForToday();
        return ResponseEntity.ok(schedules);
    }
    
    @GetMapping("/week")
    public ResponseEntity<List<GamingSchedule>> getSchedulesForWeek() {
        List<GamingSchedule> schedules = scheduleService.getSchedulesForWeek();
        return ResponseEntity.ok(schedules);
    }
    
    @PostMapping
    public ResponseEntity<?> createSchedule(@Valid @RequestBody GamingSchedule schedule) {
        try {
            // Check for conflicts
            List<GamingSchedule> conflicts = scheduleService.getConflictingSchedules(schedule);
            if (!conflicts.isEmpty()) {
                return ResponseEntity.badRequest().body("{\"error\": \"Schedule conflicts with existing sessions\", \"conflicts\": " + conflicts.size() + "}");
            }
            
            GamingSchedule createdSchedule = scheduleService.createSchedule(schedule);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdSchedule);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("{\"error\": \"" + e.getMessage() + "\"}");
        }
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<?> updateSchedule(@PathVariable Long id, @Valid @RequestBody GamingSchedule schedule) {
        try {
            GamingSchedule updatedSchedule = scheduleService.updateSchedule(id, schedule);
            return ResponseEntity.ok(updatedSchedule);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("{\"error\": \"" + e.getMessage() + "\"}");
        }
    }
    
    @PostMapping("/{id}/complete")
    public ResponseEntity<Void> markScheduleCompleted(@PathVariable Long id) {
        scheduleService.markScheduleCompleted(id);
        return ResponseEntity.ok().build();
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSchedule(@PathVariable Long id) {
        scheduleService.deleteSchedule(id);
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping("/analytics")
    public ResponseEntity<Map<String, Object>> getScheduleAnalytics() {
        Map<String, Object> analytics = scheduleService.getScheduleAnalytics();
        return ResponseEntity.ok(analytics);
    }
    
    @GetMapping("/recommendations")
    public ResponseEntity<List<String>> getScheduleRecommendations() {
        List<String> recommendations = scheduleService.generateScheduleRecommendations();
        return ResponseEntity.ok(recommendations);
    }
    
    @GetMapping("/{id}/conflicts")
    public ResponseEntity<List<GamingSchedule>> getConflictingSchedules(@PathVariable Long id) {
        // This would require getting the schedule first, then checking conflicts
        // For simplicity, we'll return empty list for now
        return ResponseEntity.ok(List.of());
    }
}
