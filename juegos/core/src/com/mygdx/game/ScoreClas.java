/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.game;

/**
 *
 * @author Infante96
 */
public class ScoreClas{
    public int scoreMax;

    ScoreClas() {
           this.scoreMax = 0;
    }
    public ScoreClas(int scoreMax) {
        this.scoreMax = scoreMax;
    }

    public int getScoreMax() {
        return scoreMax;
    }

    public void setScoreMax(int scoreMax) {
        this.scoreMax = scoreMax;
    }
    
}
