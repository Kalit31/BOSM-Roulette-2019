package com.bitspilani.bosm2019.models;

public class UserBetModel
{
    private String match_id;
    private double betAmount;
    private String team;
    private int result;

    public UserBetModel() {
    }

    public UserBetModel(String match_id, double betAmount, String team, int result) {
        this.match_id = match_id;
        this.betAmount = betAmount;
        this.team = team;
        this.result = result;
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

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }
}
