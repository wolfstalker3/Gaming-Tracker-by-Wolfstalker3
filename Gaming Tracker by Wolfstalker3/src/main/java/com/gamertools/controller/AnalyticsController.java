package com.gamertools.controller;

import com.gamertools.model.Game;
import com.gamertools.service.GameService;
import com.gamertools.service.PerformanceAnalyticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/analytics")
@CrossOrigin(origins = "*")
public class AnalyticsController {
    
    @Autowired
    private PerformanceAnalyticsService analyticsService;
    
    @Autowired
    private GameService gameService;
    
    @GetMapping("/game/{gameId}")
    public ResponseEntity<?> getGameAnalytics(@PathVariable Long gameId) {
        return gameService.getGameById(gameId)
                .map(game -> {
                    Map<String, Object> analytics = analyticsService.getPerformanceAnalytics(game);
                    return ResponseEntity.ok(analytics);
                })
                .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/overall")
    public ResponseEntity<Map<String, Object>> getOverallAnalytics() {
        Map<String, Object> analytics = analyticsService.getOverallStats();
        return ResponseEntity.ok(analytics);
    }
}
