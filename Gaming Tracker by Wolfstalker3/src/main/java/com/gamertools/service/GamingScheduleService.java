package com.gamertools.service;

import com.gamertools.model.GamingSchedule;
import com.gamertools.model.ScheduleType;
import com.gamertools.repository.GamingScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.DayOfWeek;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class GamingScheduleService {
    
    @Autowired
    private GamingScheduleRepository scheduleRepository;
    
    public List<GamingSchedule> getAllUpcomingSchedules() {
        return scheduleRepository.findByIsCompletedFalseOrderByScheduledTimeAsc();
    }
    
    public List<GamingSchedule> getSchedulesForToday() {
        LocalDateTime startOfDay = LocalDateTime.now().toLocalDate().atStartOfDay();
        LocalDateTime endOfDay = startOfDay.plusDays(1).minusSeconds(1);
        
        return scheduleRepository.findSchedulesBetween(startOfDay, endOfDay);
    }
    
    public List<GamingSchedule> getSchedulesForWeek() {
        LocalDateTime startOfWeek = LocalDateTime.now().toLocalDate().atStartOfDay();
        LocalDateTime endOfWeek = startOfWeek.plusDays(7);
        
        return scheduleRepository.findSchedulesBetween(startOfWeek, endOfWeek);
    }
    
    public GamingSchedule createSchedule(GamingSchedule schedule) {
        return scheduleRepository.save(schedule);
    }
    
    public GamingSchedule updateSchedule(Long id, GamingSchedule updatedSchedule) {
        return scheduleRepository.findById(id)
                .map(schedule -> {
                    schedule.setTitle(updatedSchedule.getTitle());
                    schedule.setGame(updatedSchedule.getGame());
                    schedule.setScheduledTime(updatedSchedule.getScheduledTime());
                    schedule.setEstimatedDurationMinutes(updatedSchedule.getEstimatedDurationMinutes());
                    schedule.setType(updatedSchedule.getType());
                    schedule.setDayOfWeek(updatedSchedule.getDayOfWeek());
                    schedule.setDescription(updatedSchedule.getDescription());
                    return scheduleRepository.save(schedule);
                })
                .orElseThrow(() -> new RuntimeException("Schedule not found with id: " + id));
    }
    
    public void markScheduleCompleted(Long id) {
        scheduleRepository.findById(id)
                .ifPresent(schedule -> {
                    schedule.setCompleted(true);
                    scheduleRepository.save(schedule);
                });
    }
    
    public void deleteSchedule(Long id) {
        scheduleRepository.deleteById(id);
    }
    
    public Map<String, Object> getScheduleAnalytics() {
        Map<String, Object> analytics = new HashMap<>();
        
        LocalDateTime weekAgo = LocalDateTime.now().minus(7, java.time.temporal.ChronoUnit.DAYS);
        LocalDateTime monthAgo = LocalDateTime.now().minus(30, java.time.temporal.ChronoUnit.DAYS);
        
        Long completedThisWeek = scheduleRepository.countCompletedSchedulesSince(weekAgo);
        Long completedThisMonth = scheduleRepository.countCompletedSchedulesSince(monthAgo);
        
        List<GamingSchedule> upcomingSchedules = getAllUpcomingSchedules();
        List<GamingSchedule> overdueSchedules = scheduleRepository.findOverdueSchedules(LocalDateTime.now());
        
        analytics.put("completedThisWeek", completedThisWeek != null ? completedThisWeek : 0);
        analytics.put("completedThisMonth", completedThisMonth != null ? completedThisMonth : 0);
        analytics.put("upcomingSchedules", upcomingSchedules.size());
        analytics.put("overdueSchedules", overdueSchedules.size());
        
        // Schedule type distribution
        Map<ScheduleType, Long> typeDistribution = upcomingSchedules.stream()
                .collect(Collectors.groupingBy(GamingSchedule::getType, Collectors.counting()));
        analytics.put("scheduleTypeDistribution", typeDistribution);
        
        return analytics;
    }
    
    public List<String> generateScheduleRecommendations() {
        List<String> recommendations = new ArrayList<>();
        
        List<GamingSchedule> upcomingSchedules = getAllUpcomingSchedules();
        List<GamingSchedule> overdueSchedules = scheduleRepository.findOverdueSchedules(LocalDateTime.now());
        
        if (overdueSchedules.size() > 0) {
            recommendations.add("You have " + overdueSchedules.size() + " overdue gaming sessions. Consider rescheduling or marking them as completed.");
        }
        
        if (upcomingSchedules.isEmpty()) {
            recommendations.add("No upcoming gaming sessions scheduled. Plan your gaming time to maintain consistency!");
        }
        
        // Check for scheduling conflicts (sessions too close together)
        List<GamingSchedule> todaySchedules = getSchedulesForToday();
        for (int i = 0; i < todaySchedules.size() - 1; i++) {
            GamingSchedule current = todaySchedules.get(i);
            GamingSchedule next = todaySchedules.get(i + 1);
            
            if (current.getEstimatedDurationMinutes() != null) {
                LocalDateTime currentEnd = current.getScheduledTime().plusMinutes(current.getEstimatedDurationMinutes());
                if (currentEnd.isAfter(next.getScheduledTime().minusMinutes(15))) {
                    recommendations.add("Potential scheduling conflict detected between '" + 
                            current.getTitle() + "' and '" + next.getTitle() + "'. Consider adjusting times.");
                }
            }
        }
        
        // Weekly gaming balance
        List<GamingSchedule> weekSchedules = getSchedulesForWeek();
        Map<DayOfWeek, Long> dailyDistribution = weekSchedules.stream()
                .collect(Collectors.groupingBy(
                    s -> s.getScheduledTime().getDayOfWeek(),
                    Collectors.counting()
                ));
        
        long maxSessionsPerDay = dailyDistribution.values().stream().mapToLong(Long::longValue).max().orElse(0);
        if (maxSessionsPerDay > 4) {
            recommendations.add("You have many gaming sessions scheduled for some days. Consider spreading them out for better balance.");
        }
        
        if (recommendations.isEmpty()) {
            recommendations.add("Your gaming schedule looks well organized! Keep up the good planning.");
        }
        
        return recommendations;
    }
    
    // Scheduled task to send reminders (runs every 15 minutes)
    @Scheduled(fixedRate = 900000) // 15 minutes in milliseconds
    public void checkForReminders() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime reminderTime = now.plusMinutes(30); // 30 minutes before
        
        List<GamingSchedule> schedules = scheduleRepository.findSchedulesNeedingReminder(now, reminderTime);
        
        for (GamingSchedule schedule : schedules) {
            // In a real application, you would send notifications here
            // For now, we'll just mark the reminder as sent
            schedule.setReminderSent(true);
            scheduleRepository.save(schedule);
            
            System.out.println("Reminder: Gaming session '" + schedule.getTitle() + 
                             "' is scheduled in 30 minutes at " + schedule.getScheduledTime());
        }
    }
    
    public List<GamingSchedule> getConflictingSchedules(GamingSchedule newSchedule) {
        LocalDateTime startTime = newSchedule.getScheduledTime();
        LocalDateTime endTime = startTime.plusMinutes(
            newSchedule.getEstimatedDurationMinutes() != null ? 
            newSchedule.getEstimatedDurationMinutes() : 60
        );
        
        return scheduleRepository.findSchedulesBetween(startTime.minusMinutes(15), endTime.plusMinutes(15))
                .stream()
                .filter(s -> !s.getId().equals(newSchedule.getId()) && !s.isCompleted())
                .collect(Collectors.toList());
    }
}
