/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.efsf.sf.bean;

import com.efsf.sf.sql.dao.ClientDAO;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;


/**
 *
 * @author XaI
 */
@ManagedBean
@SessionScoped
public class CreateClientMB 
{
    
    ClientDAO clientDao = new ClientDAO();
    
    private int loginNumber;
    private String  login;
    private String  name;
    private String  password;
    private String  passwordCheck;
    private String  email; 
    
    public CreateClientMB()
    {
        loginNumber = clientDao.getLastClientID() + 1;
        login = Integer.toString(loginNumber);
        login = ("000000" + login).substring(login.length());
    }
    
    public void validateSamePassword(FacesContext context, UIComponent toValidate, Object value) 
    {
        String confirmPassword = (String)value;
        if (!confirmPassword.equals(password)) {
             FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Hasła nie pasują!", "Hasła nie pasują!");
        throw new ValidatorException(message);
        }
    }
    
    public int getLoginNumber() {
        return loginNumber;
    }

    public void setLoginNumber(int loginNumber) {
        this.loginNumber = loginNumber;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordCheck() {
        return passwordCheck;
    }

    public void setPasswordCheck(String passwordCheck) {
        this.passwordCheck = passwordCheck;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
           
    
}
