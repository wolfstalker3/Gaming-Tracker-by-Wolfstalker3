package com.gamertools.controller;

import com.gamertools.service.SystemMonitoringService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/system")
@CrossOrigin(origins = "*")
public class SystemController {
    
    @Autowired
    private SystemMonitoringService systemMonitoringService;
    
    @GetMapping("/metrics")
    public ResponseEntity<Map<String, Object>> getCurrentSystemMetrics() {
        Map<String, Object> metrics = systemMonitoringService.getCurrentSystemMetrics();
        return ResponseEntity.ok(metrics);
    }
    
    @GetMapping("/info")
    public ResponseEntity<Map<String, Object>> getSystemInfo() {
        Map<String, Object> info = systemMonitoringService.getSystemInfo();
        return ResponseEntity.ok(info);
    }
    
    @GetMapping("/recommendations")
    public ResponseEntity<Map<String, String>> getOptimizationRecommendations() {
        Map<String, Object> metrics = systemMonitoringService.getCurrentSystemMetrics();
        Map<String, String> recommendations = systemMonitoringService.getOptimizationRecommendations(metrics);
        return ResponseEntity.ok(recommendations);
    }
}
