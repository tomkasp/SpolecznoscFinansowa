/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.efsf.sf.bean;

import com.efsf.sf.sql.dao.ClientCaseDAO;
import com.efsf.sf.sql.entity.ClientCase;
import java.util.ArrayList;
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
    ArrayList<ClientCase> clientCaseList = new ArrayList();
    
   
    
    public void reloadCases()
    {
        ClientCaseDAO caseDao = new ClientCaseDAO();
        
    }
    
    
    
}
