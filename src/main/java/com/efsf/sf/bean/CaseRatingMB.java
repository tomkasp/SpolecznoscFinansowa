package com.efsf.sf.bean;

import com.efsf.sf.bean.client.ClientCaseMB;
import com.efsf.sf.sql.dao.CaseRatingDAO;
import com.efsf.sf.sql.entity.CaseRating;
import java.io.Serializable;
import java.util.Date;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;

@ManagedBean
@RequestScoped
public class CaseRatingMB implements Serializable {

    @ManagedProperty(value = "#{clientCaseMB}")
    private ClientCaseMB clientCaseMB;
    private CaseRating caseRating = new CaseRating();

    public String saveRating() {
        
        double average = 0;
        CaseRatingDAO dao = new CaseRatingDAO();
        
        caseRating.setClientCase(clientCaseMB.getSelectedClientCase());
        //caseRating.setIdRating(clientCaseMB.getSelectedClientCase().getIdClientCase());
        average += caseRating.getCompetence() + caseRating.getContact()
                + caseRating.getCulture() + caseRating.getDifficulty()
                + caseRating.getPunctuality() + caseRating.getReliability()
                + caseRating.getRespect() + caseRating.getTrust();

        average /= 8;
        caseRating.setAverage(average);
        caseRating.setCommentDate(new Date());
        dao.save(caseRating);
        
        return "/client/clientCaseDetails";
    }
    
    public boolean isNotRated(){
        CaseRatingDAO dao = new CaseRatingDAO();
        return dao.isNotRated(clientCaseMB.getSelectedClientCase().getIdClientCase());
    }
    
    public String showRatePage() {
        return "/client/clientConsultantRate";
    }

    public CaseRating getCaseRating() {
        return caseRating;
    }

    public void setCaseRating(CaseRating caseRating) {
        this.caseRating = caseRating;
    }

    public ClientCaseMB getClientCaseMB() {
        return clientCaseMB;
    }

    public void setClientCaseMB(ClientCaseMB clientCaseMB) {
        this.clientCaseMB = clientCaseMB;
    }
}
