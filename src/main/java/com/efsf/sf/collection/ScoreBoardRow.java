package com.efsf.sf.collection;

import java.io.Serializable;

public class ScoreBoardRow implements Serializable{
    
    private String title;
    private Double score;
    private Double averageScore;
    private Integer maxScore;
    private Integer minScore;

    
    public ScoreBoardRow(String title, Double score, Double averageScore, Integer maxScore, Integer minScore){
       this.title=title;
       this.score=score;
       this.averageScore=averageScore;
       this.maxScore=maxScore;
       this.minScore=minScore;
               
    }
            

    public Double getScore() {
        return score;
    }


    public void setScore(Double score) {
        this.score = score;
    }


    public Double getAverageScore() {
        return averageScore;
    }


    public void setAverageScore(Double averageScore) {
        this.averageScore = averageScore;
    }


    public Integer getMaxScore() {
        return maxScore;
    }


    public void setMaxScore(Integer maxScore) {
        this.maxScore = maxScore;
    }


    public Integer getMinScore() {
        return minScore;
    }


    public void setMinScore(Integer minScore) {
        this.minScore = minScore;
    }

    public String getTitle() {
        return title;
    }


    public void setTitle(String title) {
        this.title = title;
    }
    
}
