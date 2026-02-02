package com.kob.matchingsystem.service;

public interface MatchingService {
    String addPlayer(Integer user_id, Integer rating, Integer bot_id);
    String removePlayer(Integer user_id);
}
