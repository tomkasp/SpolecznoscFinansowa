
package com.efsf.sf.bean;

import com.efsf.sf.sql.dao.CaseRatingDAO;
import com.efsf.sf.sql.entity.CaseRating;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;


@ManagedBean
@RequestScoped
public class CaseRatingMB implements Serializable{
    
    
    private CaseRating caseRating = new CaseRating();
    
    public String saveRating(){
      CaseRatingDAO dao= new CaseRatingDAO();
      
      caseRating.setClientCase(dao.getTestCase());
      dao.save(caseRating);
      return "/index"; 
    } 


    public CaseRating getCaseRating() {
        return caseRating;
    }


    public void setCaseRating(CaseRating caseRating) {
        this.caseRating = caseRating;
    }
    
       
       
}
