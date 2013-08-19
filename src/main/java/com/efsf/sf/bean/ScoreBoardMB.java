/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.efsf.sf.bean;

import com.efsf.sf.sql.dao.GenericDao;
import com.efsf.sf.sql.entity.ConsultantRating;
import java.io.Serializable;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;


@ManagedBean
@RequestScoped
public class ScoreBoardMB implements Serializable{
    

    public String showScoreBoard(){
        return "/common/scoreBoard";
    }
    
    public List<ConsultantRating> getBoard() {
        GenericDao<ConsultantRating> dao = new GenericDao(ConsultantRating.class);
        return dao.getAllInOrder("average", "desc");
    }
    
    
    
}
