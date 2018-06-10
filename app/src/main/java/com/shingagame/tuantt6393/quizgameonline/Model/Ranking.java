package com.shingagame.tuantt6393.quizgameonline.Model;

public class Ranking {
    private String UserName;
    private long Score;

    public Ranking() {
    }

    public Ranking(String userName, long score) {
        UserName = userName;
        Score = score;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public long getScore() {
        return Score;
    }

    public void setScore(long score) {
        Score = score;
    }
}
