package com.gamertools.repository;

import com.gamertools.model.GamingSchedule;
import com.gamertools.model.ScheduleType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.time.DayOfWeek;
import java.util.List;

@Repository
public interface GamingScheduleRepository extends JpaRepository<GamingSchedule, Long> {
    
    List<GamingSchedule> findByIsCompletedFalseOrderByScheduledTimeAsc();
    
    List<GamingSchedule> findByTypeAndIsCompletedFalse(ScheduleType type);
    
    @Query("SELECT s FROM GamingSchedule s WHERE s.scheduledTime >= :startTime AND s.scheduledTime <= :endTime ORDER BY s.scheduledTime")
    List<GamingSchedule> findSchedulesBetween(@Param("startTime") LocalDateTime startTime, @Param("endTime") LocalDateTime endTime);
    
    @Query("SELECT s FROM GamingSchedule s WHERE s.scheduledTime <= :currentTime AND s.isCompleted = false AND s.reminderSent = false")
    List<GamingSchedule> findOverdueSchedules(@Param("currentTime") LocalDateTime currentTime);
    
    @Query("SELECT s FROM GamingSchedule s WHERE s.scheduledTime BETWEEN :now AND :reminderTime AND s.reminderSent = false AND s.isCompleted = false")
    List<GamingSchedule> findSchedulesNeedingReminder(@Param("now") LocalDateTime now, @Param("reminderTime") LocalDateTime reminderTime);
    
    List<GamingSchedule> findByDayOfWeekAndType(DayOfWeek dayOfWeek, ScheduleType type);
    
    @Query("SELECT COUNT(s) FROM GamingSchedule s WHERE s.isCompleted = true AND s.scheduledTime >= :startDate")
    Long countCompletedSchedulesSince(@Param("startDate") LocalDateTime startDate);
}
