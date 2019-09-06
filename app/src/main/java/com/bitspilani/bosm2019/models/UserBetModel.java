package com.bitspilani.bosm2019.models;

public class UserBetModel
{
    private String match_id;
    private double betAmount;
    private String team1;
    private String team2;
    private int bettedOn;
    private String game;
    private boolean update;
    private int score1,score2,result;

    public UserBetModel() {
    }

    public UserBetModel(String match_id, double betAmount, String team1, String team2, int bettedOn, String game, boolean update
    ,int score1,int score2,int result) {
        this.score1 = score1;
        this.score2 = score2;
        this.match_id = match_id;
        this.betAmount = betAmount;
        this.team1 = team1;
        this.team2 = team2;
        this.bettedOn = bettedOn;
        this.game = game;
        this.update = update;
        this.result = result;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public int getScore1() {
        return score1;
    }

    public void setScore1(int score1) {
        this.score1 = score1;
    }

    public int getScore2() {
        return score2;
    }

    public void setScore2(int score2) {
        this.score2 = score2;
    }

    public String getMatch_id() {
        return match_id;
    }

    public void setMatch_id(String match_id) {
        this.match_id = match_id;
    }

    public double getBetAmount() {
        return betAmount;
    }

    public void setBetAmount(double betAmount) {
        this.betAmount = betAmount;
    }

    public String getTeam1() {
        return team1;
    }

    public void setTeam1(String team1) {
        this.team1 = team1;
    }

    public String getTeam2() {
        return team2;
    }

    public void setTeam2(String team2) {
        this.team2 = team2;
    }

    public int getBettedOn() {
        return bettedOn;
    }

    public void setBettedOn(int bettedOn) {
        this.bettedOn = bettedOn;
    }

    public String getGame() {
        return game;
    }

    public void setGame(String game) {
        this.game = game;
    }

    public boolean isUpdate() {
        return update;
    }

    public void setUpdate(boolean update) {
        this.update = update;
    }
}
