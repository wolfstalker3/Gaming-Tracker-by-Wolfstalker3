package com.gamertools.controller;

import com.gamertools.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class WebController {
    
    @Autowired
    private GameService gameService;
    
    @Autowired
    private GamingSessionService sessionService;
    
    @Autowired
    private GamingScheduleService scheduleService;
    
    @Autowired
    private SystemMonitoringService systemMonitoringService;
    
    @Autowired
    private PerformanceAnalyticsService analyticsService;
    
    @GetMapping("/")
    public String dashboard(Model model) {
        model.addAttribute("games", gameService.getAllGames());
        model.addAttribute("activeSessions", sessionService.getActiveSessions());
        model.addAttribute("todaySchedules", scheduleService.getSchedulesForToday());
        model.addAttribute("systemMetrics", systemMonitoringService.getCurrentSystemMetrics());
        model.addAttribute("overallStats", analyticsService.getOverallStats());
        return "dashboard";
    }
    
    @GetMapping("/games")
    public String games(Model model) {
        model.addAttribute("games", gameService.getAllGames());
        model.addAttribute("genres", gameService.getAllGenres());
        model.addAttribute("platforms", gameService.getAllPlatforms());
        return "games";
    }
    
    @GetMapping("/sessions")
    public String sessions(Model model) {
        model.addAttribute("recentSessions", sessionService.getRecentSessions(30));
        model.addAttribute("activeSessions", sessionService.getActiveSessions());
        model.addAttribute("games", gameService.getAllGames());
        return "sessions";
    }
    
    @GetMapping("/schedule")
    public String schedule(Model model) {
        model.addAttribute("upcomingSchedules", scheduleService.getAllUpcomingSchedules());
        model.addAttribute("todaySchedules", scheduleService.getSchedulesForToday());
        model.addAttribute("weekSchedules", scheduleService.getSchedulesForWeek());
        model.addAttribute("games", gameService.getAllGames());
        return "schedule";
    }
    
    @GetMapping("/analytics")
    public String analytics(Model model) {
        model.addAttribute("games", gameService.getAllGames());
        model.addAttribute("overallStats", analyticsService.getOverallStats());
        model.addAttribute("scheduleAnalytics", scheduleService.getScheduleAnalytics());
        return "analytics";
    }
    
    @GetMapping("/analytics/game/{gameId}")
    public String gameAnalytics(@PathVariable Long gameId, Model model) {
        return gameService.getGameById(gameId)
                .map(game -> {
                    model.addAttribute("game", game);
                    model.addAttribute("analytics", analyticsService.getPerformanceAnalytics(game));
                    model.addAttribute("sessions", sessionService.getSessionsForGame(gameId));
                    return "game-analytics";
                })
                .orElse("redirect:/analytics");
    }
    
    @GetMapping("/system")
    public String system(Model model) {
        var metrics = systemMonitoringService.getCurrentSystemMetrics();
        model.addAttribute("systemMetrics", metrics);
        model.addAttribute("systemInfo", systemMonitoringService.getSystemInfo());
        model.addAttribute("recommendations", systemMonitoringService.getOptimizationRecommendations(metrics));
        return "system";
    }
}
