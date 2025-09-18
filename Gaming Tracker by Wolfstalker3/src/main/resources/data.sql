-- Sample data for Gaming Session Optimizer

-- Insert sample games
INSERT INTO games (name, genre, platform, created_at) VALUES
('Valorant', 'FPS', 'PC', '2024-01-15 10:00:00'),
('League of Legends', 'MOBA', 'PC', '2024-01-16 11:00:00'),
('Cyberpunk 2077', 'RPG', 'PC', '2024-01-17 12:00:00'),
('Rocket League', 'Sports', 'PC', '2024-01-18 13:00:00'),
('The Witcher 3', 'RPG', 'PC', '2024-01-19 14:00:00');

-- Insert sample gaming sessions
INSERT INTO gaming_sessions (game_id, start_time, end_time, duration_minutes, kills, deaths, assists, accuracy, score, avg_fps, avg_cpu_usage, avg_gpu_usage, avg_memory_usage, avg_temperature, mood_before, mood_after, performance_rating, notes) VALUES
(1, '2024-01-20 19:00:00', '2024-01-20 21:30:00', 150, 25, 12, 8, 75.5, 4500, 144.2, 65.5, 80.2, 72.1, 68.5, 'GOOD', 'EXCELLENT', 'GOOD', 'Great aim today, feeling confident'),
(1, '2024-01-21 20:00:00', '2024-01-21 22:00:00', 120, 18, 15, 6, 68.2, 3200, 142.8, 70.2, 82.1, 74.5, 71.2, 'NEUTRAL', 'FRUSTRATED', 'POOR', 'Bad day, too many deaths'),
(2, '2024-01-22 18:30:00', '2024-01-22 20:45:00', 135, 12, 8, 22, 0.0, 0, 120.5, 68.8, 75.6, 71.2, 69.8, 'EXCELLENT', 'GOOD', 'EXCELLENT', 'Carried the team as support'),
(3, '2024-01-23 21:00:00', '2024-01-23 23:30:00', 150, 0, 0, 0, 0.0, 0, 85.6, 72.5, 95.2, 78.9, 75.2, 'GOOD', 'EXCELLENT', 'GOOD', 'Exploring Night City, beautiful graphics');

-- Insert sample gaming schedules
INSERT INTO gaming_schedules (title, game_id, scheduled_time, estimated_duration_minutes, type, description, is_completed, reminder_sent, created_at) VALUES
('Valorant Ranked Session', 1, '2024-01-25 19:00:00', 120, 'ONE_TIME', 'Push for Diamond rank', false, false, '2024-01-24 10:00:00'),
('Weekly League Match', 2, '2024-01-26 20:00:00', 90, 'WEEKLY', 'Team practice with friends', false, false, '2024-01-24 11:00:00'),
('Cyberpunk Story Mode', 3, '2024-01-27 21:00:00', 180, 'ONE_TIME', 'Continue main questline', false, false, '2024-01-24 12:00:00'),
('Rocket League Tournament', 4, '2024-01-28 18:00:00', 240, 'TOURNAMENT', 'Online tournament participation', false, false, '2024-01-24 13:00:00'),
('Daily Valorant Practice', 1, '2024-01-29 19:30:00', 60, 'DAILY', 'Aim training and warmup', false, false, '2024-01-24 14:00:00');
