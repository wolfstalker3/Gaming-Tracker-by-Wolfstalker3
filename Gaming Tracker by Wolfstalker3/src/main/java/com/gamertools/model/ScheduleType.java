package com.gamertools.model;

public enum ScheduleType {
    ONE_TIME("One Time"),
    DAILY("Daily"),
    WEEKLY("Weekly"),
    TOURNAMENT("Tournament"),
    RAID("Raid/Group Event"),
    PRACTICE("Practice Session");
    
    private final String displayName;
    
    ScheduleType(String displayName) {
        this.displayName = displayName;
    }
    
    public String getDisplayName() {
        return displayName;
    }
}
