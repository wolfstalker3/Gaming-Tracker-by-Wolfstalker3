package com.gamertools.service;

import com.gamertools.model.Game;
import com.gamertools.model.GamingSession;
import com.gamertools.repository.GameRepository;
import com.gamertools.repository.GamingSessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class GamingSessionService {
    
    @Autowired
    private GamingSessionRepository sessionRepository;
    
    @Autowired
    private GameRepository gameRepository;
    
    @Autowired
    private SystemMonitoringService systemMonitoringService;
    
    public GamingSession startSession(Long gameId) {
        Game game = gameRepository.findById(gameId)
                .orElseThrow(() -> new RuntimeException("Game not found with id: " + gameId));
        
        // Check if there's already an active session for this game
        Optional<GamingSession> existingSession = sessionRepository.findByGameAndEndTimeIsNull(game);
        if (existingSession.isPresent()) {
            throw new RuntimeException("There's already an active session for this game");
        }
        
        GamingSession session = new GamingSession(game);
        
        // Capture initial system metrics
        Map<String, Object> systemMetrics = systemMonitoringService.getCurrentSystemMetrics();
        if (systemMetrics.get("cpuUsage") != null) {
            session.setAvgCpuUsage((Double) systemMetrics.get("cpuUsage"));
        }
        if (systemMetrics.get("memoryUsage") != null) {
            session.setAvgMemoryUsage((Double) systemMetrics.get("memoryUsage"));
        }
        if (systemMetrics.get("cpuTemperature") != null) {
            session.setAvgTemperature((Double) systemMetrics.get("cpuTemperature"));
        }
        
        return sessionRepository.save(session);
    }
    
    public GamingSession endSession(Long sessionId) {
        GamingSession session = sessionRepository.findById(sessionId)
                .orElseThrow(() -> new RuntimeException("Session not found with id: " + sessionId));
        
        if (!session.isActive()) {
            throw new RuntimeException("Session is already ended");
        }
        
        session.endSession();
        return sessionRepository.save(session);
    }
    
    public GamingSession updateSessionStats(Long sessionId, GamingSession updatedSession) {
        return sessionRepository.findById(sessionId)
                .map(session -> {
                    if (updatedSession.getKills() != null) {
                        session.setKills(updatedSession.getKills());
                    }
                    if (updatedSession.getDeaths() != null) {
                        session.setDeaths(updatedSession.getDeaths());
                    }
                    if (updatedSession.getAssists() != null) {
                        session.setAssists(updatedSession.getAssists());
                    }
                    if (updatedSession.getAccuracy() != null) {
                        session.setAccuracy(updatedSession.getAccuracy());
                    }
                    if (updatedSession.getScore() != null) {
                        session.setScore(updatedSession.getScore());
                    }
                    if (updatedSession.getAvgFps() != null) {
                        session.setAvgFps(updatedSession.getAvgFps());
                    }
                    if (updatedSession.getMoodBefore() != null) {
                        session.setMoodBefore(updatedSession.getMoodBefore());
                    }
                    if (updatedSession.getMoodAfter() != null) {
                        session.setMoodAfter(updatedSession.getMoodAfter());
                    }
                    if (updatedSession.getPerformanceRating() != null) {
                        session.setPerformanceRating(updatedSession.getPerformanceRating());
                    }
                    if (updatedSession.getNotes() != null) {
                        session.setNotes(updatedSession.getNotes());
                    }
                    
                    return sessionRepository.save(session);
                })
                .orElseThrow(() -> new RuntimeException("Session not found with id: " + sessionId));
    }
    
    public List<GamingSession> getSessionsForGame(Long gameId) {
        Game game = gameRepository.findById(gameId)
                .orElseThrow(() -> new RuntimeException("Game not found with id: " + gameId));
        
        return sessionRepository.findByGameOrderByStartTimeDesc(game);
    }
    
    public List<GamingSession> getActiveSessions() {
        return sessionRepository.findActiveSessions();
    }
    
    public List<GamingSession> getRecentSessions(int days) {
        LocalDateTime startDate = LocalDateTime.now().minusDays(days);
        LocalDateTime endDate = LocalDateTime.now();
        
        return sessionRepository.findSessionsBetweenDates(startDate, endDate);
    }
    
    public Optional<GamingSession> getSessionById(Long id) {
        return sessionRepository.findById(id);
    }
    
    public void deleteSession(Long id) {
        sessionRepository.deleteById(id);
    }
}
