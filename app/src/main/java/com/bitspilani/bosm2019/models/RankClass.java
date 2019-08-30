package com.bitspilani.bosm2019.models;

public class RankClass {
    private String username;
    private int wallet;
    private int rank;

    public RankClass() {
    }

    public RankClass(int wallet, String username,int rank) {
        this.wallet = wallet;
        this.username = username;
        this.rank = rank;
    }

    public int getWallet() {
        return wallet;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public void setWallet(int wallet) {
        this.wallet = wallet;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
