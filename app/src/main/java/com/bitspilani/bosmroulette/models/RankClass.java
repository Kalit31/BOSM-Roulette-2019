package com.bitspilani.bosmroulette.models;

public class RankClass {
    private String username;
    private String id;
    private double wallet;
    private int rank;

    public RankClass() {
    }

    public RankClass(double wallet, String username,int rank,String id) {
        this.wallet = wallet;
        this.username = username;
        this.rank = rank;
        this.id=id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getWallet() {
        return wallet;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public void setWallet(double wallet) {
        this.wallet = wallet;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
