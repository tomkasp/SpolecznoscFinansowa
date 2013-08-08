package com.efsf.sf.bean.client;

import com.efsf.sf.bean.LoginMB;
import com.efsf.sf.collection.IncomeData;
import com.efsf.sf.sql.dao.CaseStatusDAO;
import com.efsf.sf.sql.dao.ClientCaseDAO;
import com.efsf.sf.sql.entity.*;
import com.efsf.sf.util.Converters;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.annotation.PostConstruct;
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
    
    private List<ClientCase> clientCaseList = new ArrayList<>();
    
    private List<ClientCase> awaitingClientCaseList = new ArrayList<>();
    
    private List<ClientCase> currentClientCaseList = new ArrayList<>();
    
    private List<ClientCase> finishedClientCaseList = new ArrayList<>();
    
    private List<ClientCase> premiumClientCaseList = new ArrayList<>();
    
    private List<ClientCase> allClientCaseList = new ArrayList<>();
    
    private ClientCaseDAO caseDao = new ClientCaseDAO();

    private Converters converters = new Converters();
    
    private ClientCase selectedCase;
    
    private ClientCase lastSelectedCase;
    
    private ClientCase awaitingSelectedCase;
    
    private ClientCase currentSelectedCase;
    
    private ClientCase finishedSelectedCase;
    
    private ArrayList<Set<String>> modelsEmploymentType = new ArrayList<>();
    
    private ArrayList<Set<String>> modelsBranch = new ArrayList<>();
    
    private ArrayList<IncomeData> selectedCaseIncomeTable = new ArrayList<>();

    

    @PostConstruct
    public void fillTables()
    {
        reloadCases();
        reloadCases2();
        reloadCases3();
        reloadCases4();
        reloadCases5();
        reloadCases6();
    }
   
    public void reloadCases()
    {
        modelsEmploymentType = new ArrayList<>();
        modelsBranch = new ArrayList<>();
        clientCaseList = caseDao.last5CasesSelectedClient( loginMB.getClient().getIdClient() );
        
        if(!clientCaseList.isEmpty())
        {
            modelsEmploymentType.add( showAllClientsEmploymentTypes( clientCaseList.get(0).getClient() ) );
            modelsBranch.add( showAllClientsBranches( clientCaseList.get(0).getClient() ) );
        }
        System.out.println("Pobrano"); 
    }
        
    public void reloadCases2()
    {
        awaitingClientCaseList = caseDao.awaitingCasesSelectedClient( loginMB.getClient().getIdClient() );
        System.out.println("Pobrano"); 
    }
    
     public void reloadCases3()
    {     
        currentClientCaseList = caseDao.currentCasesSelectedClient( loginMB.getClient().getIdClient() );
        System.out.println("Pobrano"); 
    }
    
     public void reloadCases4()
    {
        finishedClientCaseList = caseDao.finishedCasesSelectedClient( loginMB.getClient().getIdClient() );
        System.out.println("Pobrano"); 
    }
     
     public void reloadCases5()
    {
        premiumClientCaseList = caseDao.premiumCasesSelectedClient( loginMB.getClient().getIdClient() );
        System.out.println("Pobrano"); 
    }
     
    public void reloadCases6()
    {
        allClientCaseList = caseDao.allActiveCasesSelectedClient( loginMB.getClient().getIdClient() );
        System.out.println("Pobrano"); 
    }
     
     public int countConsultantApplications(ClientCase cs)
    { 
        Set<Consultant> cons = cs.getConsultants();
        if (cons == null) {
            return 0;
        }
        else {
            return cons.size();
        }
    }   
        
     public Set<String> showAllClientsEmploymentTypes(Client client)
     {
        HashSet<String> types = new HashSet<>();
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
        HashSet<String> types = new HashSet<>();
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
            if (rds.getBik() == null) {
                return false;
            }
            else {
                return true;
            }   
        }   
    }
    
    public void rowDoubleClick(ClientCase cs) throws IOException
    {
        selectedCase = cs;
        System.out.println("2 razy: "  + selectedCase.getIdClientCase());
        FacesContext.getCurrentInstance().getExternalContext().redirect("clientCaseDetails.xhtml"); 
    }
    
    public void rowClick(ClientCase cs)
    {
        selectedCase = cs;
        System.out.println("Klik " + cs.getIdClientCase());
    }

