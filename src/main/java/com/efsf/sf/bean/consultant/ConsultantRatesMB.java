/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.efsf.sf.bean.consultant;

import com.efsf.sf.bean.client.ClientMainPageMB;
import com.efsf.sf.sql.dao.CaseRatingDAO;
import com.efsf.sf.sql.entity.CaseRating;
import com.efsf.sf.sql.entity.ClientCase;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import org.primefaces.event.SelectEvent;

/**
 *
 * @author EI GLOBAL
 */
@ManagedBean
@RequestScoped
public class ConsultantRatesMB implements Serializable{
    
    @ManagedProperty(value="#{loginMB.consultant.idConsultant}")
    private Integer idConsultant;

    
    public String showConsulantRatesPage(){
        return "/consultant/consultantReceivedComments";
    }    
    
     public List<ClientCase> getConsulantRates(){
        CaseRatingDAO dao = new CaseRatingDAO();
        return dao.getConsultantRatings(idConsultant);
    }
     
   public void onRowSelect(SelectEvent event) throws IOException {  
        ClientCase clientCase=((ClientCase) event.getObject());  
        FacesContext.getCurrentInstance().getExternalContext().redirect("/consultant/consultantCaseDetails.xhtml"); 
    }
   
    public Integer getIdConsultant() {
        return idConsultant;
    }


    public void setIdConsultant(Integer idConsultant) {
        this.idConsultant = idConsultant;
    }
    
}