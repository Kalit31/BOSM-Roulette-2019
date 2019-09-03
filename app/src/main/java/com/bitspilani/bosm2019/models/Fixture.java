package com.bitspilani.bosm2019.models;

public class Fixture {
    private String college1,college2;
    private String timestamp;
    private String game;
    private String matchId;


    public Fixture() {
    }

    public Fixture(String college1, String college2, String timestamp, String matchId,String game) {
        this.college1 = college1;
        this.college2 = college2;
        this.timestamp = timestamp;
        this.matchId = matchId;
        this.game = game;
    }

    public String getGame() {
        return game;
    }

    public void setGame(String game) {
        this.game = game;
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

    public String getMatchId() {
        return matchId;
    }

    public void setMatchId(String matchId) {
        this.matchId = matchId;
    }
}