//     public void fillSelectedCaseIncomeTable() {
//       
//        selectedCaseIncomeTable = new ArrayList<>();
//        
//        for (Income i : selectedCase.getClient().getIncomes())
//        {
//            selectedCaseIncomeTable.add(new IncomeData(i.getEmploymentType().getName(), i.getBranch().getName(), i.getMonthlyNetto().doubleValue()));
//        }
//        
//        for (IncomeBusinessActivity i : selectedCase.getClient().getIncomeBusinessActivities())
//        {
//            selectedCaseIncomeTable.add(new IncomeData(i.getEmploymentType().getName(), i.getBranch().getName(), i.getIncomeLastYearNetto().doubleValue()));
//        }
//        
//    }
     
     public String backOffAwaiting(){
         CaseStatusDAO csdao = new CaseStatusDAO();
         CaseStatus cs = csdao.read(8);
         awaitingSelectedCase.setCaseStatus(cs);
         caseDao.updateClientCase(awaitingSelectedCase);
         awaitingSelectedCase=null;
     return "/client/clientMainPage.xhtml";
     }
     
     public String backOffCurrent(){
         CaseStatusDAO csdao = new CaseStatusDAO();
         CaseStatus cs = csdao.read(8);
         currentSelectedCase.setCaseStatus(cs);
         caseDao.updateClientCase(currentSelectedCase);
         currentSelectedCase=null;
     return "/client/clientMainPage.xhtml";
     }
    
    public LoginMB getLoginMB() {
        return loginMB;
    }

    public void setLoginMB(LoginMB loginMB) {
        this.loginMB = loginMB;
    }

    public List<ClientCase> getClientCaseList() {
//        reloadCases();
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
//        reloadCases2();
        return awaitingClientCaseList;
    }

    public void setAwaitingClientCaseList(List<ClientCase> awaitingClientCaseList) {
        this.awaitingClientCaseList = awaitingClientCaseList;
    }

    public List<ClientCase> getCurrentClientCaseList() {
//        reloadCases3();
        return currentClientCaseList;
    }

    public void setCurrentClientCaseList(List<ClientCase> currentClientCaseList) {
        this.currentClientCaseList = currentClientCaseList;
    }

    public List<ClientCase> getFinishedClientCaseList() {
//        reloadCases4();
        return finishedClientCaseList;
    }

    public void setFinishedClientCaseList(List<ClientCase> finishedClientCaseList) {
        this.finishedClientCaseList = finishedClientCaseList;
    }

    public List<ClientCase> getPremiumClientCaseList() {
//        reloadCases5();
        return premiumClientCaseList;
    }

    public void setPremiumClientCaseList(List<ClientCase> premiumClientCaseList) {
        this.premiumClientCaseList = premiumClientCaseList;
    }

    public List<ClientCase> getAllClientCaseList() {
//        reloadCases6();
        return allClientCaseList;
    }

    public void setAllClientCaseList(List<ClientCase> allClientCaseList) {
        this.allClientCaseList = allClientCaseList;
    }

    public ClientCase getAwaitingSelectedCase() {
        return awaitingSelectedCase;
    }

    public void setAwaitingSelectedCase(ClientCase awaitingSelectedCase) {
        this.awaitingSelectedCase = awaitingSelectedCase;
    }

    public ClientCase getCurrentSelectedCase() {
        return currentSelectedCase;
    }

    public void setCurrentSelectedCase(ClientCase currentSelectedCase) {
        this.currentSelectedCase = currentSelectedCase;
    }

    public ClientCase getFinishedSelectedCase() {
        return finishedSelectedCase;
    }

    public void setFinishedSelectedCase(ClientCase finishedSelectedCase) {
        this.finishedSelectedCase = finishedSelectedCase;
    }

    public ClientCase getSelectedCase() {
        return selectedCase;
    }

    public void setSelectedCase(ClientCase selectedCase) {
        this.selectedCase = selectedCase;
    }

    public ClientCase getLastSelectedCase() {
        return lastSelectedCase;
    }

    public void setLastSelectedCase(ClientCase lastSelectedCase) {
        this.lastSelectedCase = lastSelectedCase;
    }

    
     
    
    
    
}