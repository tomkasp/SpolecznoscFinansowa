/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.efsf.sf.bean;

import com.efsf.sf.sql.dao.ClientCaseDAO;
import com.efsf.sf.sql.dao.ClientDAO;
import com.efsf.sf.sql.dao.ProductTypeDAO;
import com.efsf.sf.sql.entity.CaseStatus;
import com.efsf.sf.sql.entity.Client;
import com.efsf.sf.sql.entity.ClientCase;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

/**
 * @author admin
 */
@ManagedBean
@SessionScoped
public class ClientCaseMB implements Serializable{

    @ManagedProperty(value="#{loginMB}")
    private LoginMB login;
    
    private int idTypProduktu;
    
    private ClientCase clientCase = new ClientCase();
    /**
     * Creates a new instance of ClientCaseMB
     */
    public ClientCaseMB() {
    }

    
    public void addCase(){
        ClientCaseDAO ccd = new ClientCaseDAO();
        ProductTypeDAO ptd = new ProductTypeDAO();
        ClientDAO cd = new ClientDAO();
        
        //pamietac o zabraniu punktow z klienta!
        cd.decrementPoints(login.getClient());
        login.getClient().setPoints(login.getClient().getPoints()-1);
        
        
        clientCase.setProductType(ptd.getProductType(idTypProduktu));
        clientCase.setClient(login.getClient());
        clientCase.setPhase(1);
        clientCase.setViewCounter(0);
        clientCase.setDifficulty(0);
        ccd.saveClientCase(clientCase);
        
        clientCase=null;
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
}
