/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.efsf.sf.bean;

import com.efsf.sf.sql.dao.ClientCaseDAO;
import com.efsf.sf.sql.entity.ClientCase;
import com.efsf.sf.util.Converters;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

/**
 *
 * @author XaI
 */
@ManagedBean
@ApplicationScoped
public class MarketMB
{
    private List<ClientCase> clientCaseList = new ArrayList();
    
    private Converters converters =  new Converters();
    
    private ClientCase[] selectedCases;
    
    private ClientCase selectedCase;
    
    public MarketMB()
    {
        reloadCases();
    }
   
    
    public void reloadCases()
    {
        ClientCaseDAO caseDao = new ClientCaseDAO();
        
        clientCaseList =  caseDao.last5Cases();
        
        System.out.println("Pobrano"); 
    }
    
    public List<ClientCase> getClientCaseList() {
        return clientCaseList;
    }

    public void setClientCaseList(List<ClientCase> clientCaseList) {
        this.clientCaseList = clientCaseList;
    }

    public Converters getConverters() {
        return converters;
    }

    public void setConverters(Converters converters) {
        this.converters = converters;
    }

    public ClientCase[] getSelectedCases() {
        return selectedCases;
    }

    public void setSelectedCases(ClientCase[] selectedCases) {
        this.selectedCases = selectedCases;
    }

    public ClientCase getSelectedCase() {
        return selectedCase;
    }

    public void setSelectedCase(ClientCase selectedCase) {
        this.selectedCase = selectedCase;
    }
    
    
    
}
