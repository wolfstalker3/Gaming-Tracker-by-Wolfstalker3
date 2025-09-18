package com.gamertools.repository;

import com.gamertools.model.GamingSession;
import com.gamertools.model.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface GamingSessionRepository extends JpaRepository<GamingSession, Long> {
    
    List<GamingSession> findByGameOrderByStartTimeDesc(Game game);
    
    Optional<GamingSession> findByGameAndEndTimeIsNull(Game game);
    
    @Query("SELECT s FROM GamingSession s WHERE s.endTime IS NULL")
    List<GamingSession> findActiveSessions();
    
    @Query("SELECT s FROM GamingSession s WHERE s.startTime >= :startDate AND s.startTime <= :endDate ORDER BY s.startTime DESC")
    List<GamingSession> findSessionsBetweenDates(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);
    
    @Query("SELECT AVG(s.durationMinutes) FROM GamingSession s WHERE s.game = :game AND s.durationMinutes IS NOT NULL")
    Double findAverageSessionDurationByGame(@Param("game") Game game);
    
    @Query("SELECT AVG(s.avgFps) FROM GamingSession s WHERE s.game = :game AND s.avgFps IS NOT NULL")
    Double findAverageFpsByGame(@Param("game") Game game);
    
    @Query("SELECT COUNT(s) FROM GamingSession s WHERE s.startTime >= :date")
    Long countSessionsSince(@Param("date") LocalDateTime date);
    
    @Query("SELECT SUM(s.durationMinutes) FROM GamingSession s WHERE s.startTime >= :date AND s.durationMinutes IS NOT NULL")
    Long getTotalPlaytimeSince(@Param("date") LocalDateTime date);
    
    @Query("SELECT s FROM GamingSession s WHERE s.game = :game AND s.performanceRating IS NOT NULL ORDER BY s.startTime DESC")
    List<GamingSession> findSessionsWithPerformanceRating(@Param("game") Game game);
}
