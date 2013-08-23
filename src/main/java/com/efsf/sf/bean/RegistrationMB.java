package com.efsf.sf.bean;

import com.efsf.sf.sql.dao.GenericDao;
import com.efsf.sf.sql.entity.User;
import com.efsf.sf.util.Security;
import com.efsf.sf.util.Settings;
import java.io.IOException;
import java.io.Serializable;
import java.util.ResourceBundle;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

@ManagedBean
@RequestScoped
public class RegistrationMB implements Serializable {

    @ManagedProperty(value = "#{loginMB}")
    private LoginMB loginMB;
    private String token;
    private Integer id;
    
    @ManagedProperty("#{msg}")
    private transient ResourceBundle bundle;

    public void confirmRegistration() throws IOException {
        GenericDao<User> dao = new GenericDao(User.class);
        User u = dao.getById(getId());

        FacesContext facesContext = FacesContext.getCurrentInstance();

        if (u != null && Security.sha1(u.getEmail()).equals(getToken())) {
            if (u.getType() == Settings.CONSULTANT_UNVERIFIED) {
                u.setType(Settings.CONSULTANT_ACTIVE);
            } else if (u.getType() == Settings.CLIENT_UNVERIFIED) {
                u.setType(Settings.CLIENT_ACTIVE);
            }

            dao.update(u);

            loginMB.setActualMessage(getBundle().getString("correctLink"));
        } else {
            loginMB.setActualMessage(getBundle().getString("wrongLink"));
        }

        facesContext.getExternalContext().redirect("login.xhtml");
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LoginMB getLoginMB() {
        return loginMB;
    }

    public void setLoginMB(LoginMB loginMB) {
        this.loginMB = loginMB;
    }


    public ResourceBundle getBundle() {
        return bundle;
    }


    public void setBundle(ResourceBundle bundle) {
        this.bundle = bundle;
    }
}
