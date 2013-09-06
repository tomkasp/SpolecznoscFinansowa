package com.efsf.sf.bean.consultant;

import com.efsf.sf.bean.LoginMB;
import com.efsf.sf.bean.MailerMB;
import com.efsf.sf.sql.dao.*;
import com.efsf.sf.sql.entity.*;
import com.efsf.sf.util.Security;
import com.efsf.sf.util.Settings;
import java.io.Serializable;
import java.util.Date;
import java.util.ResourceBundle;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;


@ManagedBean
@SessionScoped
public class ConsultantCreateMB implements Serializable {
    private static final long serialVersionUID = 1L;

    @ManagedProperty(value="#{loginMB}")
    private LoginMB loginMB;
    
    @ManagedProperty(value="#{mailerMB}")
    private MailerMB mailerMB;
    
    @ManagedProperty("#{msg}")
    private transient ResourceBundle bundle;
    
    //ConsultantCreateAccount
    private User user = new User();
    private Consultant consultant = new Consultant();
    private String confirmPassword = new String();
    private Subscription subscription = new Subscription();

    private Boolean policy = false;
    private Boolean policy2 = false;
    

    public String savePart1() throws Exception {
        UserDAO udao = new UserDAO();
        
        //SET USER TYPE:
        user.setType(Settings.CONSULTANT_UNVERIFIED);
        user.setLogin(String.valueOf(new Date().getTime()));
        user.setPassword( Security.sha1(confirmPassword) );
        udao.save(user);
        user.setLogin( ( "000000" + Integer.toString(user.getIdUser())).substring( Integer.toString( user.getIdUser() ).length() ) );
        udao.update(user);
        
        WorkingPlaceDAO wpdao = new WorkingPlaceDAO();
        WorkingPlace wp = (WorkingPlace) wpdao.workingPlaceList().get(3);
        if (wp != null) {
            consultant.setWorkingPlace(wp);
        }
        
        RegionDAO regDao = new RegionDAO();
        
        Region r = (Region) regDao.getRegion(0);
        
        consultant.setRegion(r);
        
        ConsultantDAO cdao = new ConsultantDAO();
        consultant.setUser(user);
        cdao.save(consultant);

        subscription.setConsultant(consultant);
        SubscriptionTypeDAO stdao = new SubscriptionTypeDAO();
        SubscriptionType st = stdao.getSubscriptionType(4);
        if (st != null) {
            subscription.setSubscriptionType(st);
            //PAMIETAJ O DODANIU DATY W PRZYSZŁOŚCI
            SubscriptionDAO sdao = new SubscriptionDAO();
            sdao.save(subscription);
        }
        
        getMailerMB().sendMail(user.getEmail(), consultant.getName(), user.getIdUser());
        loginMB.setActualMessage(getBundle().getString("confirmRegistrationTitle"));
        
        return "/login?faces-redirect=true";
    }

    public void validateSamePassword(FacesContext context, UIComponent toValidate, Object value) 
    {
        String password = (String) value;
      
        if (!password.equals(confirmPassword)) {
             FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Hasła nie pasują!", "Hasła nie pasują!");
        throw new ValidatorException(message);
        }
        
    }
    
    public void validatePolicy(FacesContext context, UIComponent toValidate, Object value) 
    {
        Boolean policyValue=(Boolean)value;
        
        if (!policyValue) {
             FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Musisz akceptować warunki umowy", "Musisz akceptować warunki umowy");
        throw new ValidatorException(message);
        }
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Consultant getConsultant() {
        return consultant;
    }

    public void setConsultant(Consultant consultant) {
        this.consultant = consultant;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public Boolean getPolicy() {
        return policy;
    }

    public void setPolicy(Boolean policy) {
        this.policy = policy;
    }

    public Boolean getPolicy2() {
        return policy2;
    }

    public void setPolicy2(Boolean policy2) {
        this.policy2 = policy2;
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

    public MailerMB getMailerMB() {
        return mailerMB;
    }

    public void setMailerMB(MailerMB mailerMB) {
        this.mailerMB = mailerMB;
    }

}