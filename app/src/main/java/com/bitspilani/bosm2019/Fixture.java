package com.bitspilani.bosm2019;

public class Fixture {
    private String Team1,Team2;
    private String Venue;
    private String Time;

    public Fixture(String team1, String team2, String venue, String time) {
        Team1 = team1;
        Team2 = team2;
        Venue = venue;
        Time = time;
    }

    public String getTeam1() {
        return Team1;
    }

    public String getTeam2() {
        return Team2;
    }

    public String getVenue() {
        return Venue;
    }

    public String getTime() {
        return Time;
    }
}
