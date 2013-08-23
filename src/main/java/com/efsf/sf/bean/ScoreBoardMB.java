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
