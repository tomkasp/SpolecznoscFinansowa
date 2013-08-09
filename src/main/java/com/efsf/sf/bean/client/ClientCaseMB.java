/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.efsf.sf.bean.client;

import com.efsf.sf.bean.LoginMB;
import com.efsf.sf.sql.dao.*;
import com.efsf.sf.sql.entity.*;
import java.io.Serializable;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
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
    
    @ManagedProperty(value="#{clientMainPageMB}")
    private ClientMainPageMB clientMainPageMB;
    
    
    private int idTypProduktu;
    private int idTypProduktuObligation;
    private ClientCase clientCase = new ClientCase();
    private Date currentDate = new Date();
    private Obligation obligation = new Obligation();
    private ProductTypeDAO ptd = new ProductTypeDAO();
    private List<Obligation> obligationList = new ArrayList<>();
    ObligationDAO obdao = new ObligationDAO();
    private int premium = 30;
    
    
    
    // VIEW CASE DETAILS FIELDS 
    private ClientCase selectedClientCase;
    
    private Consultant selectedConsultant;
    
    
    
    /**
     * Creates a new instance of ClientCaseMB
     */

    public ClientCaseMB(){
        
    }    
    
    public List<Obligation> retrieveObligationListForCurrentClient(){ 
        setObligationList(obdao.obligationListForClient(login.getClient().getIdClient()));
        return obligationList;
    }
    
    //zwraca liste zobowiazan dla danego klienta w sesji
    public void addObligation(){
        obligation.setClient(login.getClient());
        obligation.setProductType(ptd.getProductType(idTypProduktuObligation));
        obdao.save(obligation);
        obligation = new Obligation();
    }
    
    public void addMessage(){ 
        
       if(clientCase.getPremium()){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Zabiore Ci 30 punktów!"));  
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
        return "/client/clientMainPage.xhtml";
    }
    
    // VIEW CASE METHODS 
    public void loadCaseConsultantsDetails()
    {   FacesContext facesContext = FacesContext.getCurrentInstance();
         if (!facesContext.isPostback() && !facesContext.isValidationFailed())
         {
            selectedClientCase = new ClientCaseDAO().getClientCaseWithConsultantDetails(clientMainPageMB.getSelectedCase().getIdClientCase());
            selectedConsultant = null;
         }
    }
    
    public ArrayList<Consultant> castConsultantSetToArray(Set<Consultant> cSet)
    {
        return new ArrayList<Consultant>(cSet);
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

    public ClientMainPageMB getClientMainPageMB() {
        return clientMainPageMB;
    }

    public void setClientMainPageMB(ClientMainPageMB clientMainPageMB) {
        this.clientMainPageMB = clientMainPageMB;
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

   
}
