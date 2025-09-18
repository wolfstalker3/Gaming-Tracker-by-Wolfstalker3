package com.gamertools.service;

import com.gamertools.model.Game;
import com.gamertools.repository.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GameService {
    
    @Autowired
    private GameRepository gameRepository;
    
    public List<Game> getAllGames() {
        return gameRepository.findAll();
    }
    
    public Optional<Game> getGameById(Long id) {
        return gameRepository.findById(id);
    }
    
    public Game createGame(Game game) {
        // Check if game with same name already exists
        Optional<Game> existingGame = gameRepository.findByNameIgnoreCase(game.getName());
        if (existingGame.isPresent()) {
            throw new RuntimeException("Game with name '" + game.getName() + "' already exists");
        }
        
        return gameRepository.save(game);
    }
    
    public Game updateGame(Long id, Game updatedGame) {
        return gameRepository.findById(id)
                .map(game -> {
                    game.setName(updatedGame.getName());
                    game.setGenre(updatedGame.getGenre());
                    game.setPlatform(updatedGame.getPlatform());
                    return gameRepository.save(game);
                })
                .orElseThrow(() -> new RuntimeException("Game not found with id: " + id));
    }
    
    public void deleteGame(Long id) {
        gameRepository.deleteById(id);
    }
    
    public List<Game> getGamesByGenre(String genre) {
        return gameRepository.findByGenreIgnoreCase(genre);
    }
    
    public List<Game> getGamesByPlatform(String platform) {
        return gameRepository.findByPlatformIgnoreCase(platform);
    }
    
    public List<String> getAllGenres() {
        return gameRepository.findAllGenres();
    }
    
    public List<String> getAllPlatforms() {
        return gameRepository.findAllPlatforms();
    }
    
    public List<Game> getMostPlayedGames() {
        return gameRepository.findMostPlayedGames();
    }
    
    public Optional<Game> findGameByName(String name) {
        return gameRepository.findByNameIgnoreCase(name);
    }
}
