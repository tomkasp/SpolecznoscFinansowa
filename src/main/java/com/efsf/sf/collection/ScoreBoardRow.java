/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.efsf.sf.collection;

import java.io.Serializable;

/**
 *
 * @author EI GLOBAL
 */
public class ScoreBoardRow implements Serializable{
    
    private String title;
    private Double score;
    private Double average_score;
    private Integer max_score;
    private Integer min_score;

    
    public ScoreBoardRow(String title, Double score, Double average_score, Integer max_score, Integer min_score){
       this.title=title;
       this.score=score;
       this.average_score=average_score;
       this.max_score=max_score;
       this.min_score=min_score;
               
    }
            

    public Double getScore() {
        return score;
    }


    public void setScore(Double score) {
        this.score = score;
    }


    public Double getAverage_score() {
        return average_score;
    }


    public void setAverage_score(Double average_score) {
        this.average_score = average_score;
    }


    public Integer getMax_score() {
        return max_score;
    }


    public void setMax_score(Integer max_score) {
        this.max_score = max_score;
    }


    public Integer getMin_score() {
        return min_score;
    }


    public void setMin_score(Integer min_score) {
        this.min_score = min_score;
    }

    public String getTitle() {
        return title;
    }


    public void setTitle(String title) {
        this.title = title;
    }
    
}
