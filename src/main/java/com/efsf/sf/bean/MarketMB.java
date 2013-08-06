/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.efsf.sf.bean;

import com.efsf.sf.collection.IncomeData;
import com.efsf.sf.sql.dao.ClientCaseDAO;
import com.efsf.sf.sql.dao.ConsultantDAO;
import com.efsf.sf.sql.entity.Client;
import com.efsf.sf.sql.entity.ClientCase;
import com.efsf.sf.sql.entity.Consultant;
import com.efsf.sf.sql.entity.Income;
import com.efsf.sf.sql.entity.IncomeBusinessActivity;
import com.efsf.sf.sql.entity.RequiredDocuments;
import com.efsf.sf.util.Converters;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author XaI
 */
@ManagedBean
@SessionScoped
public class MarketMB implements Serializable
{
    
    @ManagedProperty(value="#{loginMB}")
    private LoginMB loginMB;
    
    
    private List<ClientCase> clientCaseList = new ArrayList();
    
    private Converters converters =  new Converters();
      
    ClientCaseDAO caseDao = new ClientCaseDAO();
   
    private ClientCase selectedCase;
    private ClientCase selectedLastCase;
    private ClientCase selectedObservedCase;
    private ClientCase selectedAppliedCase;
    
    private boolean alreadyApplied = false;
    private boolean alreadyObserved = false;
    
    ArrayList<Set<String>> modelsEmploymentType = new ArrayList();
    private ArrayList<Set<String>> modelsBranch = new ArrayList();
    
    private ArrayList<Set<String>> observedModelsEmploymentType = new ArrayList();
    private ArrayList<Set<String>> observedModelsBranch = new ArrayList();
    
    private ArrayList<Set<String>> appliedModelsEmploymentType = new ArrayList();
    private ArrayList<Set<String>> appliedModelsBranch = new ArrayList();
    
    private ArrayList<IncomeData> selectedCaseIncomeTable = new ArrayList<IncomeData>();

    //MARKET VIEW FIELDS!
    
    private int phaseMin = 0;
    private int phaseMax = 100;
    
    private int ageMin = 0; 
    private int ageMax = 99;
    
    private int difficultyMin = 0;
    private int difficultyMax = 10;
    
    private int branchId = 0;
    private int regionId = 0;
    
    private ArrayList<Integer> incomeIds = new ArrayList<Integer>(Arrays.asList(1,2,3,4,5,6,7,8));
    private ArrayList<Integer> businessIds = new ArrayList<Integer>(Arrays.asList(9,10,11,12,13));
    
    List<ClientCase> allMarket = new ArrayList();
    
    public MarketMB()
    {
        reloadCases(); 
    }
    
    
    @PostConstruct
    public void fillModels()
    {
        makeObservedModels();
        makeAppliedModels();
    }
    
    public void makeObservedModels()
    {
        Set<ClientCase> cs = loginMB.getConsultant().getClientCases_2();
        
        Iterator<ClientCase> i = cs.iterator();
        
        while (i.hasNext())
        {
            Client client = i.next().getClient();
            observedModelsEmploymentType.add(showAllClientsEmploymentTypes(client));
            observedModelsBranch.add(showAllClientsBranches(client));  
        }
    }
    
    public void makeAppliedModels()
    {
        Set<ClientCase> cs = loginMB.getConsultant().getClientCases();
        
        Iterator<ClientCase> i = cs.iterator();
        
        while (i.hasNext())
        {
            Client client = i.next().getClient();
            appliedModelsEmploymentType.add(showAllClientsEmploymentTypes(client));
            appliedModelsBranch.add(showAllClientsBranches(client));  
        }
    }
            
    
    public void rowDoubleClick() throws IOException
    {
        System.out.println("2 razy: "  + selectedCase.getIdClientCase());
        fillSelectedCaseIncomeTable();
        FacesContext.getCurrentInstance().getExternalContext().redirect("consultantCaseDetails.xhtml"); 
    }
    
    public void rowClick(ClientCase cs)
    {
        selectedCase = cs;
        System.out.println("Klik " + cs.getIdClientCase());
    }
   
    
    public ArrayList<ClientCase> castClientCaseSetToArray(Set<ClientCase> csSet)
    {
        return new ArrayList<ClientCase>(csSet);
    }
    
