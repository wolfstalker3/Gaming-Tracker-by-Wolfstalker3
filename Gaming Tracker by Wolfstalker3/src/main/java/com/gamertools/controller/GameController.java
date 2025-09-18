package com.gamertools.controller;

import com.gamertools.model.Game;
import com.gamertools.service.GameService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/games")
@CrossOrigin(origins = "*")
public class GameController {
    
    @Autowired
    private GameService gameService;
    
    @GetMapping
    public ResponseEntity<List<Game>> getAllGames() {
        List<Game> games = gameService.getAllGames();
        return ResponseEntity.ok(games);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Game> getGameById(@PathVariable Long id) {
        return gameService.getGameById(id)
                .map(game -> ResponseEntity.ok(game))
                .orElse(ResponseEntity.notFound().build());
    }
    
    @PostMapping
    public ResponseEntity<?> createGame(@Valid @RequestBody Game game) {
        try {
            Game createdGame = gameService.createGame(game);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdGame);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("{\"error\": \"" + e.getMessage() + "\"}");
        }
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<?> updateGame(@PathVariable Long id, @Valid @RequestBody Game game) {
        try {
            Game updatedGame = gameService.updateGame(id, game);
            return ResponseEntity.ok(updatedGame);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("{\"error\": \"" + e.getMessage() + "\"}");
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGame(@PathVariable Long id) {
        gameService.deleteGame(id);
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping("/genre/{genre}")
    public ResponseEntity<List<Game>> getGamesByGenre(@PathVariable String genre) {
        List<Game> games = gameService.getGamesByGenre(genre);
        return ResponseEntity.ok(games);
    }
    
    @GetMapping("/platform/{platform}")
    public ResponseEntity<List<Game>> getGamesByPlatform(@PathVariable String platform) {
        List<Game> games = gameService.getGamesByPlatform(platform);
        return ResponseEntity.ok(games);
    }
    
    @GetMapping("/genres")
    public ResponseEntity<List<String>> getAllGenres() {
        List<String> genres = gameService.getAllGenres();
        return ResponseEntity.ok(genres);
    }
    
    @GetMapping("/platforms")
    public ResponseEntity<List<String>> getAllPlatforms() {
        List<String> platforms = gameService.getAllPlatforms();
        return ResponseEntity.ok(platforms);
    }
    
    @GetMapping("/most-played")
    public ResponseEntity<List<Game>> getMostPlayedGames() {
        List<Game> games = gameService.getMostPlayedGames();
        return ResponseEntity.ok(games);
    }
}
