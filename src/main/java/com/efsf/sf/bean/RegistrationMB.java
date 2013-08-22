package com.efsf.sf.bean;

import com.efsf.sf.sql.dao.GenericDao;
import com.efsf.sf.sql.entity.User;
import com.efsf.sf.util.Security;
import com.efsf.sf.util.Settings;
import java.io.IOException;
import java.io.Serializable;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;


@ManagedBean
@SessionScoped
public class RegistrationMB implements Serializable{

    private String token;
    private Integer id;    
    
     public void confirmRegistration() throws IOException {
        GenericDao<User> dao = new GenericDao(User.class);
        User u = dao.getById(getId());

        if (u != null && Security.sha1(u.getEmail()).equals(getToken())) {
            if (u.getType() == Settings.CONSULTANT_UNVERIFIED) {
                u.setType(Settings.CONSULTANT_ACTIVE);
            } else if (u.getType() == Settings.CLIENT_UNVERIFIED) {
                u.setType(Settings.CLIENT_ACTIVE);
            }

            dao.update(u);

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Twoje konto jest aktywne", 
                    "Zaloguj się przy użyciu maila i hasła podanego przy rejestracji!")); 
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL,"Błąd", "Podany link jest błędny, zarejestruj się jeszcze raz"));  
        }
        
        FacesContext.getCurrentInstance().getExternalContext().redirect("login.xhtml");
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
    
}
