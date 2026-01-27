package com.kob.matchingsystem.service;

public interface MatchingService {
    String addPlayer(Integer user_id, Integer rating);
    String removePlayer(Integer user_id);
}
