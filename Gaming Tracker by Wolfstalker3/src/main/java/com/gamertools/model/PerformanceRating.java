package com.gamertools.model;

public enum PerformanceRating {
    TERRIBLE("Terrible", 1),
    POOR("Poor", 2),
    AVERAGE("Average", 3),
    GOOD("Good", 4),
    EXCELLENT("Excellent", 5);
    
    private final String displayName;
    private final int value;
    
    PerformanceRating(String displayName, int value) {
        this.displayName = displayName;
        this.value = value;
    }
    
    public String getDisplayName() {
        return displayName;
    }
    
    public int getValue() {
        return value;
    }
    
    public static PerformanceRating fromValue(int value) {
        for (PerformanceRating rating : values()) {
            if (rating.value == value) {
                return rating;
            }
        }
        return AVERAGE;
    }
}
