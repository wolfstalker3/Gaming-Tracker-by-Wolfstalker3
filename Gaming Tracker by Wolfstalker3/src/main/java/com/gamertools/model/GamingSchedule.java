package com.gamertools.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.time.DayOfWeek;

@Entity
@Table(name = "gaming_schedules")
public class GamingSchedule {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "Title is required")
    private String title;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "game_id")
    private Game game;
    
    @NotNull(message = "Scheduled time is required")
    @Column(name = "scheduled_time")
    private LocalDateTime scheduledTime;
    
    @Column(name = "estimated_duration_minutes")
    private Integer estimatedDurationMinutes;
    
    @Enumerated(EnumType.STRING)
    private ScheduleType type;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "day_of_week")
    private DayOfWeek dayOfWeek; // For recurring schedules
    
    private String description;
    
    @Column(name = "is_completed")
    private boolean isCompleted = false;
    
    @Column(name = "reminder_sent")
    private boolean reminderSent = false;
    
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    
    // Constructors
    public GamingSchedule() {
        this.createdAt = LocalDateTime.now();
    }
    
    public GamingSchedule(String title, LocalDateTime scheduledTime, ScheduleType type) {
        this();
        this.title = title;
        this.scheduledTime = scheduledTime;
        this.type = type;
    }
    
    // Methods
    public boolean isUpcoming() {
        return scheduledTime.isAfter(LocalDateTime.now()) && !isCompleted;
    }
    
    public boolean isOverdue() {
        return scheduledTime.isBefore(LocalDateTime.now()) && !isCompleted;
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public Game getGame() {
        return game;
    }
    
    public void setGame(Game game) {
        this.game = game;
    }
    
    public LocalDateTime getScheduledTime() {
        return scheduledTime;
    }
    
    public void setScheduledTime(LocalDateTime scheduledTime) {
        this.scheduledTime = scheduledTime;
    }
    
    public Integer getEstimatedDurationMinutes() {
        return estimatedDurationMinutes;
    }
    
    public void setEstimatedDurationMinutes(Integer estimatedDurationMinutes) {
        this.estimatedDurationMinutes = estimatedDurationMinutes;
    }
    
    public ScheduleType getType() {
        return type;
    }
    
    public void setType(ScheduleType type) {
        this.type = type;
    }
    
    public DayOfWeek getDayOfWeek() {
        return dayOfWeek;
    }
    
    public void setDayOfWeek(DayOfWeek dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public boolean isCompleted() {
        return isCompleted;
    }
    
    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }
    
    public boolean isReminderSent() {
        return reminderSent;
    }
    
    public void setReminderSent(boolean reminderSent) {
        this.reminderSent = reminderSent;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
