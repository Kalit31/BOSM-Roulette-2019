package com.bitspilani.bosm2019.models;

public class Fixture {
    private String team1,team2;
    private String venue;
    private String time;
    private String matchId;

    public Fixture(String team1, String team2, String venue, String time,String id) {
        this.team1 = team1;
        this.team2 = team2;
        this.venue = venue;
        this.time = time;
        this.matchId = id;
    }

    public String getMatchId() {
        return matchId;
    }

    public void setMatchId(String matchId) {
        this.matchId = matchId;
    }

    public String getteam1() {
        return team1;
    }

    public String getteam2() {
        return team2;
    }

    public String getvenue() {
        return venue;
    }

    public String getTime() {
        return time;
    }
}
