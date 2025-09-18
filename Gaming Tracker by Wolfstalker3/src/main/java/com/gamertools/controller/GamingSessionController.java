package com.gamertools.controller;

import com.gamertools.model.GamingSession;
import com.gamertools.service.GamingSessionService;
import com.gamertools.service.PerformanceAnalyticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/sessions")
@CrossOrigin(origins = "*")
public class GamingSessionController {
    
    @Autowired
    private GamingSessionService sessionService;
    
    @Autowired
    private PerformanceAnalyticsService analyticsService;
    
    @PostMapping("/start/{gameId}")
    public ResponseEntity<?> startSession(@PathVariable Long gameId) {
        try {
            GamingSession session = sessionService.startSession(gameId);
            return ResponseEntity.status(HttpStatus.CREATED).body(session);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("{\"error\": \"" + e.getMessage() + "\"}");
        }
    }
    
    @PostMapping("/end/{sessionId}")
    public ResponseEntity<?> endSession(@PathVariable Long sessionId) {
        try {
            GamingSession session = sessionService.endSession(sessionId);
            return ResponseEntity.ok(session);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("{\"error\": \"" + e.getMessage() + "\"}");
        }
    }
    
    @PutMapping("/{sessionId}")
    public ResponseEntity<?> updateSession(@PathVariable Long sessionId, @RequestBody GamingSession session) {
        try {
            GamingSession updatedSession = sessionService.updateSessionStats(sessionId, session);
            return ResponseEntity.ok(updatedSession);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("{\"error\": \"" + e.getMessage() + "\"}");
        }
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<GamingSession> getSessionById(@PathVariable Long id) {
        return sessionService.getSessionById(id)
                .map(session -> ResponseEntity.ok(session))
                .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/game/{gameId}")
    public ResponseEntity<List<GamingSession>> getSessionsForGame(@PathVariable Long gameId) {
        try {
            List<GamingSession> sessions = sessionService.getSessionsForGame(gameId);
            return ResponseEntity.ok(sessions);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @GetMapping("/active")
    public ResponseEntity<List<GamingSession>> getActiveSessions() {
        List<GamingSession> activeSessions = sessionService.getActiveSessions();
        return ResponseEntity.ok(activeSessions);
    }
    
    @GetMapping("/recent/{days}")
    public ResponseEntity<List<GamingSession>> getRecentSessions(@PathVariable int days) {
        List<GamingSession> sessions = sessionService.getRecentSessions(days);
        return ResponseEntity.ok(sessions);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSession(@PathVariable Long id) {
        sessionService.deleteSession(id);
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping("/analytics/overall")
    public ResponseEntity<Map<String, Object>> getOverallAnalytics() {
        Map<String, Object> analytics = analyticsService.getOverallStats();
        return ResponseEntity.ok(analytics);
    }
}
