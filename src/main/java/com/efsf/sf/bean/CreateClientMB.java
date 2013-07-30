/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.efsf.sf.bean;

import com.efsf.sf.sql.dao.ClientDAO;
import com.efsf.sf.sql.dao.EducationDAO;
import com.efsf.sf.sql.dao.MaritalStatusDAO;
import com.efsf.sf.sql.dao.UserDAO;
import com.efsf.sf.sql.entity.Client;
import com.efsf.sf.sql.entity.User;
import com.efsf.sf.util.Security;
import java.security.NoSuchAlgorithmException;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;


/**
 *
 * @author XaI
 */
@ManagedBean
@ViewScoped
public class CreateClientMB 
{
    
    @ManagedProperty(value="#{loginMB}")
    private LoginMB loginMB;
    
    User user; 
    
    private int loginNumber;
    private String  login;
    private String  name;
    private String  password;
    private String  passwordCheck;
    private String  email; 
    
    public CreateClientMB()
    {
//        UserDAO userDao = new UserDAO();
//        
//        user = new User("", "", "", 3);        
//        userDao.save(user);
//        loginNumber = user.getIdUser();
//        login = Integer.toString(loginNumber);
//        login = ("000000" + login).substring(login.length());
    }
//    
//    @PreDestroy
//    public void deleteUser()
//    {
////        UserDAO userDao = new UserDAO();
////        
////        userDao.delete(user);
////        
////        System.out.println("Usunieto");
//        
//    }
    public String createClientAccount() throws NoSuchAlgorithmException
    {
        UserDAO userDao = new UserDAO();
        ClientDAO clientDao = new ClientDAO();
        EducationDAO eduDao = new EducationDAO();
        MaritalStatusDAO maritalDao = new MaritalStatusDAO();
        
    
        user = new User();
        user.setPassword(Security.sha1(password));
        user.setEmail(email);
        user.setType(3);
        user.setLogin(email); 
        
        Client client = new Client();
        client.setName(name);
        client.setUser(user);
        client.setLastName("");
        client.setEducation(eduDao.getEducation(7));  
        client.setMaritalStatus(maritalDao.getMaritalStatus(7));
        client.setPoints(5);
        
        userDao.save(user);
        clientDao.save(client);
        
        user.setLogin(user.getIdUser().toString());
        
        userDao.update(user);
        
        
        loginMB.setUser(user);
        loginMB.setClient(client);
        
        
        
        return "/client/clientFillAccountData?faces-redirect=true";
    }
        
    public void validateSamePassword(FacesContext context, UIComponent toValidate, Object value) 
    {
        String confirmPassword = (String)value;
        if (!confirmPassword.equals(password)) {
             FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Hasła nie pasują!", "Hasła nie pasują!");
        throw new ValidatorException(message);
        }
    }
    
    public String redirectToCreateConsultant()
    {
        return "/consultant/consultantCreateAccount?faces-redirect=true";   
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

    public LoginMB getLoginMB() {
        return loginMB;
    }

    public void setLoginMB(LoginMB loginMB) {
        this.loginMB = loginMB;
    }
    
           
    
}
