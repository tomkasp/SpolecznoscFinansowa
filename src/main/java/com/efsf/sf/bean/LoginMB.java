package com.efsf.sf.bean;

import com.efsf.sf.sql.dao.UserDAO;
import com.efsf.sf.sql.entity.User;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean
@SessionScoped
public class LoginMB implements Serializable{
    private static final long serialVersionUID = 1L;

    private String email;
    private String password;
    private boolean isLogged = false;
    private Integer type;
    private int idUser;

    public LoginMB() {  
    }

    public String login() {

        UserDAO userDao=new UserDAO();
        User user=null;
        user=userDao.login(this.email, this.password);
        if ( user!=null ) {
            isLogged = true;
            type = user.getType();
            idUser = user.getIdUser();
            System.out.println("login");
            if(type==1){ return "/admin/adminMainPage"; }
            if(type==2){ return "/consultant/consultantMainPage"; }
            if(type==3){ return "/client/clientMainPage";  }
        }
        
        return "/login";
        
    }

    public String logout() {
        isLogged = false;
        System.out.println("logout");
        return "/login";
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

   
    
}