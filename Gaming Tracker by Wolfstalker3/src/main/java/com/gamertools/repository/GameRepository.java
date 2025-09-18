package com.gamertools.repository;

import com.gamertools.model.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface GameRepository extends JpaRepository<Game, Long> {
    
    Optional<Game> findByNameIgnoreCase(String name);
    
    List<Game> findByGenreIgnoreCase(String genre);
    
    List<Game> findByPlatformIgnoreCase(String platform);
    
    @Query("SELECT DISTINCT g.genre FROM Game g ORDER BY g.genre")
    List<String> findAllGenres();
    
    @Query("SELECT DISTINCT g.platform FROM Game g WHERE g.platform IS NOT NULL ORDER BY g.platform")
    List<String> findAllPlatforms();
    
    @Query("SELECT g FROM Game g JOIN g.sessions s GROUP BY g ORDER BY COUNT(s) DESC")
    List<Game> findMostPlayedGames();
}
