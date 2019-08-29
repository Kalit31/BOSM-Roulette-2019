package com.bitspilani.bosm2019.models;

public class LeaderBoardModel
{
    String rank,name;
    double wallet;

    public LeaderBoardModel(String rank, String name, double wallet) {
        this.rank = rank;
        this.name = name;
        this.wallet = wallet;
    }

    public LeaderBoardModel() {
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getWallet() {
        return wallet;
    }

    public void setWallet(double wallet) {
        this.wallet = wallet;
    }
}