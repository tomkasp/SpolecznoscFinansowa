/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.efsf.sf.bean.client;

import com.efsf.sf.bean.DictionaryMB;
import com.efsf.sf.bean.LoginMB;
import com.efsf.sf.bean.MessagesMB;
import com.efsf.sf.collection.IncomeData;
import com.efsf.sf.sql.dao.*;
import com.efsf.sf.sql.entity.*;
import java.io.Serializable;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

@ManagedBean
@SessionScoped
public class ClientCaseMB implements Serializable {

    @ManagedProperty(value = "#{loginMB}")
    private LoginMB login;
    
    @ManagedProperty(value = "#{dictionaryMB}")
    private DictionaryMB dictionaryMB;
    
    @ManagedProperty(value = "#{messagesMB}")
    private MessagesMB messagesMB;
    
    @ManagedProperty("#{msg}")
    private ResourceBundle bundle;

        
    private int idTypProduktu;
    private int idTypProduktuObligation;
    
    private ClientCase clientCase = new ClientCase();
    private Date currentDate = new Date();
    private Obligation obligation = new Obligation();
    private ProductTypeDAO ptd = new ProductTypeDAO();
    private List<Obligation> obligationList = new ArrayList<>();
    ObligationDAO obdao = new ObligationDAO();
    
    
    private int premium = 6;
    
    // VIEW CASE DETAILS FIELDS 
    private ClientCase selectedClientCase;
    
    private Consultant selectedConsultant;
    
    private Obligation selectedObligation;
    
    private ArrayList<CaseStatus> statusModel = new ArrayList();
    
    private int caseStatusID;
    
    private ArrayList<IncomeData> selectedCaseIncomeTable = new ArrayList<IncomeData>();
    
    
    /**
     * Creates a new instance of ClientCaseMB
     */

    public ClientCaseMB(){
        
    }    
    
    @PostConstruct
    public void setModels()
    {
        for (CaseStatus cs : dictionaryMB.getCaseStatus())
        {
            if(cs.getIdCaseStatus() >= 2 && cs.getIdCaseStatus() != 8)
            {
                statusModel.add(cs);
            }
        }
    }
    
    public List<Obligation> retrieveObligationListForCurrentClient(){ 
        
        setObligationList(obdao.obligationListForClient(login.getClient().getIdClient()));
        
        return obligationList;
    }
   
  public void delObligation(){
        //System.out.println("zaznaczone zobowiazanie: "+selectedObligation.getName());
        obdao.deleteObligation(selectedObligation);
        //obligation = new Obligation();
        
    }
    
    //zwraca liste zobowiazan dla danego klienta w sesji
    public void addObligation(){
        //RequestContext.getCurrentInstance().execute("zobDial.show()");
        
        obligation.setClient(login.getClient());
        obligation.setProductType(ptd.getProductType(idTypProduktuObligation));
        obdao.save(obligation);
        System.out.println("wykonano save....");
        obligation = new Obligation();
        System.out.println("data:"+ obligation.getBeginDate());
    }
    
    public void addMessage(){ 
        
       if(clientCase.getPremium()){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Zabiore Ci " + premium + " punktów!"));  
       }
        
    }
    
