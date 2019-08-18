package com.bitspilani.bosm2019.models;

public class LeaderBoardModel
{
    String rank,name,score;

    public LeaderBoardModel(String rank, String name, String score) {
        this.rank = rank;
        this.name = name;
        this.score = score;
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

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

}