    public void reloadCases()
    {
        modelsEmploymentType = new ArrayList();
        modelsBranch= new ArrayList();   
        clientCaseList =  caseDao.last5Cases();
        for (int i = 0; i<clientCaseList.size(); i++)
        {
            modelsEmploymentType.add(showAllClientsEmploymentTypes(clientCaseList.get(i).getClient()));
            modelsBranch.add(showAllClientsBranches(clientCaseList.get(i).getClient()));
        }

        System.out.println("Pobrano"); 
    }
    
    public void pollData()
    {
        selectedCase = null;
        selectedLastCase = null;
        selectedObservedCase = null;
        selectedAppliedCase = null;
        
         // IT COULD BE BETTER TO JUST UPDATE CASES CONNECTED TO THE CONSULTANT NOT THE WHOLE CONSULTANT //TODO
         loginMB.setConsultant((new ConsultantDAO()).getCounsultantConnectedToUser(loginMB.getIdUser()));
         reloadCases();
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
    
    public void traceCase()
    {
        Consultant consultant = loginMB.getConsultant();
        ConsultantDAO consultantDao;
        if (!caseDao.doesConsultantObserveCase(consultant.getIdConsultant(),selectedLastCase.getIdClientCase()))
        {
            alreadyObserved=false;
            if (!caseDao.doesConsultantAppliedToCase(consultant.getIdConsultant(), selectedLastCase.getIdClientCase()))
            {
                 consultant.getClientCases_2().add(selectedLastCase);

            }
            else
            {
                 for (ClientCase cc : consultant.getClientCases())
                 {
                     if (cc.getIdClientCase().equals(selectedLastCase.getIdClientCase()))
                     {
                         consultant.getClientCases_2().add(cc);
                     }
                 }
            }
            consultantDao = new ConsultantDAO();
            consultantDao.update(consultant);
            observedModelsEmploymentType = new ArrayList();
            observedModelsBranch = new ArrayList();
            makeObservedModels();
        }
        else
        {
            alreadyObserved = true;
        }
    }
    
    public void applyToCase()
    {
        Consultant consultant = loginMB.getConsultant();
   
        if (!caseDao.doesConsultantAppliedToCase(consultant.getIdConsultant(),selectedLastCase.getIdClientCase()))
        {     
            alreadyApplied = false;
            if (!caseDao.doesConsultantObserveCase(consultant.getIdConsultant(), selectedLastCase.getIdClientCase()))
            {
                consultant.getClientCases().add(selectedLastCase);
            }
            else
            {
                 for (ClientCase cc : consultant.getClientCases_2())
                 {
                     if (cc.getIdClientCase().equals(selectedLastCase.getIdClientCase()))
                     {
                         consultant.getClientCases().add(cc);
                     }
                 }
            }
             
            ConsultantDAO consultantDao = new ConsultantDAO();
            consultantDao.update(consultant);
            
            loginMB.setConsultant(consultantDao.getCounsultantConnectedToUser(loginMB.getIdUser()));
            
            appliedModelsEmploymentType = new ArrayList();
            appliedModelsBranch = new ArrayList();
            makeAppliedModels();
        }
        else
        {
            alreadyApplied = true;
        }
    }
    
    public void applyToObservedCase()
    {
        Consultant consultant = loginMB.getConsultant();
        
        if (!caseDao.doesConsultantAppliedToCase(consultant.getIdConsultant(),selectedObservedCase.getIdClientCase()))
        {
            alreadyApplied = false;
            consultant.getClientCases().add(selectedObservedCase);
            ConsultantDAO consultantDao = new ConsultantDAO();
            consultantDao.update(consultant);
            
            loginMB.setConsultant(consultantDao.getCounsultantConnectedToUser(loginMB.getIdUser()));
            
            appliedModelsEmploymentType = new ArrayList();
            appliedModelsBranch = new ArrayList();
            makeAppliedModels();
        }
        else
        {
            alreadyApplied = true;
        }
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
    
    public void stopObserve()
    {
        ConsultantDAO consultantDao = new ConsultantDAO();
        for (ClientCase cc : loginMB.getConsultant().getClientCases_2())
        {
            if (cc.getIdClientCase() == selectedObservedCase.getIdClientCase())
            {
                loginMB.getConsultant().getClientCases_2().remove(cc);
                consultantDao.update(loginMB.getConsultant());
                selectedObservedCase = null;
                break;
            }
        }
    }
    
    public void revokeApplication()
    {
        ConsultantDAO consultantDao = new ConsultantDAO();
        for (ClientCase cc : loginMB.getConsultant().getClientCases())
        {
            if (cc.getIdClientCase() == selectedAppliedCase.getIdClientCase())
            {
                loginMB.getConsultant().getClientCases().remove(cc);
                consultantDao.update(loginMB.getConsultant());
                selectedAppliedCase = null;
                break;
            }
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

    public ArrayList<IncomeData> getSelectedCaseIncomeTable() {
        return selectedCaseIncomeTable;
    }

    public void setSelectedCaseIncomeTable(ArrayList<IncomeData> selectedCaseIncomeTable) {
        this.selectedCaseIncomeTable = selectedCaseIncomeTable;
    }

    public LoginMB getLoginMB() {
        return loginMB;
    }

    public void setLoginMB(LoginMB loginMB) {
        this.loginMB = loginMB;
    }

    public ArrayList<Set<String>> getObservedModelsEmploymentType() {
        return observedModelsEmploymentType;
    }

    public void setObservedModelsEmploymentType(ArrayList<Set<String>> observedModelsEmploymentType) {
        this.observedModelsEmploymentType = observedModelsEmploymentType;
    }

    public ArrayList<Set<String>> getObservedModelsBranch() {
        return observedModelsBranch;
    }

    public void setObservedModelsBranch(ArrayList<Set<String>> observedModelsBranch) {
        this.observedModelsBranch = observedModelsBranch;
    }

    public ClientCase getSelectedObservedCase() {
        return selectedObservedCase;
    }

    public void setSelectedObservedCase(ClientCase selectedObservedCase) {
        this.selectedObservedCase = selectedObservedCase;
    }

    public ArrayList<Set<String>> getAppliedModelsEmploymentType() {
        return appliedModelsEmploymentType;
    }

    public void setAppliedModelsEmploymentType(ArrayList<Set<String>> appliedModelsEmploymentType) {
        this.appliedModelsEmploymentType = appliedModelsEmploymentType;
    }

    public ArrayList<Set<String>> getAppliedModelsBranch() {
        return appliedModelsBranch;
    }

    public void setAppliedModelsBranch(ArrayList<Set<String>> appliedModelsBranch) {
        this.appliedModelsBranch = appliedModelsBranch;
    }

    public ClientCase getSelectedLastCase() {
        return selectedLastCase;
    }

    public void setSelectedLastCase(ClientCase selectedLastCase) {
        this.selectedLastCase = selectedLastCase;
    }

    public boolean isAlreadyApplied() {
        return alreadyApplied;
    }

    public void setAlreadyApplied(boolean alreadyApplied) {
        this.alreadyApplied = alreadyApplied;
    }

    public boolean isAlreadyObserved() {
        return alreadyObserved;
    }

    public void setAlreadyObserved(boolean alreadyObserved) {
        this.alreadyObserved = alreadyObserved;
    }

    public ClientCase getSelectedAppliedCase() {
        return selectedAppliedCase;
    }

    public void setSelectedAppliedCase(ClientCase selectedAppliedCase) {
        this.selectedAppliedCase = selectedAppliedCase;
    }

    public int getPhaseMin() {
        return phaseMin;
    }

    public void setPhaseMin(int phaseMin) {
        this.phaseMin = phaseMin;
    }

    public int getPhaseMax() {
        return phaseMax;
    }

    public void setPhaseMax(int phaseMax) {
        this.phaseMax = phaseMax;
    }

    public int getAgeMin() {
        return ageMin;
    }

    public void setAgeMin(int ageMin) {
        this.ageMin = ageMin;
    }

    public int getAgeMax() {
        return ageMax;
    }

    public void setAgeMax(int ageMax) {
        this.ageMax = ageMax;
    }

    public int getDifficultyMin() {
        return difficultyMin;
    }

    public void setDifficultyMin(int difficultyMin) {
        this.difficultyMin = difficultyMin;
    }

    public int getDifficultyMax() {
        return difficultyMax;
    }

    public void setDifficultyMax(int difficultyMax) {
        this.difficultyMax = difficultyMax;
    }

    public ArrayList<Integer> getIncomeIds() {
        return incomeIds;
    }

    public void setIncomeIds(ArrayList<Integer> incomeIds) {
        this.incomeIds = incomeIds;
    }

    public int getBranchId() {
        return branchId;
    }

    public void setBranchId(int branchId) {
        this.branchId = branchId;
    }

    public int getRegionId() {
        return regionId;
    }

    public void setRegionId(int regionId) {
        this.regionId = regionId;
    }

    public ArrayList<Integer> getBusinessIds() {
        return businessIds;
    }

    public void setBusinessIds(ArrayList<Integer> businessIds) {
        this.businessIds = businessIds;
    }
    
    
    
}