    public Boolean premiumPointsChecking(){
        
        if(login.getClient().getPoints() < premium){
            return true;
        }
        else{
            return false;
        }
    }
    
    
    public String addCase(){
        if (login.getClient().getPoints() > 0) {
            ClientCaseDAO ccd = new ClientCaseDAO();
            
            ClientDAO cd = new ClientDAO();

            
            if(clientCase.getPremium()==null){
                clientCase.setPremium(false);
            }
                
            //pamietac o zabraniu punktow z klienta!
            if(clientCase.getPremium()){
                cd.decrementPoints(login.getClient(),premium);
                login.getClient().setPoints(login.getClient().getPoints() - premium);
            }
            else{
                cd.decrementPoints(login.getClient(),1);
                login.getClient().setPoints(login.getClient().getPoints() - 1);
            }
            
            //ucinanie do dwoch miejsc po przecinku bez zaokrlaglania!
            clientCase.setConsolidationValue(clientCase.getConsolidationValue().setScale(2,RoundingMode.DOWN));
            clientCase.setFreeResourcesValue(clientCase.getFreeResourcesValue().setScale(2,RoundingMode.DOWN));
            clientCase.setExpectedInstalment(clientCase.getExpectedInstalment().setScale(2,RoundingMode.DOWN));
            
            clientCase.setProductType(ptd.getProductType(idTypProduktu));
            clientCase.setClient(login.getClient());
            clientCase.setPhase(1);
            clientCase.setViewCounter(0);
            clientCase.setDifficulty(0);
            ccd.saveClientCase(clientCase);

            if(login.getClient().getPoints()==0){
                login.setActiveAddingApp(false);
            }
            clientCase = new ClientCase();
            
        }
        return "/client/clientMainPage.xhtml?faces-redirect=true";
    }
    
    public void changeCaseStatus()
    {
        CaseStatus cs = new CaseStatusDAO().read(caseStatusID);
        selectedClientCase.setCaseStatus(cs);
        new ClientCaseDAO().updateClientCase(selectedClientCase);
    }
    
    // VIEW CASE METHODS 
    public void loadCaseConsultantsDetails()
    {   FacesContext facesContext = FacesContext.getCurrentInstance();
         if (!facesContext.isPostback() && !facesContext.isValidationFailed())
         {
            selectedClientCase = new ClientCaseDAO().getClientCaseWithConsultantDetails(selectedClientCase.getIdClientCase());
            selectedConsultant = null;
         }
    }
    
    public void loadCaseClientsDetails()
    {   FacesContext facesContext = FacesContext.getCurrentInstance();
        if (!facesContext.isValidationFailed())
        {
             ClientCaseDAO cdao = new ClientCaseDAO();
             selectedClientCase = cdao.getClientCaseWithClientDetails(selectedClientCase.getIdClientCase());
             caseStatusID = selectedClientCase.getCaseStatus().getIdCaseStatus();
             selectedClientCase.setViewCounter(selectedClientCase.getViewCounter()+ 1);      
             cdao.updateClientCase(selectedClientCase);
             fillSelectedCaseIncomeTable();
             System.out.println("HAHA");
        }
    }
    
    public void fillSelectedCaseIncomeTable() {
       
        selectedCaseIncomeTable = new ArrayList();
        
        Client client = new ClientDAO().getClientWithIncomes(getSelectedClientCase().getClient().getIdClient());
        
        for (Income i : client.getIncomes())
        {
            selectedCaseIncomeTable.add(new IncomeData(i.getEmploymentType().getName(), i.getBranch().getName(), i.getMonthlyNetto().doubleValue()));
        }
        
        for (IncomeBusinessActivity i : client.getIncomeBusinessActivities())
        {
            selectedCaseIncomeTable.add(new IncomeData(i.getEmploymentType().getName(), i.getBranch().getName(), i.getIncomeLastYearNetto().doubleValue()));
        }
    }
    
    
    
    
    public ArrayList<Consultant> castConsultantSetToArray(Set<Consultant> cSet)
    {
        return new ArrayList<Consultant>(cSet);
    }
    
    public boolean checkLoggedConsultantAccessToCase()
    {
        ClientCaseDAO caseDao = new ClientCaseDAO();
        List list = caseDao.getSelectedCaseWithConsultant(selectedClientCase.getIdClientCase(), login.getConsultant().getIdConsultant());
        if (list != null && list.size() > 0)
            return true;
        else
            return false;                   
    }
    
    public void consultantAcceptPremium(ClientCase cc)
    {
        cc.setConsultants(null);
        cc.setConsultants_1(null);
        cc.setCaseStatus(new CaseStatusDAO().read(2));
        
        messagesMB.generateSystemMessage(bundle.getString("CONSULTANT_ACCEPT_PREMIUM"), cc.getClient().getUser().getIdUser(), new Object[] {login.getConsultant().getIdConsultant(), cc.getIdClientCase()});
        
        new ClientCaseDAO().updateClientCase(cc);
    }
    
