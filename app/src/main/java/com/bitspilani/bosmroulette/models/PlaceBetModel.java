package com.bitspilani.bosmroulette.models;

public class PlaceBetModel {

    private int betAmount;
    private String userId;
    private String college;

    public PlaceBetModel() {
    }

    public PlaceBetModel(int betAmount, String userId, String college) {
        this.betAmount = betAmount;
        this.userId = userId;
        this.college = college;
    }

    public int getBetAmount() {
        return betAmount;
    }

    public void setBetAmount(int betAmount) {
        this.betAmount = betAmount;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCollege() {
        return college;
    }

    public void setCollege(String college) {
        this.college = college;
    }
}
