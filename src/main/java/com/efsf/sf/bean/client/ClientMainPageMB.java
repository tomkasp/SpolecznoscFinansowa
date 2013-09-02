package com.efsf.sf.bean.client;

import com.efsf.sf.bean.LoginMB;
import com.efsf.sf.bean.MessagesMB;
import com.efsf.sf.collection.IncomeData;
import com.efsf.sf.sql.dao.CaseStatusDAO;
import com.efsf.sf.sql.dao.ClientCaseDAO;
import com.efsf.sf.sql.dao.ClientDAO;
import com.efsf.sf.sql.dao.ConsultantRatingDAO;
import com.efsf.sf.sql.entity.*;
import com.efsf.sf.util.Algorithms;
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
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

@ManagedBean
@ViewScoped
public class ClientMainPageMB implements Serializable {
    private static final long serialVersionUID = 1L;

    @ManagedProperty(value="#{loginMB}")
    private LoginMB loginMB;
    
    @ManagedProperty(value="#{clientCaseMB}")
    private ClientCaseMB clientCaseMB;
    
    @ManagedProperty(value="#{messagesMB}")
    private MessagesMB messagesMB;
    
    private List<ClientCase> clientCaseList = new ArrayList<>();
    
    private List<ClientCase> awaitingClientCaseList = new ArrayList<>();
    
    private List<ClientCase> currentClientCaseList = new ArrayList<>();
    
    private List<ClientCase> finishedClientCaseList = new ArrayList<>();
    
    private List<ClientCase> premiumClientCaseList = new ArrayList<>();
    
    private List<ClientCase> allClientCaseList = new ArrayList<>();
    
    private List<ClientCase> awaitingForMarketClientCaseList = new ArrayList<>();
    
    private ClientCaseDAO caseDao = new ClientCaseDAO();

    private Converters converters = new Converters();
    
    private ClientCase selectedCase;
    
    private ClientCase lastSelectedCase;
    
    private ClientCase awaitingSelectedCase;
    
    private ClientCase currentSelectedCase;
    
    private ClientCase finishedSelectedCase;
    
    private ClientCase premiumSelectedCase;
    
    private ClientCase awaitingForMarketSelectedCase;
    
    private List<Set<String>> modelsEmploymentType = new ArrayList<>();
    private List<Set<String>> modelsBranch = new ArrayList<>();
    
    private List<IncomeData> selectedCaseIncomeTable = new ArrayList<>();
   
    private Integer newPoints;
    
    private boolean requirementsFulfilled = false;
    
    @PostConstruct
    public void makeModels()
    {
        modelsEmploymentType.add( showAllClientsEmploymentTypes( loginMB.getClient() ) );
        modelsBranch.add( showAllClientsBranches( loginMB.getClient() ) );
    }
    
    public void fillTables()
    {    
        
        finishedSelectedCase = null;
        awaitingSelectedCase = null;
        lastSelectedCase = null;
        premiumSelectedCase = null;
        awaitingForMarketSelectedCase = null;
        currentSelectedCase = null;
        selectedCase = null;
        
        reloadCases();
        reloadCases2();
        reloadCases3();
        reloadCases4();
        reloadCases5();
        reloadCases6();
        reloadCases7();
        messagesMB.loadUnreadMessages();
        checkRequirementsForNewApplication(); 
    }
   
    public void reloadCases()
    {
        clientCaseList = caseDao.last5CasesSelectedClient( loginMB.getClient().getIdClient() );
    }
        
    public void reloadCases2()
    {
        awaitingClientCaseList = caseDao.awaitingCasesSelectedClient( loginMB.getClient().getIdClient() );
    }
    
     public void reloadCases3()
    {     
        currentClientCaseList = caseDao.currentCasesSelectedClient( loginMB.getClient().getIdClient() );
    }
    
     public void reloadCases4()
    {
        finishedClientCaseList = caseDao.finishedCasesSelectedClient( loginMB.getClient().getIdClient() );
    }
     
