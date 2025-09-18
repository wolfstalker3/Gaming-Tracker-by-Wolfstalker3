package com.gamertools.service;

import org.springframework.stereotype.Service;
import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;
import oshi.hardware.GlobalMemory;
import oshi.hardware.HardwareAbstractionLayer;
import oshi.hardware.Sensors;
import oshi.software.os.OperatingSystem;

import java.util.HashMap;
import java.util.Map;

@Service
public class SystemMonitoringService {
    
    private final SystemInfo systemInfo;
    private final HardwareAbstractionLayer hardware;
    private final OperatingSystem os;
    
    public SystemMonitoringService() {
        this.systemInfo = new SystemInfo();
        this.hardware = systemInfo.getHardware();
        this.os = systemInfo.getOperatingSystem();
    }
    
    public Map<String, Object> getCurrentSystemMetrics() {
        Map<String, Object> metrics = new HashMap<>();
        
        try {
            // CPU Information
            CentralProcessor processor = hardware.getProcessor();
            double cpuUsage = processor.getSystemCpuLoadBetweenTicks(processor.getSystemCpuLoadTicks()) * 100;
            
            // Memory Information
            GlobalMemory memory = hardware.getMemory();
            long totalMemory = memory.getTotal();
            long availableMemory = memory.getAvailable();
            double memoryUsage = ((double) (totalMemory - availableMemory) / totalMemory) * 100;
            
            // Temperature (if available)
            Sensors sensors = hardware.getSensors();
            double cpuTemp = sensors.getCpuTemperature();
            
            metrics.put("cpuUsage", Math.round(cpuUsage * 100.0) / 100.0);
            metrics.put("memoryUsage", Math.round(memoryUsage * 100.0) / 100.0);
            metrics.put("totalMemoryGB", Math.round((totalMemory / 1024.0 / 1024.0 / 1024.0) * 100.0) / 100.0);
            metrics.put("availableMemoryGB", Math.round((availableMemory / 1024.0 / 1024.0 / 1024.0) * 100.0) / 100.0);
            metrics.put("cpuTemperature", cpuTemp > 0 ? Math.round(cpuTemp * 100.0) / 100.0 : null);
            metrics.put("cpuName", processor.getProcessorIdentifier().getName());
            metrics.put("cpuCores", processor.getLogicalProcessorCount());
            
        } catch (Exception e) {
            metrics.put("error", "Failed to retrieve system metrics: " + e.getMessage());
        }
        
        return metrics;
    }
    
    public Map<String, Object> getSystemInfo() {
        Map<String, Object> info = new HashMap<>();
        
        try {
            CentralProcessor processor = hardware.getProcessor();
            GlobalMemory memory = hardware.getMemory();
            
            info.put("osName", os.getFamily());
            info.put("osVersion", os.getVersionInfo().toString());
            info.put("cpuName", processor.getProcessorIdentifier().getName());
            info.put("cpuCores", processor.getLogicalProcessorCount());
            info.put("totalMemoryGB", Math.round((memory.getTotal() / 1024.0 / 1024.0 / 1024.0) * 100.0) / 100.0);
            
        } catch (Exception e) {
            info.put("error", "Failed to retrieve system info: " + e.getMessage());
        }
        
        return info;
    }
    
    public Map<String, String> getOptimizationRecommendations(Map<String, Object> metrics) {
        Map<String, String> recommendations = new HashMap<>();
        
        try {
            Double cpuUsage = (Double) metrics.get("cpuUsage");
            Double memoryUsage = (Double) metrics.get("memoryUsage");
            Double cpuTemp = (Double) metrics.get("cpuTemperature");
            
            if (cpuUsage != null && cpuUsage > 80) {
                recommendations.put("cpu", "High CPU usage detected (" + cpuUsage + "%). Close unnecessary applications and consider lowering game settings.");
            }
            
            if (memoryUsage != null && memoryUsage > 85) {
                recommendations.put("memory", "High memory usage detected (" + memoryUsage + "%). Close background applications or consider adding more RAM.");
            }
            
            if (cpuTemp != null && cpuTemp > 80) {
                recommendations.put("temperature", "High CPU temperature detected (" + cpuTemp + "°C). Check cooling system and clean dust from fans.");
            }
            
            if (recommendations.isEmpty()) {
                recommendations.put("general", "System performance looks good! Your setup is optimized for gaming.");
            }
            
        } catch (Exception e) {
            recommendations.put("error", "Unable to generate recommendations: " + e.getMessage());
        }
        
        return recommendations;
    }
}
