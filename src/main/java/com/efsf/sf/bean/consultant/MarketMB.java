package com.efsf.sf.bean.consultant;

import com.efsf.sf.bean.DictionaryMB;
import com.efsf.sf.bean.LoginMB;
import com.efsf.sf.bean.client.ClientCaseMB;
import com.efsf.sf.sql.dao.ClientCaseDAO;
import com.efsf.sf.sql.entity.ClientCase;
import com.efsf.sf.sql.entity.EmploymentType;
import com.efsf.sf.util.Settings;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;


@ManagedBean
@SessionScoped
public class MarketMB implements Serializable
{
    
    @ManagedProperty(value="#{loginMB}")
    private LoginMB loginMB;
    
    @ManagedProperty(value="#{dictionaryMB}")
    private DictionaryMB dictionaryMB;
    
    @ManagedProperty(value="#{clientCaseMB}")
    private ClientCaseMB clientCaseMB;
    
    @ManagedProperty(value="#{consultantMainPageMB}")
    private ConsultantMainPageMB consultantMainPageMB;
       
    private ClientCaseDAO caseDao = new ClientCaseDAO(); 
   
    //MARKET VIEW FIELDS!
    
    private int phaseMin = 0;
    private int phaseMax = 100;
    
    private int ageMin = 0; 
    private int ageMax = 99;
    
    private int difficultyMin = 0;
    private int difficultyMax = 10;
    
    private int branchId = 0;
    private int regionId = 0;
    
    private List<String> incomeIds = new ArrayList();
    private List<String> businessIds = new ArrayList();

    private List<Set<String>> marketModelsEmploymentType = new ArrayList();
    private List<Set<String>> marketModelsBranch = new ArrayList();
    
    private List<ClientCase> allMarket = new ArrayList();
    
    private ClientCase selectedMarketCase;

    public void loadMarket()
    {
        selectedMarketCase = null;
        marketModelsEmploymentType = new ArrayList();
        marketModelsBranch= new ArrayList();
        
        //WE DO NOT ASSUME THAT ALL THE SUBSCRIPTION TYPES HIGHER THEN 'PREMIUM (3)' HAVE AT LEAST PREMIUM ACCESS RIGHTS 
        if (loginMB.returnConsultantAccessRights().equals(Settings.PREMIUM))
        {
            allMarket = caseDao.getCasesWithMarketFilter(phaseMin,phaseMax,ageMin,ageMax,difficultyMin,difficultyMax,branchId,regionId,incomeIds,businessIds);
        }
        else if(loginMB.returnConsultantAccessRights().equals(Settings.FREE))
        {
            allMarket = new ArrayList();
        } 
       // WE ASSUME THAT IF ACCOUNT IS NOT FREE THEN IT HAVE ATLEASE STANDARD RIGHTS 
        else
        {
            allMarket = caseDao.lastCases(Settings.MAX_CASES_DISPLAYED);
        }
        for (int i = 0; i<allMarket.size(); i++)
        {
            marketModelsEmploymentType.add(consultantMainPageMB.showAllClientsEmploymentTypes(allMarket.get(i).getClient()));
            marketModelsBranch.add(consultantMainPageMB.showAllClientsBranches(allMarket.get(i).getClient()));
        }
    }
    
    
    
    @PostConstruct
    public void resetMarket()
    {
        phaseMin = 0;
        phaseMax = 100;
        ageMin = 0;
        ageMax = 99;
    
        difficultyMin = 0;
        difficultyMax = 10;
    
         branchId = 0;
         regionId = 0;
         
         incomeIds = new ArrayList();
         businessIds = new ArrayList();   
         
         for (EmploymentType et : dictionaryMB.getIncome()) {
            incomeIds.add(et.getShortcut());
         }
         for (EmploymentType ba : dictionaryMB.getBusinessActivity()) {
            businessIds.add(ba.getShortcut());  
         }
         loadMarket();
         
    }

    
    public void unselectEmployment()
    {
        businessIds = new ArrayList();
        incomeIds = new ArrayList();
    }
     
    
    

    public LoginMB getLoginMB() {
        return loginMB;
    }

    public void setLoginMB(LoginMB loginMB) {
        this.loginMB = loginMB;
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

    public List<String> getIncomeIds() {
        return incomeIds;
    }

    public void setIncomeIds(List<String> incomeIds) {
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

    public List<String> getBusinessIds() {
        return businessIds;
    }

    public void setBusinessIds(List<String> businessIds) {
        this.businessIds = businessIds;
    }

    public List<ClientCase> getAllMarket() {
        return allMarket;
    }

    public void setAllMarket(List<ClientCase> allMarket) {
        this.allMarket = allMarket;
    }

    public List<Set<String>> getMarketModelsEmploymentType() {
        return marketModelsEmploymentType;
    }

    public void setMarketModelsEmploymentType(List<Set<String>> marketModelsEmploymentType) {
        this.marketModelsEmploymentType = marketModelsEmploymentType;
    }

    public List<Set<String>> getMarketModelsBranch() {
        return marketModelsBranch;
    }

    public void setMarketModelsBranch(List<Set<String>> marketModelsBranch) {
        this.marketModelsBranch = marketModelsBranch;
    }

    public DictionaryMB getDictionaryMB() {
        return dictionaryMB;
    }

    public void setDictionaryMB(DictionaryMB dictionaryMB) {
        this.dictionaryMB = dictionaryMB;
    }

    public ClientCase getSelectedMarketCase() {
        return selectedMarketCase;
    }

    public void setSelectedMarketCase(ClientCase selectedMarketCase) {
        this.selectedMarketCase = selectedMarketCase;
    }

    public ClientCaseMB getClientCaseMB() {
        return clientCaseMB;
    }

    public void setClientCaseMB(ClientCaseMB clientCaseMB) {
        this.clientCaseMB = clientCaseMB;
    }

    public ConsultantMainPageMB getConsultantMainPageMB() {
        return consultantMainPageMB;
    }

    public void setConsultantMainPageMB(ConsultantMainPageMB consultantMainPageMB) {
        this.consultantMainPageMB = consultantMainPageMB;
    }

}
