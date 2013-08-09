package com.efsf.sf.bean.consultant;

import com.efsf.sf.bean.client.ClientCaseMB;
import com.efsf.sf.sql.dao.CaseRatingDAO;
import com.efsf.sf.sql.entity.ClientCase;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;

/**
 *
 * @author EI GLOBAL
 */
@ManagedBean
@RequestScoped
public class ConsultantRatesMB implements Serializable{
    
    @ManagedProperty(value="#{loginMB.consultant.idConsultant}")
    private Integer idConsultant;
    
    @ManagedProperty(value="#{clientCaseMB}")
    private ClientCaseMB clientCaseMB;
    
    @ManagedProperty(value="#{marketMB}")
    private MarketMB marketMB;
    
    private ClientCase clientCase;

    
    public String showConsulantRatesPage(){
        return "/consultant/consultantReceivedComments";
    }    
    
     public List<ClientCase> getConsulantRates(){
        CaseRatingDAO dao = new CaseRatingDAO();
        return dao.getConsultantRatings(idConsultant);
    }
     
   public void onRowSelect() throws IOException { 
        clientCaseMB.setSelectedClientCase(clientCase);
        marketMB.rowDoubleClick();
        
    }
   
    public Integer getIdConsultant() {
        return idConsultant;
    }

    public void setIdConsultant(Integer idConsultant) {
        this.idConsultant = idConsultant;
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


    public MarketMB getMarketMB() {
        return marketMB;
    }


    public void setMarketMB(MarketMB marketMB) {
        this.marketMB = marketMB;
    }
    
}