    public void consultantRevokePremium(ClientCase cc)
    {
        cc.setConsultant(null);
        
        messagesMB.generateSystemMessage(bundle.getString("CONSULTANT_REVOKE_PREMIUM"), cc.getClient().getUser().getIdUser(), new Object[] {login.getConsultant().getIdConsultant(), cc.getIdClientCase()});
     
        new ClientCaseDAO().updateClientCase(cc);
    }
    
    public void assignConsultant()
    {
        ClientCaseDAO caseDao = new ClientCaseDAO();
        CaseStatusDAO statusDao = new CaseStatusDAO();
        selectedClientCase.setConsultant(selectedConsultant);
        selectedClientCase.setCaseStatus(statusDao.read(2));
        selectedClientCase.setConsultants(null);
        selectedClientCase.setConsultants_1(null);
        caseDao.updateClientCase(selectedClientCase);  
        
        messagesMB.generateSystemMessage(bundle.getString("CLIENT_SELECTED_CONSULTANT"), selectedConsultant.getUser().getIdUser(), new Object[] {login.getClient().getIdClient(), selectedClientCase.getIdClientCase()});
     
    }

    public Date getCurrentDate() {
        return currentDate;
    }

    public ClientCase getClientCase() {
        return clientCase;
    }

    public void setClientCase(ClientCase clientCase) {
        this.clientCase = clientCase;
    }

    public LoginMB getLogin() {
        return login;
    }

    public void setLogin(LoginMB login) {
        this.login = login;
    }

    public int getIdTypProduktu() {
        return idTypProduktu;
    }

    public void setIdTypProduktu(int idTypProduktu) {
        this.idTypProduktu = idTypProduktu;
    }

    public Obligation getObligation() {
        return obligation;
    }

    public void setObligation(Obligation obligation) {
        this.obligation = obligation;
    }

    public int getIdTypProduktuObligation() {
        return idTypProduktuObligation;
    }

    public void setIdTypProduktuObligation(int idTypProduktuObligation) {
        this.idTypProduktuObligation = idTypProduktuObligation;
    }

    
    public List<Obligation> getObligationList() {
        return obligationList;
    }

    public void setObligationList(List<Obligation> obligationList) {
        this.obligationList = obligationList;
    }

    public ClientCase getSelectedClientCase() {
        return selectedClientCase;
    }

    public void setSelectedClientCase(ClientCase selectedClientCase) {
        this.selectedClientCase = selectedClientCase;
    }

    public Consultant getSelectedConsultant() {
        return selectedConsultant;
    }

    public void setSelectedConsultant(Consultant selectedConsultant) {
        this.selectedConsultant = selectedConsultant;
    }

    public Obligation getSelectedObligation() {
        return selectedObligation;
    }

    public void setSelectedObligation(Obligation selectedObligation) {
        this.selectedObligation = selectedObligation;
    }

    public ArrayList<CaseStatus> getStatusModel() {
        return statusModel;
    }

    public void setStatusModel(ArrayList<CaseStatus> statusModel) {
        this.statusModel = statusModel;
    }

    public DictionaryMB getDictionaryMB() {
        return dictionaryMB;
    }

    public void setDictionaryMB(DictionaryMB dictionaryMB) {
        this.dictionaryMB = dictionaryMB;
    }

    public int getCaseStatusID() {
        return caseStatusID;
    }

    public void setCaseStatusID(int caseStatusID) {
        this.caseStatusID = caseStatusID;
    }

    public ArrayList<IncomeData> getSelectedCaseIncomeTable() {
        return selectedCaseIncomeTable;
    }

    public void setSelectedCaseIncomeTable(ArrayList<IncomeData> selectedCaseIncomeTable) {
        this.selectedCaseIncomeTable = selectedCaseIncomeTable;
    }

    public MessagesMB getMessagesMB() {
        return messagesMB;
    }

    public void setMessagesMB(MessagesMB messagesMB) {
        this.messagesMB = messagesMB;
    }

   
}
