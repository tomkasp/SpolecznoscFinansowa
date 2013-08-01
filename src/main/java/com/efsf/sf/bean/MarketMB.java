/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.efsf.sf.bean;

import com.efsf.sf.sql.dao.ClientCaseDAO;
import com.efsf.sf.sql.entity.ClientCase;
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
    
    
    
}
