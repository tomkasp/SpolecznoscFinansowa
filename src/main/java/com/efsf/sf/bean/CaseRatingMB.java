
package com.efsf.sf.bean;

import com.efsf.sf.sql.dao.CaseRatingDAO;
import com.efsf.sf.sql.entity.CaseRating;
import java.io.Serializable;
import java.util.Date;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;


@ManagedBean
@RequestScoped
public class CaseRatingMB implements Serializable{
    
    
    private CaseRating caseRating = new CaseRating();
    private Integer caseId;
    
    public String saveRating(){
     
      double average=0;  
      CaseRatingDAO dao= new CaseRatingDAO();

      caseRating.setIdRating(dao.getCase(4).getIdClientCase());
      average+=caseRating.getCompetence()+caseRating.getContact()
              +caseRating.getCulture()+caseRating.getDifficulty()
              +caseRating.getPunctuality()+caseRating.getReliability()
              +caseRating.getRespect()+caseRating.getTrust();
      
      average/=8;
      caseRating.setAverage(average);
      caseRating.setCommentDate(new Date());
      dao.save(caseRating);
      return "/index"; 
    } 

    public String showRatePage(Integer caseId){
        this.setCaseId(caseId);
     System.out.println("CaseID: "+caseId);
        
     return "/client/clientConsultantRate";  
    }

    public CaseRating getCaseRating() {
        return caseRating;
    }


    public void setCaseRating(CaseRating caseRating) {
        this.caseRating = caseRating;
    }


    public Integer getCaseId() {
        return caseId;
    }


    public void setCaseId(Integer caseId) {
        this.caseId = caseId;
    }
    
 
}
