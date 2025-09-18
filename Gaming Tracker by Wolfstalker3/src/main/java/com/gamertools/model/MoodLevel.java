package com.gamertools.model;

public enum MoodLevel {
    VERY_FRUSTRATED("Very Frustrated", 1),
    FRUSTRATED("Frustrated", 2),
    NEUTRAL("Neutral", 3),
    GOOD("Good", 4),
    EXCELLENT("Excellent", 5);
    
    private final String displayName;
    private final int value;
    
    MoodLevel(String displayName, int value) {
        this.displayName = displayName;
        this.value = value;
    }
    
    public String getDisplayName() {
        return displayName;
    }
    
    public int getValue() {
        return value;
    }
    
    public static MoodLevel fromValue(int value) {
        for (MoodLevel mood : values()) {
            if (mood.value == value) {
                return mood;
            }
        }
        return NEUTRAL;
    }
}
