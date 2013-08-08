/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.efsf.sf.bean;

import com.efsf.sf.sql.dao.ClientCaseDAO;
import com.efsf.sf.sql.entity.ClientCase;
import com.efsf.sf.sql.entity.Consultant;
import java.util.ArrayList;
import java.util.Set;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author XaI
 */
@ManagedBean
@ViewScoped
public class ClientViewedCaseMB 
{
    @ManagedProperty(value="#{clientMainPageMB}")
    private ClientMainPageMB clientMainPageMB;

    private ClientCase clientCase;
    
    @PostConstruct
    public void load()
    {
        clientCase = new ClientCaseDAO().getClientCaseWithConsultantDetails(clientMainPageMB.getSelectedCase().getIdClientCase());
    }
    
    public ArrayList<Consultant> castConsultantSetToArray(Set<Consultant> cSet)
    {
        return new ArrayList<Consultant>(cSet);
    }
    
    public ClientCase getClientCase() {
        return clientCase;
    }

    public void setClientCase(ClientCase clientCase) {
        this.clientCase = clientCase;
    }

    public ClientMainPageMB getClientMainPageMB() {
        return clientMainPageMB;
    }

    public void setClientMainPageMB(ClientMainPageMB clientMainPageMB) {
        this.clientMainPageMB = clientMainPageMB;
    }
    
    
}
