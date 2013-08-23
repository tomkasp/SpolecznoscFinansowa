package com.efsf.sf.bean;

import com.efsf.sf.bean.client.CaseViewMB;
import com.efsf.sf.bean.client.ClientCaseMB;
import com.efsf.sf.sql.dao.CaseRatingDAO;
import com.efsf.sf.sql.entity.CaseRating;
import com.efsf.sf.sql.entity.ClientCase;
import java.io.Serializable;
import java.util.Date;
import java.util.ResourceBundle;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

@ManagedBean
@ViewScoped
public class CaseRatingMB implements Serializable {

    @ManagedProperty(value="#{clientCaseMB}")
    private ClientCaseMB clientCaseMB;
        
    @ManagedProperty(value = "#{caseViewMB}")
    private CaseViewMB caseViewMB;
    
    @ManagedProperty(value = "#{messagesMB}")
    private MessagesMB messagesMB;
    
    @ManagedProperty("#{msg}")
    private transient ResourceBundle bundle;
    
    private ClientCase clientCase;
    
    private CaseRating caseRating = new CaseRating();

    @PostConstruct
    public void load()
    {
        
        clientCase = clientCaseMB.getCurrentlyRatedCase();
    }
    
    public String saveRating() {
        
        double average = 0;
        CaseRatingDAO dao = new CaseRatingDAO();
        
        caseRating.setClientCase(clientCase);
        //caseRating.setIdRating(caseViewMB.getSelectedClientCase().getIdClientCase());
        average += caseRating.getCompetence() + caseRating.getContact()
                + caseRating.getCulture() + caseRating.getDifficulty()
                + caseRating.getPunctuality() + caseRating.getReliability()
                + caseRating.getRespect() + caseRating.getTrust();

        average /= 8;
        caseRating.setAverage(average);
        caseRating.setCommentDate(new Date());
        dao.save(caseRating);

        messagesMB.generateSystemMessage(bundle.getString("CASE_RATED"), clientCase.getConsultant().getUser().getIdUser(), new Object[] {clientCase.getIdClientCase()} );
        
        return "/client/clientCaseDetails?faces-redirect=true&clientCaseId= " + clientCase.getIdClientCase();
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

    public MessagesMB getMessagesMB() {
        return messagesMB;
    }

    public void setMessagesMB(MessagesMB messagesMB) {
        this.messagesMB = messagesMB;
    }

    public ResourceBundle getBundle() {
        return bundle;
    }

    public void setBundle(ResourceBundle bundle) {
        this.bundle = bundle;
    }

    public CaseViewMB getCaseViewMB() {
        return caseViewMB;
    }

    public void setCaseViewMB(CaseViewMB caseViewMB) {
        this.caseViewMB = caseViewMB;
    }

    public ClientCase getClientCase() {
        return clientCase;
    }

    public void setClientCase(ClientCase clientCase) {
        this.clientCase = clientCase;
    }

    public ClientCaseMB getClientCaseMB() {
        return clientCaseMB;
    }

    public void setClientCaseMB(ClientCaseMB clientCaseMB) {
        this.clientCaseMB = clientCaseMB;
    }
}
