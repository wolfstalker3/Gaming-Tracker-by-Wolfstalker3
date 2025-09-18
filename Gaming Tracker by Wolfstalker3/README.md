# Gaming Session Optimizer 🎮

A comprehensive Spring Boot application designed to help gamers track their performance, optimize their gaming setup, and manage their gaming schedules. This unique tool solves common but often overlooked problems that gamers face.

## 🚀 Features

### 1. **Gaming Session Tracking**
- Track gaming sessions with detailed performance metrics
- Record kills, deaths, assists, accuracy, and scores
- Monitor system performance during gameplay (FPS, CPU usage, memory usage, temperature)
- Track mood before and after gaming sessions
- Rate your performance and add notes

### 2. **Performance Analytics**
- Detailed performance analysis for each game
- Trend analysis to see if you're improving or declining
- Mood pattern analysis to understand how games affect you
- System performance correlation with gaming performance
- Personalized recommendations based on your data

### 3. **Gaming Schedule Management**
- Schedule gaming sessions with conflict detection
- Support for one-time, daily, weekly, tournament, and raid schedules
- Automatic reminders for upcoming sessions
- Schedule analytics and recommendations
- Track completion rates

### 4. **System Optimization**
- Real-time system monitoring (CPU, memory, temperature)
- Performance recommendations based on current system state
- Gaming-specific optimization tips
- System information display
- Auto-refreshing metrics

### 5. **Beautiful Web Interface**
- Modern, responsive design with glassmorphism effects
- Real-time dashboard with key metrics
- Interactive charts and analytics
- Mobile-friendly interface
- Dark theme optimized for gamers

## 🛠️ Technology Stack

- **Backend**: Spring Boot 3.1.5, Java 17
- **Database**: H2 (in-memory for development)
- **Frontend**: Thymeleaf, Bootstrap 5, Chart.js
- **System Monitoring**: OSHI (Operating System and Hardware Information)
- **Build Tool**: Maven

## 📋 Prerequisites

- Java 17 or higher
- Maven 3.6 or higher
- Any modern web browser

## 🚀 Quick Start

1. **Clone the repository**
   ```bash
   git clone <repository-url>
   cd gaming-session-optimizer
   ```

2. **Build the application**
   ```bash
   mvn clean install
   ```

3. **Run the application**
   ```bash
   mvn spring-boot:run
   ```

4. **Open your browser**
   Navigate to `http://localhost:8080`

## 🎯 How to Use

### Getting Started
1. **Add Your Games**: Go to the Games section and add your favorite games
2. **Start a Session**: Begin tracking a gaming session for any game
3. **Monitor System**: Check the System page for real-time performance metrics
4. **Schedule Games**: Plan your gaming time with the Schedule feature
5. **Analyze Performance**: View detailed analytics for your gaming patterns

### Key Workflows

#### Starting a Gaming Session
1. Navigate to Games or Dashboard
2. Click "Start Session" for your desired game
3. The system will automatically capture initial system metrics
4. Game away! The session tracks your start time and system performance

#### Ending and Rating a Session
1. Go to Sessions page
2. Find your active session
3. Click "End Session"
4. Add performance stats (kills, deaths, accuracy, etc.)
5. Rate your performance and mood
6. Add any notes about the session

#### Viewing Analytics
1. Go to Analytics page
2. Select a specific game for detailed analysis
3. View performance trends, mood patterns, and system correlations
4. Get personalized recommendations

#### Scheduling Gaming Time
1. Navigate to Schedule
2. Create new gaming sessions with specific times
3. Set duration estimates and add descriptions
4. The system will detect conflicts and send reminders

## 🔧 Configuration

### Database Configuration
The application uses H2 in-memory database by default. To use a persistent database, update `application.yml`:

```yaml
spring:
  datasource:
    url: jdbc:h2:file:./data/gamingdb
    # Or use MySQL/PostgreSQL
```

### System Monitoring
System monitoring works out of the box but may require additional permissions on some systems for temperature readings.

## 🌟 Unique Problem-Solving Features

### 1. **Performance Correlation Analysis**
- Correlates your gaming performance with system metrics
- Identifies if low FPS or high CPU usage affects your gameplay
- Recommends optimal system conditions for peak performance

### 2. **Mood-Performance Tracking**
- Tracks how your mood changes during gaming
- Identifies games that consistently improve or worsen your mood
- Helps you make informed decisions about when to play certain games

### 3. **Gaming Schedule Optimization**
- Prevents over-scheduling and gaming burnout
- Detects conflicts between gaming sessions
- Provides balance recommendations for healthy gaming habits

### 4. **System-Aware Gaming**
- Real-time system monitoring during gameplay
- Proactive optimization recommendations
- Temperature monitoring to prevent overheating

### 5. **Data-Driven Insights**
- Identifies your peak gaming hours
- Analyzes session length patterns
- Provides actionable recommendations for improvement

## 📊 API Endpoints

### Games API
- `GET /api/games` - Get all games
- `POST /api/games` - Add new game
- `GET /api/games/{id}` - Get specific game
- `PUT /api/games/{id}` - Update game
- `DELETE /api/games/{id}` - Delete game

### Sessions API
- `POST /api/sessions/start/{gameId}` - Start new session
- `POST /api/sessions/end/{sessionId}` - End session
- `PUT /api/sessions/{sessionId}` - Update session stats
- `GET /api/sessions/active` - Get active sessions
- `GET /api/sessions/recent/{days}` - Get recent sessions

### Schedule API
- `GET /api/schedules` - Get upcoming schedules
- `POST /api/schedules` - Create new schedule
- `PUT /api/schedules/{id}` - Update schedule
- `POST /api/schedules/{id}/complete` - Mark as completed

### System API
- `GET /api/system/metrics` - Current system metrics
- `GET /api/system/info` - System information
- `GET /api/system/recommendations` - Optimization tips

### Analytics API
- `GET /api/analytics/game/{gameId}` - Game-specific analytics
- `GET /api/analytics/overall` - Overall gaming statistics

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## 📝 License

This project is licensed under the MIT License - see the LICENSE file for details.

## 🎮 Why This Application is Unique

Unlike simple game time trackers, this application provides:

1. **Holistic Gaming Analysis** - Combines performance metrics, system monitoring, and mood tracking
2. **Proactive Optimization** - Provides real-time recommendations to improve gaming experience
3. **Health-Conscious Gaming** - Helps prevent burnout through schedule management and mood tracking
4. **System Integration** - Deep integration with system metrics for performance correlation
5. **Actionable Insights** - Data-driven recommendations that actually help improve gaming performance

This tool transforms casual gaming tracking into a comprehensive performance optimization platform, helping gamers understand not just what they play, but how they can play better.

## 🚨 Troubleshooting

### Common Issues

1. **System metrics not showing**: Ensure the application has appropriate system permissions
2. **Temperature readings unavailable**: Normal on some systems; other metrics will still work
3. **Sessions not starting**: Check that the game exists and no other active session is running for that game

### Support

For issues and questions, please create an issue in the GitHub repository.

---

**Happy Gaming! 🎮**
