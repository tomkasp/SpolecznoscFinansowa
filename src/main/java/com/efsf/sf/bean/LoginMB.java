package com.efsf.sf.bean;

import com.efsf.sf.sql.dao.ConsultantDAO;
import com.efsf.sf.sql.dao.UserDAO;
import com.efsf.sf.sql.entity.Client;
import com.efsf.sf.sql.entity.Consultant;
import com.efsf.sf.sql.entity.User;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean
@SessionScoped
public class LoginMB implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String email;
    private String password;
    private boolean isLogged = false;
    private Integer type;
    private int idUser;
    private int points;
    
    private User user; 
    private Client client;
    private Consultant consultant;
    
    private Boolean activeAddingApp;
    
    public LoginMB() {  
    }

    public String login() {

        UserDAO userDao=new UserDAO();
        ConsultantDAO consultantDao = new ConsultantDAO();
        user=null;
        user=userDao.login(this.email, this.password);
        if ( user!=null ) {
            isLogged = true;
            type = user.getType();
            idUser = user.getIdUser();
            System.out.println("login");
            if(type==1)
            {
                return "/admin/adminMainPage?faces-redirect=true"; 
            }
            if(type==2)
            { 
                consultant = consultantDao.getCounsultantConnectedToUser(idUser);
                return "/consultant/consultantMainPage?faces-redirect=true"; 
            }
            if(type==3)
            {
                client = userDao.getClientConnectedToUser(idUser);
                points = client.getPoints();
                
                this.activeAddingApp=this.checkNewAppActivity();
                
                return "/client/clientMainPage?faces-redirect=true";  
            } 
        }
        return "/login?faces-redirect=true"; 
    }

    
    private Boolean checkNewAppActivity(){
        if(this.points>0) {
            return true;
        }
        else {
            return false;
        }
    }
    public String logout() {
        isLogged = false;
        //FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        System.out.println("logout");
        return "/login?faces-redirect=true";
    }
    
  
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isIsLogged() {
        return isLogged;
    }

    public void setIsLogged(boolean isLogged) {
        this.isLogged = isLogged;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Consultant getConsultant() {
        return consultant;
    }

    public void setConsultant(Consultant consultant) {
        this.consultant = consultant;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getPoints() {
        return points;
    }

    public Boolean getActiveAddingApp() {
        return activeAddingApp;
    }

    public void setActiveAddingApp(Boolean activeAddingApp) {
        this.activeAddingApp = activeAddingApp;
    }

   
    
}
