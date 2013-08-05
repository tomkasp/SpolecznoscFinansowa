/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.efsf.sf.bean;

import com.efsf.sf.sql.dao.ClientCaseDAO;
import com.efsf.sf.sql.entity.Client;
import com.efsf.sf.sql.entity.ClientCase;
import com.efsf.sf.sql.entity.Consultant;
import com.efsf.sf.sql.entity.Income;
import com.efsf.sf.sql.entity.IncomeBusinessActivity;
import com.efsf.sf.sql.entity.RequiredDocuments;
import com.efsf.sf.util.Converters;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
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
    
    ClientCaseDAO caseDao = new ClientCaseDAO();
    
    ArrayList<Set<String>> modelsEmploymentType = new ArrayList();

    private ArrayList<Set<String>> modelsBranch= new ArrayList();
    
    
    
    public MarketMB()
    {
        reloadCases();
    }
   
    
    public void reloadCases()
    {
        clientCaseList =  caseDao.last5Cases();
        
        for (int i = 0; i<clientCaseList.size(); i++)
        {
            modelsEmploymentType.add(showAllClientsEmploymentTypes(clientCaseList.get(i).getClient()));
            modelsBranch.add(showAllClientsBranches(clientCaseList.get(i).getClient()));
        }
        
        
        System.out.println("Pobrano"); 
    }
    
    
    // I THINK THERE SHOULD BE CLASS LIKE MARKET UTIL MADE BECAUSE SUCH METHODS PROBS COULD BE USED SOMEWHERE ELSE TOO
    public int countConsultantApplications(ClientCase cs)
    { 
        Set<Consultant> cons = cs.getConsultants();
        if (cons == null)
            return 0;
        else
            return cons.size();
    }
    
    public Set<String> showAllClientsEmploymentTypes(Client client)
    {
        HashSet<String> types = new HashSet();
        if (client.getIncomes() != null)
        {
            for (Income i : client.getIncomes())
            {
                types.add(i.getEmploymentType().getShortcut());
            }
        }
        if( client.getIncomeBusinessActivities() != null)
        {
            for (IncomeBusinessActivity i : client.getIncomeBusinessActivities())
            {
                types.add(i.getEmploymentType().getShortcut());
            }
        }
        
        return types;
    }
    
    public Set<String> showAllClientsBranches(Client client)
    {
        HashSet<String> types = new HashSet();
        if (client.getIncomes() != null)
        {
            for (Income i : client.getIncomes())
            {
                types.add(i.getBranch().getName());
            }
        }
        if( client.getIncomeBusinessActivities() != null)
        {
            for (IncomeBusinessActivity i : client.getIncomeBusinessActivities())
            {
                types.add(i.getBranch().getName());
            }
        }
        
        return types;
    }
    
    public boolean showBIK(Client client)
    {
        if (client.getRequiredDocumentses().isEmpty())
        {
            return false;
        }
        else
        {
            RequiredDocuments rds = client.getRequiredDocumentses().iterator().next();
            if (rds.getBik() == null)
                return false;
            else
                return true;   
        }   
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
    
    public ArrayList<Set<String>> getModelsEmploymentType() {
        return modelsEmploymentType;
    }

    public void setModelsEmploymentType(ArrayList<Set<String>> modelsEmploymentType) {
        this.modelsEmploymentType = modelsEmploymentType;
    }

    public ArrayList<Set<String>> getModelsBranch() {
        return modelsBranch;
    }

    public void setModelsBranch(ArrayList<Set<String>> modelsBranch) {
        this.modelsBranch = modelsBranch;
    }
    
    
    
}
