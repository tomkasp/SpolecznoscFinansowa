package com.efsf.sf.bean;

import com.efsf.sf.sql.dao.ConsultantDAO;
import com.efsf.sf.sql.dao.GenericDao;
import com.efsf.sf.sql.dao.UserDAO;
import com.efsf.sf.sql.entity.Client;
import com.efsf.sf.sql.entity.Consultant;
import com.efsf.sf.sql.entity.User;
import com.efsf.sf.util.Settings;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

@ManagedBean
@SessionScoped
public class LoginMB implements Serializable {

    @ManagedProperty("#{msg}")
    private transient ResourceBundle bundle;
    
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
    
    
    private String actualMessage;

    public String login() {
        
       
        UserDAO userDao = new UserDAO();
        ConsultantDAO consultantDao = new ConsultantDAO();
        user = null;
        user = userDao.login(this.email, this.password);
        
        if (user != null) {
            type = user.getType();
            if (type.equals(Settings.ADMIN_ACTIVE) || type.equals(Settings.CLIENT_ACTIVE) || type.equals(Settings.CONSULTANT_ACTIVE)) {
                isLogged = true;

                idUser = user.getIdUser();
            }

            if (type.equals(Settings.ADMIN_ACTIVE)) {
                return "/admin/adminMainPage?faces-redirect=true";
            }
            if (type.equals(Settings.CONSULTANT_ACTIVE)) {
                consultant = consultantDao.getCounsultantConnectedToUser(idUser);
                return "/consultant/consultantMainPage?faces-redirect=true";
            }
            if (type.equals(Settings.CLIENT_ACTIVE)) {
                client = userDao.getClientConnectedToUser(idUser);
                points = client.getPoints();

                this.activeAddingApp = this.checkNewAppActivity();

                return "/client/clientMainPage?faces-redirect=true";
            }

            if (type.equals(Settings.ADMIN_INACTIVE) || type.equals(Settings.CLIENT_INACTIVE) || type.equals(Settings.CONSULTANT_INACTIVE)) {
                return "/activateAccount?faces-redirect=true";
            } else if (type.equals(Settings.CLIENT_UNVERIFIED) || type.equals(Settings.CONSULTANT_UNVERIFIED)) {

                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, getBundle().getString("activateAccountTitle"), ""));

                return "/login";
            }

        }
        
        return "/login?faces-redirect=true";
    }

    private Boolean checkNewAppActivity() {
        return this.points > 0;
    }
    
    public void addMessageToContext(){
       FacesContext facesContext = FacesContext.getCurrentInstance();
       if(getActualMessage()!=null){
            facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, getActualMessage(), ""));
       }
       setActualMessage(null);
    }

    public String logout() {
        isLogged = false;
        type = Settings.LOGGED_OUT;
        client = null;
        consultant = null;
        user = null;
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "/login?faces-redirect=true";
    }

    public String deactivateUser() {
        if (user.getType() == Settings.ADMIN_ACTIVE) {
            user.setType(Settings.ADMIN_INACTIVE);
        }
        if (user.getType() == Settings.CONSULTANT_ACTIVE) {
            user.setType(Settings.CONSULTANT_INACTIVE);
        }
        if (user.getType() == Settings.CLIENT_ACTIVE) {
            user.setType(Settings.CLIENT_INACTIVE);
        }

        GenericDao<User> udao = new GenericDao(User.class);
        udao.update(user);

        return logout();
    }

    public String activateUser() {
        if (user.getType() == Settings.ADMIN_INACTIVE) {
            user.setType(Settings.ADMIN_ACTIVE);
        }
        if (user.getType() == Settings.CONSULTANT_INACTIVE) {
            user.setType(Settings.CONSULTANT_ACTIVE);
        }
        if (user.getType() == Settings.CLIENT_INACTIVE) {
            user.setType(Settings.CLIENT_ACTIVE);
        }

        GenericDao<User> udao = new GenericDao(User.class);
        udao.update(user);

        return login();
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

    public ResourceBundle getBundle() {
        return bundle;
    }

    public void setBundle(ResourceBundle bundle) {
        this.bundle = bundle;
    }

    public String getActualMessage() {
        return actualMessage;
    }


    public void setActualMessage(String actualMessage) {
        this.actualMessage = actualMessage;
    }

}
