package com.gamertools.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.time.Duration;

@Entity
@Table(name = "gaming_sessions")
public class GamingSession {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "game_id", nullable = false)
    @NotNull(message = "Game is required")
    private Game game;
    
    @Column(name = "start_time", nullable = false)
    private LocalDateTime startTime;
    
    @Column(name = "end_time")
    private LocalDateTime endTime;
    
    @Column(name = "duration_minutes")
    private Long durationMinutes;
    
    // Performance metrics
    private Integer kills;
    private Integer deaths;
    private Integer assists;
    private Double accuracy;
    private Integer score;
    
    // System performance during session
    @Column(name = "avg_fps")
    private Double avgFps;
    
    @Column(name = "avg_cpu_usage")
    private Double avgCpuUsage;
    
    @Column(name = "avg_gpu_usage")
    private Double avgGpuUsage;
    
    @Column(name = "avg_memory_usage")
    private Double avgMemoryUsage;
    
    @Column(name = "avg_temperature")
    private Double avgTemperature;
    
    // Session quality metrics
    @Enumerated(EnumType.STRING)
    @Column(name = "mood_before")
    private MoodLevel moodBefore;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "mood_after")
    private MoodLevel moodAfter;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "performance_rating")
    private PerformanceRating performanceRating;
    
    private String notes;
    
    // Constructors
    public GamingSession() {
        this.startTime = LocalDateTime.now();
    }
    
    public GamingSession(Game game) {
        this();
        this.game = game;
    }
    
    // Methods
    public void endSession() {
        this.endTime = LocalDateTime.now();
        this.durationMinutes = Duration.between(startTime, endTime).toMinutes();
    }
    
    public boolean isActive() {
        return endTime == null;
    }
    
    public Double getKdRatio() {
        if (deaths == null || deaths == 0) {
            return kills != null ? kills.doubleValue() : 0.0;
        }
        return kills != null ? (double) kills / deaths : 0.0;
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Game getGame() {
        return game;
    }
    
    public void setGame(Game game) {
        this.game = game;
    }
    
    public LocalDateTime getStartTime() {
        return startTime;
    }
    
    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }
    
    public LocalDateTime getEndTime() {
        return endTime;
    }
    
    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
        if (startTime != null && endTime != null) {
            this.durationMinutes = Duration.between(startTime, endTime).toMinutes();
        }
    }
    
    public Long getDurationMinutes() {
        return durationMinutes;
    }
    
    public void setDurationMinutes(Long durationMinutes) {
        this.durationMinutes = durationMinutes;
    }
    
    public Integer getKills() {
        return kills;
    }
    
    public void setKills(Integer kills) {
        this.kills = kills;
    }
    
    public Integer getDeaths() {
        return deaths;
    }
    
    public void setDeaths(Integer deaths) {
        this.deaths = deaths;
    }
    
    public Integer getAssists() {
        return assists;
    }
    
    public void setAssists(Integer assists) {
        this.assists = assists;
    }
    
    public Double getAccuracy() {
        return accuracy;
    }
    
    public void setAccuracy(Double accuracy) {
        this.accuracy = accuracy;
    }
    
    public Integer getScore() {
        return score;
    }
    
    public void setScore(Integer score) {
        this.score = score;
    }
    
    public Double getAvgFps() {
        return avgFps;
    }
    
    public void setAvgFps(Double avgFps) {
        this.avgFps = avgFps;
    }
    
    public Double getAvgCpuUsage() {
        return avgCpuUsage;
    }
    
    public void setAvgCpuUsage(Double avgCpuUsage) {
        this.avgCpuUsage = avgCpuUsage;
    }
    
    public Double getAvgGpuUsage() {
        return avgGpuUsage;
    }
    
    public void setAvgGpuUsage(Double avgGpuUsage) {
        this.avgGpuUsage = avgGpuUsage;
    }
    
    public Double getAvgMemoryUsage() {
        return avgMemoryUsage;
    }
    
    public void setAvgMemoryUsage(Double avgMemoryUsage) {
        this.avgMemoryUsage = avgMemoryUsage;
    }
    
    public Double getAvgTemperature() {
        return avgTemperature;
    }
    
    public void setAvgTemperature(Double avgTemperature) {
        this.avgTemperature = avgTemperature;
    }
    
    public MoodLevel getMoodBefore() {
        return moodBefore;
    }
    
    public void setMoodBefore(MoodLevel moodBefore) {
        this.moodBefore = moodBefore;
    }
    
    public MoodLevel getMoodAfter() {
        return moodAfter;
    }
    
    public void setMoodAfter(MoodLevel moodAfter) {
        this.moodAfter = moodAfter;
    }
    
    public PerformanceRating getPerformanceRating() {
        return performanceRating;
    }
    
    public void setPerformanceRating(PerformanceRating performanceRating) {
        this.performanceRating = performanceRating;
    }
    
    public String getNotes() {
        return notes;
    }
    
    public void setNotes(String notes) {
        this.notes = notes;
    }
}
