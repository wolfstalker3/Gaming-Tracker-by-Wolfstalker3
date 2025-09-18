package com.gamertools.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "games")
public class Game {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "Game name is required")
    @Column(nullable = false)
    private String name;
    
    @NotBlank(message = "Genre is required")
    private String genre;
    
    private String platform;
    
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    
    @OneToMany(mappedBy = "game", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<GamingSession> sessions;
    
    // Constructors
    public Game() {
        this.createdAt = LocalDateTime.now();
    }
    
    public Game(String name, String genre, String platform) {
        this();
        this.name = name;
        this.genre = genre;
        this.platform = platform;
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getGenre() {
        return genre;
    }
    
    public void setGenre(String genre) {
        this.genre = genre;
    }
    
    public String getPlatform() {
        return platform;
    }
    
    public void setPlatform(String platform) {
        this.platform = platform;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
    public List<GamingSession> getSessions() {
        return sessions;
    }
    
    public void setSessions(List<GamingSession> sessions) {
        this.sessions = sessions;
    }
}
