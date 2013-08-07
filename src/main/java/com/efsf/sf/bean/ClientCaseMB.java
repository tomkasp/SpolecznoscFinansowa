/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.efsf.sf.bean;

import com.efsf.sf.sql.dao.*;
import com.efsf.sf.sql.entity.*;
import java.io.Serializable;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

@ManagedBean
@SessionScoped
public class ClientCaseMB implements Serializable {

    @ManagedProperty(value = "#{loginMB}")
    private LoginMB login;
    
    private int idTypProduktu;
    private int idTypProduktuObligation;
    private ClientCase clientCase = new ClientCase();
    private Date currentDate = new Date();
    private Obligation obligation = new Obligation();
    private ProductTypeDAO ptd = new ProductTypeDAO();
    private List<Obligation> obligationList = new ArrayList<>();
    ObligationDAO obdao = new ObligationDAO();
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
    
    
    
    public void addCase() {
        if (login.getClient().getPoints() > 0) {
            ClientCaseDAO ccd = new ClientCaseDAO();
            
            ClientDAO cd = new ClientDAO();

            //pamietac o zabraniu punktow z klienta!
            cd.decrementPoints(login.getClient());
            login.getClient().setPoints(login.getClient().getPoints() - 1);

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

   

    


    
}
