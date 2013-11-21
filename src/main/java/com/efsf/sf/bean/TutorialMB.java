/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.efsf.sf.bean;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author Doradca
 */

@ManagedBean
@ViewScoped
public class TutorialMB 
{
    private Integer videoId; 
    private String videoPath; 
    private String videoTitle; 

    static String rootPath = "/SpolecznoscFinansowa/resources/movies/";
    
    private List<String> titles = new ArrayList<String>(Arrays.asList("Rejestracja użytkownika","Uzupełnienie profilu", "Dołączanie plików", "Poruszanie się po stronie", "Jak stąd najszybciej uciec"));
    
    private List<String> paths = new ArrayList<String>(Arrays.asList("01.mp4","02.mp4","03.mp4","04.mp4","05.mp4"));
    
    public void loadMovie() {
        if(videoId == null) {
            videoId = 0;
        }
        else if(videoId >= titles.size() )
        {
            videoId = titles.size()-1;
        }
        videoPath =  rootPath + paths.get(videoId);
        videoTitle = titles.get(videoId);
    }
    
    public Integer getVideoId() {
        return videoId;
    }

    public void setVideoId(Integer videoId) {
        this.videoId = videoId;
    }

    public String getVideoPath() {
        return videoPath;
    }

    public void setVideoPath(String videoPath) {
        this.videoPath = videoPath;
    }

    public String getVideoTitle() {
        return videoTitle;
    }

    public void setVideoTitle(String videoTitle) {
        this.videoTitle = videoTitle;
    }

    public List<String> getTitles() {
        return titles;
    }

    public List<String> getPaths() {
        return paths;
    }
     

}
