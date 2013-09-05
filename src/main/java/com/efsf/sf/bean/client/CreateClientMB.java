package com.efsf.sf.bean.client;

import com.efsf.sf.bean.DictionaryMB;
import com.efsf.sf.bean.LoginMB;
import com.efsf.sf.bean.MailerMB;
import com.efsf.sf.sql.dao.ClientDAO;
import com.efsf.sf.sql.dao.EducationDAO;
import com.efsf.sf.sql.dao.MaritalStatusDAO;
import com.efsf.sf.sql.dao.UserDAO;
import com.efsf.sf.sql.entity.Address;
import com.efsf.sf.sql.entity.Client;
import com.efsf.sf.sql.entity.User;
import com.efsf.sf.util.Security;
import com.efsf.sf.util.Settings;
import java.io.Serializable;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;


@ManagedBean
@SessionScoped
public class CreateClientMB implements Serializable
{
    private static final long serialVersionUID = 1L;
    
    @ManagedProperty(value="#{loginMB}")
    private LoginMB loginMB;
    
    @ManagedProperty(value="#{dictionaryMB}")
    private DictionaryMB dictionaryMB;
    
    @ManagedProperty("#{msg}")
    private transient ResourceBundle bundle;
    
    @ManagedProperty(value="#{mailerMB}")
    private MailerMB mailerMB;
    
    private String  name;
    private String  password;
    private String  passwordCheck;
    private String  email; 
    
    //Address Data
    private Address address = new Address();
    private Set<Address> addressSet = new HashSet();
    
    
    // flag to set required elements if we want to make new application 
    private boolean areRequired = false;

    public boolean isAreRequired() {
        return areRequired;
    }

    public void setAreRequired(boolean areRequired) {
        this.areRequired = areRequired;
    }
    

    public String createClientAccount() 
    {
        User user;
        
        UserDAO userDao = new UserDAO();
        ClientDAO clientDao = new ClientDAO();
        EducationDAO eduDao = new EducationDAO();
        MaritalStatusDAO maritalDao = new MaritalStatusDAO();
        
        user = new User();
        user.setPassword(Security.sha1(password));
        user.setEmail(email);
        user.setType(Settings.CLIENT_UNVERIFIED);
        user.setLogin(email); 
        
        Client client = new Client();
        client.setName(name);
        client.setUser(user);
        client.setLastName("");
        client.setEducation(eduDao.getEducation(0));  
        client.setMaritalStatus(maritalDao.getMaritalStatus(0));
        client.setPoints(5);
        
        address.setClient(client);
        address.setCountry("Polska");
        address.setHouseNumber("");
        address.setRegion(dictionaryMB.getRegion().get(0));
        addressSet.add(address);
        
        client.setAddresses(addressSet);
        
        userDao.save(user);
        clientDao.save(client);
        
        user.setLogin(("000000" + Integer.toString(user.getIdUser())).substring(Integer.toString(user.getIdUser()).length()));
        userDao.update(user);
        getMailerMB().sendMail(email, name, user.getIdUser() );
 
        
        loginMB.setActualMessage(bundle.getString("confirmRegistrationTitle"));
        
        return "/login?faces-redirect=true";
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

    public DictionaryMB getDictionaryMB() {
        return dictionaryMB;
    }

    public void setDictionaryMB(DictionaryMB dictionaryMB) {
        this.dictionaryMB = dictionaryMB;
    }

    public ResourceBundle getBundle() {
        return bundle;
    }

    public void setBundle(ResourceBundle bundle) {
        this.bundle = bundle;
    }


    public MailerMB getMailerMB() {
        return mailerMB;
    }

    public void setMailerMB(MailerMB mailerMB) {
        this.mailerMB = mailerMB;
    }
  
}
