package com.bitspilani.bosmroulette.models;

public class MyBetsModel
{
    String event;
    int betAmount;
    String type;
    boolean result;
    boolean win;

    public MyBetsModel() {
    }

    public MyBetsModel(String event, int betAmount,String type,boolean result,boolean win) {
        this.event = event;
        this.betAmount = betAmount;
        this.type = type;
        this.result = result;
        this.win = win;
    }

    public boolean isWin() {
        return win;
    }

    public void setWin(boolean win) {
        this.win = win;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public int getBetAmount() {
        return betAmount;
    }

    public void setBetAmount(int betAmount) {
        this.betAmount = betAmount;
    }
}
