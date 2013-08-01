/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.efsf.sf.bean;

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
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        
        CaseStatus caseStatus = (CaseStatus)session.load(CaseStatus.class, 1);
        ProductType pt = (ProductType)session.load(ProductType.class, 1);
        
        clientCase.setClient(getLogin().getClient());
        
        clientCase.setProductType(pt);
        clientCase.setCaseStatus(caseStatus);
        
        
        clientCase.setPhase(1);
        clientCase.setViewCounter(0);
        clientCase.setDifficulty(0);
       
        session.save(clientCase);
        
        
        session.getTransaction().commit();
        session.close();
       
        
        
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