     public void reloadCases5()
    {
        premiumClientCaseList = caseDao.premiumCasesSelectedClient( loginMB.getClient().getIdClient() );
    }
     
    public void reloadCases6()
    {
        allClientCaseList = caseDao.allActiveCasesSelectedClient( loginMB.getClient().getIdClient() );
    }
    
    public void reloadCases7()
    {
        awaitingForMarketClientCaseList = caseDao.awaitingForMarketClientCaseList( loginMB.getClient().getIdClient()) ;
    }
    
    public boolean isProfilNotFilled(){
        return Algorithms.calculateProgress(loginMB.getClient().getIdClient())<60;
    }
    
    public double giveMeConsultantAverage(int consultantId)
    {
        ConsultantRatingDAO crdao = new ConsultantRatingDAO();
        ConsultantRating cr = crdao.getConsultantRatings(consultantId);
        if(cr!=null){
        return cr.getAverage();
        }
        else{
        return 0;
        }
        
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
            return rds.getBik() == null;  
        }   
    }
    
    public void reload()
    {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        if (!facesContext.isValidationFailed() && !facesContext.isPostback())
        {

            fillTables();
        }
    }
    
    public void rowDoubleClick(ClientCase cs) throws IOException
    {         
        FacesContext.getCurrentInstance().getExternalContext().redirect("clientCaseDetails.xhtml?clientCaseId=" + cs.getIdClientCase()  ); 
    }
    
    
    
     public void backOffClientCaseList(){
         CaseStatusDAO csdao = new CaseStatusDAO();
         CaseStatus cs = csdao.read(8);
         lastSelectedCase.setCaseStatus(cs);
         caseDao.updateClientCase(lastSelectedCase);
         lastSelectedCase=null;
         
         fillTables();
     }
    
     public void backOffAwaiting(){
         CaseStatusDAO csdao = new CaseStatusDAO();
         CaseStatus cs = csdao.read(8);
         awaitingSelectedCase.setCaseStatus(cs);
         caseDao.updateClientCase(awaitingSelectedCase);
         awaitingSelectedCase=null;
         
         fillTables();
     }
     
     public void backOffCurrent(){
         CaseStatusDAO csdao = new CaseStatusDAO();
         CaseStatus cs = csdao.read(8);
         currentSelectedCase.setCaseStatus(cs);
         caseDao.updateClientCase(currentSelectedCase);
         currentSelectedCase=null;
         
         reloadCases3();
         reloadCases4();

     }
     
     public void redirectNewApplication() throws IOException
     {
         if (requirementsFulfilled)
         {
              FacesContext.getCurrentInstance().getExternalContext().redirect("clientNewApplication.xhtml");
         }
     }
     
     public void checkRequirementsForNewApplication()
     {
         ClientDAO clientDao = new ClientDAO();
         Client client = clientDao.checkClientForNewApplication(loginMB.getClient().getIdClient());
         
         if (client == null || client.getAddresses() == null || client.getAddresses().isEmpty() || client.getBirthDate() == null || ((Address) client.getAddresses().toArray()[0]).getZipCode().equals("") || ((Address) client.getAddresses().toArray()[0]).getRegion().getIdRegion().equals(0) || (client.getIncomeBusinessActivities() == null && client.getIncomes() == null ))
         {
             requirementsFulfilled = false;
         }
         else
         {
             requirementsFulfilled = true;
         }
     }
    //IF THERE WILL BE VIEWED CASE BEAN SOMEDAY THIS SHOULD BE COPIED THERE  //TODO
    public List<ClientCase> castClientCaseSetToArray(Set<ClientCase> csSet)
    {
        return new ArrayList(csSet);
    }
    
    public void addPoints(){
        Client currentClient=loginMB.getClient();
        int currentPoints=currentClient.getPoints();
        currentClient.setPoints(currentPoints+newPoints);
        ClientDAO cdao=new ClientDAO();
        cdao.update(currentClient);   
        newPoints=null;
    }
    
    public LoginMB getLoginMB() {
        return loginMB;
    }

    public void setLoginMB(LoginMB loginMB) {
        this.loginMB = loginMB;
    }

    public List<ClientCase> getClientCaseList() {
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


    public List<Set<String>> getModelsEmploymentType() {
        return modelsEmploymentType;
    }

    public void setModelsEmploymentType(List<Set<String>> modelsEmploymentType) {
        this.modelsEmploymentType = modelsEmploymentType;
    }

    public List<Set<String>> getModelsBranch() {
        return modelsBranch;
    }

    public void setModelsBranch(List<Set<String>> modelsBranch) {
        this.modelsBranch = modelsBranch;
    }

    public Converters getConverters() {
        return converters;
    }

    public void setConverters(Converters converters) {
        this.converters = converters;
    }

    public List<IncomeData> getSelectedCaseIncomeTable() {
        return selectedCaseIncomeTable;
    }

    public void setSelectedCaseIncomeTable(List<IncomeData> selectedCaseIncomeTable) {
        this.selectedCaseIncomeTable = selectedCaseIncomeTable;
    }

    public List<ClientCase> getAwaitingClientCaseList() {
        return awaitingClientCaseList;
    }

    public void setAwaitingClientCaseList(List<ClientCase> awaitingClientCaseList) {
        this.awaitingClientCaseList = awaitingClientCaseList;
    }

    public List<ClientCase> getCurrentClientCaseList() {
        return currentClientCaseList;
    }

    public void setCurrentClientCaseList(List<ClientCase> currentClientCaseList) {
        this.currentClientCaseList = currentClientCaseList;
    }

    public List<ClientCase> getFinishedClientCaseList() {
        return finishedClientCaseList;
    }

    public void setFinishedClientCaseList(List<ClientCase> finishedClientCaseList) {
        this.finishedClientCaseList = finishedClientCaseList;
    }

    public List<ClientCase> getPremiumClientCaseList() {
        return premiumClientCaseList;
    }

    public void setPremiumClientCaseList(List<ClientCase> premiumClientCaseList) {
        this.premiumClientCaseList = premiumClientCaseList;
    }

    public List<ClientCase> getAllClientCaseList() {
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

    public ClientCaseMB getClientCaseMB() {
        return clientCaseMB;
    }

    public void setClientCaseMB(ClientCaseMB clientCaseMB) {
        this.clientCaseMB = clientCaseMB;
    }


    public void setNewPoints(Integer newPoints) {
        this.newPoints = newPoints;
    }

    public Integer getNewPoints() {
        return newPoints;
    }

    public boolean isRequirementsFulfilled() {
        return requirementsFulfilled;
    }

    public void setRequirementsFulfilled(boolean requirementsFulfilled) {
        this.requirementsFulfilled = requirementsFulfilled;
    }

    public MessagesMB getMessagesMB() {
        return messagesMB;
    }

    public void setMessagesMB(MessagesMB messagesMB) {
        this.messagesMB = messagesMB;
    }

    public ClientCase getPremiumSelectedCase() {
        return premiumSelectedCase;
    }

    public void setPremiumSelectedCase(ClientCase premiumSelectedCase) {
        this.premiumSelectedCase = premiumSelectedCase;
    }

    public List<ClientCase> getAwaitingForMarketClientCaseList() {
        return awaitingForMarketClientCaseList;
    }

    public void setAwaitingForMarketClientCaseList(List<ClientCase> awaitingForMarketClientCaseList) {
        this.awaitingForMarketClientCaseList = awaitingForMarketClientCaseList;
    }

    public ClientCase getAwaitingForMarketSelectedCase() {
        return awaitingForMarketSelectedCase;
    }

    public void setAwaitingForMarketSelectedCase(ClientCase awaitingForMarketSelectedCase) {
        this.awaitingForMarketSelectedCase = awaitingForMarketSelectedCase;
    }
    
    
    
}
