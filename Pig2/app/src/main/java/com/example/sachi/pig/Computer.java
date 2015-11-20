package com.example.sachi.pig;

/**
 * Created by Kapugamage Sachith on 11/19/2015.
 */
public class Computer {
    private int score;
    private int totalScore;
    private int hit;
    private int turn;
    Computer(){
        score = 0;
        totalScore = 0;
        hit = 10;
        turn = 1;
    }

    public int updateScore(int value){
        if(score >= hit){
            if(turn <10)
                turn++;

            score = 0;
            hit *= turn;
            return 1;
        }
        if(totalScore >= 10){
            return 2;
        }
        else if(value == 1 )
        {
            totalScore -= score;
            score = 0;
            return 1;
        }

        else {
            score += value;
            totalScore += value;
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
        hit = 10;
        turn = 1;
    }


}
