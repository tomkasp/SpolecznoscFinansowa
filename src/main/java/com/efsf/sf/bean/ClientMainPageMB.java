package com.efsf.sf.bean;

import com.efsf.sf.collection.IncomeData;
import com.efsf.sf.sql.dao.ClientCaseDAO;
import com.efsf.sf.sql.entity.*;
import com.efsf.sf.util.Converters;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

@ManagedBean
@SessionScoped
public class ClientMainPageMB implements Serializable {
    private static final long serialVersionUID = 1L;

    @ManagedProperty(value="#{loginMB}")
    private LoginMB loginMB;
    
    private List<ClientCase> clientCaseList = new ArrayList();
    
    private List<ClientCase> awaitingClientCaseList = new ArrayList();
    
    ClientCaseDAO caseDao = new ClientCaseDAO();

    private Converters converters =  new Converters();
    
    private ClientCase selectedCase;
    
    ArrayList<Set<String>> modelsEmploymentType = new ArrayList();
    
    private ArrayList<Set<String>> modelsBranch = new ArrayList();
    
    private ArrayList<IncomeData> selectedCaseIncomeTable = new ArrayList<IncomeData>();
   
    public void reloadCases()
    {
        modelsEmploymentType = new ArrayList();
        
        modelsBranch= new ArrayList();
        
        clientCaseList = caseDao.last5CasesSelectedClient( loginMB.getClient().getIdClient() );
        
        System.out.println("SIZA: "+loginMB.getClient().getIdClient() );
        for (int i = 0; i<clientCaseList.size(); i++)
        {
            modelsEmploymentType.add( showAllClientsEmploymentTypes( clientCaseList.get(i).getClient() ) );
            modelsBranch.add( showAllClientsBranches( clientCaseList.get(i).getClient() ) );
        }
        
        System.out.println("Pobrano"); 
    }
        
    public void reloadCases2()
    {
        modelsEmploymentType = new ArrayList();
        
        modelsBranch = new ArrayList();
        
        awaitingClientCaseList = caseDao.awaitingCasesSelectedClient( loginMB.getClient().getIdClient() );
        
        System.out.println("SIZA: "+loginMB.getClient().getIdClient() );
        for (int i = 0; i<awaitingClientCaseList.size(); i++)
        {
            modelsEmploymentType.add( showAllClientsEmploymentTypes( awaitingClientCaseList.get(i).getClient() ) );
            modelsBranch.add( showAllClientsBranches( awaitingClientCaseList.get(i).getClient() ) );
        }
        
        System.out.println("Pobrano"); 
    }
    
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
    
    
    
       
    public void rowDoubleClick() throws IOException
    {
        System.out.println("2 razy: "  + selectedCase.getIdClientCase());
        fillSelectedCaseIncomeTable();
        FacesContext.getCurrentInstance().getExternalContext().redirect("consultantCaseDetails.xhtml"); 
    }
    
    public void rowClick()
    {
        System.out.println("1 raz: " + selectedCase.getIdClientCase());
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

     public void fillSelectedCaseIncomeTable() {
       
        selectedCaseIncomeTable = new ArrayList();
        
        for (Income i : selectedCase.getClient().getIncomes())
        {
            selectedCaseIncomeTable.add(new IncomeData(i.getEmploymentType().getName(), i.getBranch().getName(), i.getMonthlyNetto().doubleValue()));
        }
        
        for (IncomeBusinessActivity i : selectedCase.getClient().getIncomeBusinessActivities())
        {
            selectedCaseIncomeTable.add(new IncomeData(i.getEmploymentType().getName(), i.getBranch().getName(), i.getIncomeLastYearNetto().doubleValue()));
        }
    }
    
    public LoginMB getLoginMB() {
        return loginMB;
    }

    public void setLoginMB(LoginMB loginMB) {
        this.loginMB = loginMB;
    }

    public List<ClientCase> getClientCaseList() {
        reloadCases();
        return clientCaseList;
    }

    public void setClientCaseList(List<ClientCase> clientCaseList) {
        this.clientCaseList = clientCaseList;
    }

    public ClientCaseDAO getCaseDao() {
        return caseDao;
    }

    public void setCaseDao(ClientCaseDAO caseDao) {
        this.caseDao = caseDao;
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

    public Converters getConverters() {
        return converters;
    }

    public void setConverters(Converters converters) {
        this.converters = converters;
    }

    public ArrayList<IncomeData> getSelectedCaseIncomeTable() {
        return selectedCaseIncomeTable;
    }

    public void setSelectedCaseIncomeTable(ArrayList<IncomeData> selectedCaseIncomeTable) {
        this.selectedCaseIncomeTable = selectedCaseIncomeTable;
    }

    public List<ClientCase> getAwaitingClientCaseList() {
        reloadCases2();
        return awaitingClientCaseList;
    }

    public void setAwaitingClientCaseList(List<ClientCase> awaitingClientCaseList) {
        this.awaitingClientCaseList = awaitingClientCaseList;
    }
     
    
    
    
}