package com.bitspilani.bosmroulette.models;

public class TrendingModel {
    private String college1,college2;
    private String timestamp;
    private String game;
    private String matchId;
    private String score1,score2;

    public TrendingModel() {
    }

    public TrendingModel(String college1, String college2, String timestamp, String game, String matchId, String score1, String score2) {
        this.college1 = college1;
        this.college2 = college2;
        this.timestamp = timestamp;
        this.game = game;
        this.matchId = matchId;
        this.score1 = score1;
        this.score2 = score2;
    }

    public String getCollege1() {
        return college1;
    }

    public void setCollege1(String college1) {
        this.college1 = college1;
    }

    public String getCollege2() {
        return college2;
    }

    public void setCollege2(String college2) {
        this.college2 = college2;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getGame() {
        return game;
    }

    public void setGame(String game) {
        this.game = game;
    }

    public String getMatchId() {
        return matchId;
    }

    public void setMatchId(String matchId) {
        this.matchId = matchId;
    }

    public String getScore1() {
        return score1;
    }

    public void setScore1(String score1) {
        this.score1 = score1;
    }

    public String getScore2() {
        return score2;
    }

    public void setScore2(String score2) {
        this.score2 = score2;
    }
}
