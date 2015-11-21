package com.example.sachi.pig;

/**
 * Created by Kapugamage Sachith on 11/19/2015.
 */
public class Player {
    private int score;
    private int totalScore;
    private String playerName;

    Player(String name){
        score = 0;
        totalScore = 0;
        playerName = name;
    }

    public int updateScore(int value) {
        if(value == 0){
            score = 0;
        }

        if (value == 1) {
            totalScore -= score;
            score = 0;
            return 1;
        } else {
            score += value;
            totalScore += value;

            if (totalScore >= 100)
                return 2;
            return 0;
        }
    }
    public int getScore(){
        return score;
    }
    public int getTotalScore(){
        return totalScore;
    }
    public void reset(){
        score = 0;
        totalScore = 0;
    }
}
