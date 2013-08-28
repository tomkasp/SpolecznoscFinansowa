/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.efsf.sf.bean;

import com.efsf.sf.sql.dao.GenericDao;
import com.efsf.sf.sql.entity.Consultant;
import com.efsf.sf.sql.entity.ConsultantRating;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;


@ManagedBean
@ViewScoped
public class ScoreBoardMB implements Serializable{
    
    private ConsultantRating consultantRating;
    
    public String showScoreBoard(){
        return "/common/scoreBoard?faces-redirect=true";
    }
    
    
    public List<ConsultantRating> getBoard() {
        GenericDao<ConsultantRating> dao = new GenericDao(ConsultantRating.class);
        return dao.getAllInOrder("average", "desc");
    }
    
    public int fetchRankingPlace(Consultant cos)
    {
        GenericDao<ConsultantRating> dao = new GenericDao(ConsultantRating.class);
        List<ConsultantRating> ranking =  dao.getAllInOrder("average", "desc");
        boolean found = false;
        int  i;
        for(i = 0; i < ranking.size(); i++)
        {
            if (ranking.get(i).getConsultant().getIdConsultant().equals(cos.getIdConsultant()))
            {
                found = true;
                break;   
            }
        }
        
        return found ? (i+1) : -1;
    }
    
    public void rowDoubleClick(Consultant cos) throws IOException
    {   
        FacesContext.getCurrentInstance().getExternalContext().redirect("./../client/clientConsultantProfileView.xhtml?idConsultant=" + cos.getIdConsultant()); 
    }

    public ConsultantRating getConsultantRating() {
        return consultantRating;
    }

    public void setConsultantRating(ConsultantRating consultantRating) {
        this.consultantRating = consultantRating;
    }

    
    
    
}
