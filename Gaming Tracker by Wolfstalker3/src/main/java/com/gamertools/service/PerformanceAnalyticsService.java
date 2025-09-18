package com.gamertools.service;

import com.gamertools.model.Game;
import com.gamertools.model.GamingSession;
import com.gamertools.model.MoodLevel;
import com.gamertools.model.PerformanceRating;
import com.gamertools.repository.GamingSessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class PerformanceAnalyticsService {
    
    @Autowired
    private GamingSessionRepository sessionRepository;
    
    public Map<String, Object> getPerformanceAnalytics(Game game) {
        Map<String, Object> analytics = new HashMap<>();
        
        List<GamingSession> sessions = sessionRepository.findSessionsWithPerformanceRating(game);
        
        if (sessions.isEmpty()) {
            analytics.put("message", "No performance data available for this game yet.");
            return analytics;
        }
        
        // Basic statistics
        analytics.put("totalSessions", sessions.size());
        analytics.put("averageSessionDuration", calculateAverageSessionDuration(sessions));
        analytics.put("totalPlaytime", calculateTotalPlaytime(sessions));
        
        // Performance trends
        analytics.put("performanceTrend", calculatePerformanceTrend(sessions));
        analytics.put("averagePerformanceRating", calculateAveragePerformanceRating(sessions));
        
        // Mood analysis
        analytics.put("moodAnalysis", analyzeMoodPatterns(sessions));
        
        // System performance
        analytics.put("systemPerformance", analyzeSystemPerformance(sessions));
        
        // Gaming patterns
        analytics.put("gamingPatterns", analyzeGamingPatterns(sessions));
        
        // Recommendations
        analytics.put("recommendations", generateRecommendations(sessions));
        
        return analytics;
    }
    
    private double calculateAverageSessionDuration(List<GamingSession> sessions) {
        return sessions.stream()
                .filter(s -> s.getDurationMinutes() != null)
                .mapToLong(GamingSession::getDurationMinutes)
                .average()
                .orElse(0.0);
    }
    
    private long calculateTotalPlaytime(List<GamingSession> sessions) {
        return sessions.stream()
                .filter(s -> s.getDurationMinutes() != null)
                .mapToLong(GamingSession::getDurationMinutes)
                .sum();
    }
    
    private Map<String, Object> calculatePerformanceTrend(List<GamingSession> sessions) {
        Map<String, Object> trend = new HashMap<>();
        
        List<GamingSession> recentSessions = sessions.stream()
                .filter(s -> s.getPerformanceRating() != null)
                .sorted(Comparator.comparing(GamingSession::getStartTime))
                .collect(Collectors.toList());
        
        if (recentSessions.size() < 2) {
            trend.put("status", "insufficient_data");
            return trend;
        }
        
        // Compare last 5 sessions with previous 5 sessions
        int halfSize = Math.min(5, recentSessions.size() / 2);
        if (halfSize < 2) {
            trend.put("status", "insufficient_data");
            return trend;
        }
        
        List<GamingSession> recent = recentSessions.subList(recentSessions.size() - halfSize, recentSessions.size());
        List<GamingSession> previous = recentSessions.subList(Math.max(0, recentSessions.size() - halfSize * 2), recentSessions.size() - halfSize);
        
        double recentAvg = recent.stream().mapToInt(s -> s.getPerformanceRating().getValue()).average().orElse(0);
        double previousAvg = previous.stream().mapToInt(s -> s.getPerformanceRating().getValue()).average().orElse(0);
        
        double change = recentAvg - previousAvg;
        
        if (change > 0.5) {
            trend.put("status", "improving");
            trend.put("message", "Your performance is improving! Keep it up!");
        } else if (change < -0.5) {
            trend.put("status", "declining");
            trend.put("message", "Performance seems to be declining. Consider taking a break or adjusting your approach.");
        } else {
            trend.put("status", "stable");
            trend.put("message", "Your performance is stable.");
        }
        
        trend.put("change", Math.round(change * 100.0) / 100.0);
        
        return trend;
    }
    
    private double calculateAveragePerformanceRating(List<GamingSession> sessions) {
        return sessions.stream()
                .filter(s -> s.getPerformanceRating() != null)
                .mapToInt(s -> s.getPerformanceRating().getValue())
                .average()
                .orElse(0.0);
    }
    
    private Map<String, Object> analyzeMoodPatterns(List<GamingSession> sessions) {
        Map<String, Object> moodAnalysis = new HashMap<>();
        
        List<GamingSession> sessionsWithMood = sessions.stream()
                .filter(s -> s.getMoodBefore() != null && s.getMoodAfter() != null)
                .collect(Collectors.toList());
        
        if (sessionsWithMood.isEmpty()) {
            moodAnalysis.put("message", "No mood data available");
            return moodAnalysis;
        }
        
        double avgMoodBefore = sessionsWithMood.stream()
                .mapToInt(s -> s.getMoodBefore().getValue())
                .average().orElse(0);
        
        double avgMoodAfter = sessionsWithMood.stream()
                .mapToInt(s -> s.getMoodAfter().getValue())
                .average().orElse(0);
        
        double moodImprovement = avgMoodAfter - avgMoodBefore;
        
        moodAnalysis.put("averageMoodBefore", Math.round(avgMoodBefore * 100.0) / 100.0);
        moodAnalysis.put("averageMoodAfter", Math.round(avgMoodAfter * 100.0) / 100.0);
        moodAnalysis.put("moodImprovement", Math.round(moodImprovement * 100.0) / 100.0);
        
        if (moodImprovement > 0.5) {
            moodAnalysis.put("impact", "This game generally improves your mood!");
        } else if (moodImprovement < -0.5) {
            moodAnalysis.put("impact", "This game might be causing frustration. Consider taking breaks.");
        } else {
            moodAnalysis.put("impact", "This game has a neutral impact on your mood.");
        }
        
        return moodAnalysis;
    }
    
    private Map<String, Object> analyzeSystemPerformance(List<GamingSession> sessions) {
        Map<String, Object> systemAnalysis = new HashMap<>();
        
        List<GamingSession> sessionsWithSystemData = sessions.stream()
                .filter(s -> s.getAvgFps() != null || s.getAvgCpuUsage() != null)
                .collect(Collectors.toList());
        
        if (sessionsWithSystemData.isEmpty()) {
            systemAnalysis.put("message", "No system performance data available");
            return systemAnalysis;
        }
        
        OptionalDouble avgFps = sessionsWithSystemData.stream()
                .filter(s -> s.getAvgFps() != null)
                .mapToDouble(GamingSession::getAvgFps)
                .average();
        
        OptionalDouble avgCpuUsage = sessionsWithSystemData.stream()
                .filter(s -> s.getAvgCpuUsage() != null)
                .mapToDouble(GamingSession::getAvgCpuUsage)
                .average();
        
        if (avgFps.isPresent()) {
            systemAnalysis.put("averageFps", Math.round(avgFps.getAsDouble() * 100.0) / 100.0);
        }
        
        if (avgCpuUsage.isPresent()) {
            systemAnalysis.put("averageCpuUsage", Math.round(avgCpuUsage.getAsDouble() * 100.0) / 100.0);
        }
        
        return systemAnalysis;
    }
    
    private Map<String, Object> analyzeGamingPatterns(List<GamingSession> sessions) {
        Map<String, Object> patterns = new HashMap<>();
        
        // Analyze gaming times
        Map<Integer, Long> hourlyDistribution = sessions.stream()
                .collect(Collectors.groupingBy(
                    s -> s.getStartTime().getHour(),
                    Collectors.counting()
                ));
        
        Optional<Map.Entry<Integer, Long>> peakHour = hourlyDistribution.entrySet().stream()
                .max(Map.Entry.comparingByValue());
        
        if (peakHour.isPresent()) {
            patterns.put("peakGamingHour", peakHour.get().getKey() + ":00");
        }
        
        // Session frequency
        LocalDateTime weekAgo = LocalDateTime.now().minus(7, ChronoUnit.DAYS);
        long recentSessions = sessions.stream()
                .filter(s -> s.getStartTime().isAfter(weekAgo))
                .count();
        
        patterns.put("sessionsThisWeek", recentSessions);
        patterns.put("averageSessionsPerDay", Math.round((recentSessions / 7.0) * 100.0) / 100.0);
        
        return patterns;
    }
    
    private List<String> generateRecommendations(List<GamingSession> sessions) {
        List<String> recommendations = new ArrayList<>();
        
        // Analyze recent performance
        List<GamingSession> recent = sessions.stream()
                .filter(s -> s.getStartTime().isAfter(LocalDateTime.now().minus(7, ChronoUnit.DAYS)))
                .collect(Collectors.toList());
        
        if (recent.isEmpty()) {
            recommendations.add("Start tracking your gaming sessions to get personalized recommendations!");
            return recommendations;
        }
        
        // Check for long sessions
        double avgDuration = recent.stream()
                .filter(s -> s.getDurationMinutes() != null)
                .mapToLong(GamingSession::getDurationMinutes)
                .average().orElse(0);
        
        if (avgDuration > 180) { // 3 hours
            recommendations.add("Consider taking breaks during long gaming sessions to maintain peak performance.");
        }
        
        // Check mood patterns
        List<GamingSession> moodSessions = recent.stream()
                .filter(s -> s.getMoodBefore() != null && s.getMoodAfter() != null)
                .collect(Collectors.toList());
        
        if (!moodSessions.isEmpty()) {
            double avgMoodChange = moodSessions.stream()
                    .mapToDouble(s -> s.getMoodAfter().getValue() - s.getMoodBefore().getValue())
                    .average().orElse(0);
            
            if (avgMoodChange < -0.5) {
                recommendations.add("Gaming sessions seem to be affecting your mood negatively. Consider shorter sessions or different games.");
            }
        }
        
        // Check system performance
        List<GamingSession> systemSessions = recent.stream()
                .filter(s -> s.getAvgFps() != null)
                .collect(Collectors.toList());
        
        if (!systemSessions.isEmpty()) {
            double avgFps = systemSessions.stream()
                    .mapToDouble(GamingSession::getAvgFps)
                    .average().orElse(0);
            
            if (avgFps < 30) {
                recommendations.add("Low FPS detected. Consider lowering graphics settings or upgrading hardware.");
            } else if (avgFps > 144) {
                recommendations.add("Excellent performance! Your system is running smoothly.");
            }
        }
        
        if (recommendations.isEmpty()) {
            recommendations.add("Keep tracking your sessions for more personalized insights!");
        }
        
        return recommendations;
    }
    
    public Map<String, Object> getOverallStats() {
        Map<String, Object> stats = new HashMap<>();
        
        LocalDateTime weekAgo = LocalDateTime.now().minus(7, ChronoUnit.DAYS);
        LocalDateTime monthAgo = LocalDateTime.now().minus(30, ChronoUnit.DAYS);
        
        Long sessionsThisWeek = sessionRepository.countSessionsSince(weekAgo);
        Long sessionsThisMonth = sessionRepository.countSessionsSince(monthAgo);
        Long playtimeThisWeek = sessionRepository.getTotalPlaytimeSince(weekAgo);
        Long playtimeThisMonth = sessionRepository.getTotalPlaytimeSince(monthAgo);
        
        stats.put("sessionsThisWeek", sessionsThisWeek != null ? sessionsThisWeek : 0);
        stats.put("sessionsThisMonth", sessionsThisMonth != null ? sessionsThisMonth : 0);
        stats.put("playtimeThisWeekHours", playtimeThisWeek != null ? Math.round((playtimeThisWeek / 60.0) * 100.0) / 100.0 : 0);
        stats.put("playtimeThisMonthHours", playtimeThisMonth != null ? Math.round((playtimeThisMonth / 60.0) * 100.0) / 100.0 : 0);
        
        return stats;
    }
}
