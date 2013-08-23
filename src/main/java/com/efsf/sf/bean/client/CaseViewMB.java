/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.efsf.sf.bean.client;

import com.efsf.sf.bean.LoginMB;
import com.efsf.sf.bean.MessagesMB;
import com.efsf.sf.collection.IncomeData;
import com.efsf.sf.collection.ScheduleItem;
import com.efsf.sf.sql.dao.CaseRatingDAO;
import com.efsf.sf.sql.dao.CaseStatusDAO;
import com.efsf.sf.sql.dao.ClientCaseDAO;
import com.efsf.sf.sql.dao.ClientDAO;
import com.efsf.sf.sql.entity.CaseStatus;
import com.efsf.sf.sql.entity.Client;
import com.efsf.sf.sql.entity.ClientCase;
import com.efsf.sf.sql.entity.Consultant;
import com.efsf.sf.sql.entity.Income;
import com.efsf.sf.sql.entity.IncomeBusinessActivity;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author XaI
 */
@ManagedBean
@ViewScoped
public class CaseViewMB implements Serializable{
    
    

    @ManagedProperty(value="#{clientCaseMB}")
    private ClientCaseMB clientCaseMB;
    
    @ManagedProperty(value = "#{loginMB}")
    private LoginMB loginMB;
    
    @ManagedProperty(value = "#{messagesMB}")
    private MessagesMB messagesMB;
    
    @ManagedProperty("#{msg}")
    private transient ResourceBundle bundle;
     
    private ClientCase selectedClientCase;
    private Integer clientCaseId;
    private Consultant selectedConsultant;
    private Consultant selectedPremiumConsultant;
    private int caseStatusID;
    
    private ArrayList<IncomeData> selectedCaseIncomeTable = new ArrayList();
    
    private ArrayList<Consultant> premiumConsultants = new ArrayList();
    
    private List<ScheduleItem> schedule = new ArrayList();
    
    public CaseViewMB() {
    }
    
    public void loadCaseConsultantsDetails() throws IOException
    {   FacesContext facesContext = FacesContext.getCurrentInstance();
         if (!facesContext.isPostback() && !facesContext.isValidationFailed())
         {
            ClientCaseDAO cdao = new ClientCaseDAO();
            if (!cdao.checkClientAccess(loginMB.getClient().getIdClient(), clientCaseId)) 
            {
                facesContext.getExternalContext().redirect("./../error.xhtml");
            }
            selectedClientCase = cdao.getClientCaseWithConsultantDetails(clientCaseId);
         }
    }
    
    public void loadCaseClientsDetails() throws IOException           
    {   
        FacesContext facesContext = FacesContext.getCurrentInstance();
        if (!facesContext.isValidationFailed())
        {
            ClientCaseDAO cdao = new ClientCaseDAO();
            if (!cdao.checkConsultantAccess(clientCaseId, loginMB.getConsultant().getIdConsultant()))
            {
                facesContext.getExternalContext().redirect("./../error.xhtml");
            }
            selectedClientCase = cdao.getClientCaseWithClientDetails(clientCaseId);
            caseStatusID = selectedClientCase.getCaseStatus().getIdCaseStatus();
            selectedClientCase.setViewCounter(selectedClientCase.getViewCounter()+ 1);      
            cdao.updateClientCase(selectedClientCase);
            fillSelectedCaseIncomeTable();
        }
    }
    
