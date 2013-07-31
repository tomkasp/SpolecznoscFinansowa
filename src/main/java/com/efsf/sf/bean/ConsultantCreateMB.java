package com.efsf.sf.bean;

import com.efsf.sf.sql.dao.ConsultantDAO;
import com.efsf.sf.sql.dao.SubscriptionDAO;
import com.efsf.sf.sql.dao.SubscriptionTypeDAO;
import com.efsf.sf.sql.dao.UserDAO;
import com.efsf.sf.sql.dao.WorkingPlaceDAO;
import com.efsf.sf.sql.entity.Address;
import com.efsf.sf.sql.entity.Consultant;
import com.efsf.sf.sql.entity.Institution;
import com.efsf.sf.sql.entity.Subscription;
import com.efsf.sf.sql.entity.SubscriptionType;
import com.efsf.sf.sql.entity.User;
import com.efsf.sf.sql.entity.WorkingPlace;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
/**
 * @author WR1EI1
 */
@ManagedBean
@SessionScoped
public class ConsultantCreateMB implements Serializable{
    
    //ConsultantCreateAccount
    private User user=new User();
    private Consultant consultant=new Consultant();
    private String confirmPassword=new String();
    
    //ConsultantFillAccountData
    private Address mainAddress=new Address();
    private Address invoiceAddress=new Address();
    
    private Integer idWorkingPlace;
    
    private List<Institution> idselectedBankList=new ArrayList<>();
    private List<Institution> idselectedInstitutionList=new ArrayList<>();
    private List<Integer> idProductTypes=new ArrayList<>();
    
    private Integer idScope;
    
    private Integer idMainRegion;
    private Integer idInvoiceRegion;
    
    private Integer idSubscriptionType;
    
    private Boolean policy=false;
    private Boolean policy2=false;
    
    
    public ConsultantCreateMB() {
    }
    
    public String savePart1() {
        UserDAO udao=new UserDAO();
        
        //SET USER TYPE:
        user.setType(1);
        
        user.setLogin( String.valueOf( new Date().getTime() ) );
        udao.save(user);
        user.setLogin( user.getIdUser().toString() );
        udao.update(user);
        
        
        WorkingPlaceDAO wpdao=new WorkingPlaceDAO();
        WorkingPlace wp=(WorkingPlace) wpdao.workingPlaceList().get(0);
        if(wp!=null)
        {
        consultant.setWorkingPlace(wp);
        }
        ConsultantDAO cdao=new ConsultantDAO();
        consultant.setUser(user);
        cdao.save(consultant);
        
        Subscription trialSubscription=new Subscription();
        trialSubscription.setConsultant(consultant);
        SubscriptionTypeDAO stdao=new SubscriptionTypeDAO();
        SubscriptionType st=stdao.getSubscriptionType(4);
        if(st!=null){
        trialSubscription.setSubscriptionType(st); 
        //PAMIETAJ O DODANIU DATY W PRZYSZŁOŚCI
        SubscriptionDAO sdao=new SubscriptionDAO();
        sdao.save(trialSubscription);
        }
        
        return "/consultant/consultantFillAccountData?faces-redirect=true";   
    }
    
    public String savePart2() {
        
        //UserDAO udao=new UserDAO();
        //udao.update(user);
        
        ConsultantDAO cdao=new ConsultantDAO();
        cdao.update(consultant);
        
        return "/consultant/consultantMainPage?faces-redirect=true";   
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

    public Address getMainAddress() {
        return mainAddress;
    }

    public void setMainAddress(Address mainAddress) {
        this.mainAddress = mainAddress;
    }

    public Address getInvoiceAddress() {
        return invoiceAddress;
    }

    public void setInvoiceAddress(Address invoiceAddress) {
        this.invoiceAddress = invoiceAddress;
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

    public Integer getIdWorkingPlace() {
        return idWorkingPlace;
    }

    public void setIdWorkingPlace(Integer idWorkingPlace) {
        this.idWorkingPlace = idWorkingPlace;
    }

    public List<Institution> getIdselectedBankList() {
        return idselectedBankList;
    }

    public void setIdselectedBankList(List<Institution> idselectedBankList) {
        this.idselectedBankList = idselectedBankList;
    }

    public List<Institution> getIdselectedInstitutionList() {
        return idselectedInstitutionList;
    }

    public void setIdselectedInstitutionList(List<Institution> idselectedInstitutionList) {
        this.idselectedInstitutionList = idselectedInstitutionList;
    }

    public List<Integer> getIdProductTypes() {
        return idProductTypes;
    }

    public void setIdProductTypes(List<Integer> idProductTypes) {
        this.idProductTypes = idProductTypes;
    }

    

    public Integer getIdScope() {
        return idScope;
    }

    public void setIdScope(Integer idScope) {
        this.idScope = idScope;
    }

    public Integer getIdMainRegion() {
        return idMainRegion;
    }

    public void setIdMainRegion(Integer idMainRegion) {
        this.idMainRegion = idMainRegion;
    }

    public Integer getIdInvoiceRegion() {
        return idInvoiceRegion;
    }

    public void setIdInvoiceRegion(Integer idInvoiceRegion) {
        this.idInvoiceRegion = idInvoiceRegion;
    }

    public Integer getIdSubscriptionType() {
        return idSubscriptionType;
    }

    public void setIdSubscriptionType(Integer idSubscriptionType) {
        this.idSubscriptionType = idSubscriptionType;
    }

    
    
  
}