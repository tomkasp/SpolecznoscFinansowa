/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.efsf.sf.bean;

import com.efsf.sf.sql.dao.ClientCaseDAO;
import com.efsf.sf.sql.entity.CaseStatus;
import com.efsf.sf.sql.entity.ClientCase;
import com.efsf.sf.sql.entity.ProductType;
import com.efsf.sf.sql.util.HibernateUtil;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import org.hibernate.Session;

/**
 *
 * @author admin
 */
@ManagedBean
@SessionScoped
public class ClientCaseMB implements Serializable{

    @ManagedProperty(value="#{loginMB}")
    private LoginMB login;
    
    private ClientCase clientCase = new ClientCase();
    /**
     * Creates a new instance of ClientCaseMB
     */
    public ClientCaseMB() {
    }

    
    public void addCase(){
        ClientCaseDAO ccd = new ClientCaseDAO();
        ccd.saveClientCase(clientCase,login);
        
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
}