    public void changeCaseStatus()
    {
        CaseStatus cs = new CaseStatusDAO().read(caseStatusID);
        String before = selectedClientCase.getCaseStatus().getName();
        
        selectedClientCase.setCaseStatus(cs);
        new ClientCaseDAO().updateClientCase(selectedClientCase);
        String after = selectedClientCase.getCaseStatus().getName();
        
        messagesMB.generateSystemMessage(bundle.getString("STATUS_CHANGED"), selectedClientCase.getClient().getUser().getIdUser(), new Object[] {clientCaseId, before, after});
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
    
    public boolean checkLoggedConsultantAccessToCase()
    {
        ClientCaseDAO caseDao = new ClientCaseDAO();
        return caseDao.checkConsultantAccess(clientCaseId, loginMB.getConsultant().getIdConsultant());
                
    }
    
    
    
    public ArrayList<Consultant> castConsultantSetToArray(Set<Consultant> cSet)
    {
        return new ArrayList(cSet);
    }
    
    public void assignPremiumConsultant()
    {
        ClientCaseDAO caseDao = new ClientCaseDAO();
        CaseStatusDAO statusDao = new CaseStatusDAO();
        selectedClientCase.setConsultant(selectedPremiumConsultant);
        caseDao.updateClientCase(selectedClientCase);  
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
        
        messagesMB.generateSystemMessage(bundle.getString("CLIENT_SELECTED_CONSULTANT"), selectedConsultant.getUser().getIdUser(), new Object[] {loginMB.getClient().getIdClient(), selectedClientCase.getIdClientCase()}); 
    }
    
    public void rowDoubleClick(Consultant cos) throws IOException
    {   
        FacesContext.getCurrentInstance().getExternalContext().redirect("clientConsultantProfileView.xhtml?idConsultant=" + cos.getIdConsultant()); 
    }
    
    public boolean isNotRated(){
        CaseRatingDAO dao = new CaseRatingDAO();
        return dao.isNotRated(getSelectedClientCase().getIdClientCase());
    }
    
    public String toRateCase()
    {
        clientCaseMB.setCurrentlyRatedCase(selectedClientCase);
        return "/client/clientConsultantRate.xhtml?faces-redirect=true";
    }

    public ClientCase getSelectedClientCase() {
        return selectedClientCase;
    }

    public void setSelectedClientCase(ClientCase selectedClientCase) {
        this.selectedClientCase = selectedClientCase;
    }

    public Integer getClientCaseId() {
        return clientCaseId;
    }

    public void setClientCaseId(Integer clientCaseId) {
        this.clientCaseId = clientCaseId;
    }

    public LoginMB getLoginMB() {
        return loginMB;
    }

    public void setLoginMB(LoginMB loginMB) {
        this.loginMB = loginMB;
    }

    public MessagesMB getMessagesMB() {
        return messagesMB;
    }

    public void setMessagesMB(MessagesMB messagesMB) {
        this.messagesMB = messagesMB;
    }

    public ResourceBundle getBundle() {
        return bundle;
    }

    public void setBundle(ResourceBundle bundle) {
        this.bundle = bundle;
    }

    public Consultant getSelectedConsultant() {
        return selectedConsultant;
    }

    public void setSelectedConsultant(Consultant selectedConsultant) {
        this.selectedConsultant = selectedConsultant;
    }

    public Consultant getSelectedPremiumConsultant() {
        return selectedPremiumConsultant;
    }

    public void setSelectedPremiumConsultant(Consultant selectedPremiumConsultant) {
        this.selectedPremiumConsultant = selectedPremiumConsultant;
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

    public ArrayList<Consultant> getPremiumConsultants() {
        return premiumConsultants;
    }

    public void setPremiumConsultants(ArrayList<Consultant> premiumConsultants) {
        this.premiumConsultants = premiumConsultants;
    }

    public ClientCaseMB getClientCaseMB() {
        return clientCaseMB;
    }

    public void setClientCaseMB(ClientCaseMB clientCaseMB) {
        this.clientCaseMB = clientCaseMB;
    }
    
    
    public List<ScheduleItem> getSchedule() {
        Integer payementNumber = 0;
        Double toPay = 0.0, total = 0.0;
        Double instalment = 0.0;
        schedule=new ArrayList();
        
        total = selectedClientCase.getConsolidationValue().doubleValue();

        if (selectedClientCase.getFreeResourcesValue() != null) {
            total += selectedClientCase.getFreeResourcesValue().doubleValue();
        }

        if (selectedClientCase.getExpectedInstalment() != null) {
            instalment = selectedClientCase.getExpectedInstalment().doubleValue();
            payementNumber = (int) (total / instalment);


            Calendar cal = Calendar.getInstance();
            cal.setTime(selectedClientCase.getEndDate());
            toPay = total;

            for (int i = 0; i < payementNumber; i++) {
                toPay -= instalment;
                cal.add(Calendar.MONTH, 1);
                schedule.add(new ScheduleItem(cal.getTime(), instalment, total - toPay, toPay));
            }

            if (toPay > 0) {
                cal.add(Calendar.MONTH, 1);
                schedule.add(new ScheduleItem(cal.getTime(), toPay, total, 0.0));
            }
        }

        return schedule;
    }

    public void setSchedule(List<ScheduleItem> schedule) {
        this.schedule = schedule;
    }
}
