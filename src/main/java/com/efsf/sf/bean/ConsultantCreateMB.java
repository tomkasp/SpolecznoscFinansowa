package com.efsf.sf.bean;

import com.efsf.sf.sql.dao.AddressDAO;
import com.efsf.sf.sql.dao.ConsultantDAO;
import com.efsf.sf.sql.dao.InstitutionDAO;
import com.efsf.sf.sql.dao.ProductTypeDAO;
import com.efsf.sf.sql.dao.RegionDAO;
import com.efsf.sf.sql.dao.SubscriptionDAO;
import com.efsf.sf.sql.dao.SubscriptionTypeDAO;
import com.efsf.sf.sql.dao.UserDAO;
import com.efsf.sf.sql.dao.WorkingPlaceDAO;
import com.efsf.sf.sql.entity.Address;
import com.efsf.sf.sql.entity.Consultant;
import com.efsf.sf.sql.entity.Institution;
import com.efsf.sf.sql.entity.ProductType;
import com.efsf.sf.sql.entity.Region;
import com.efsf.sf.sql.entity.Subscription;
import com.efsf.sf.sql.entity.SubscriptionType;
import com.efsf.sf.sql.entity.User;
import com.efsf.sf.sql.entity.WorkingPlace;
import com.efsf.sf.util.Security;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;

/**
 * @author WR1EI1
 */
@ManagedBean
@SessionScoped
public class ConsultantCreateMB implements Serializable {

    //ConsultantCreateAccount
    private User user = new User();
    private Consultant consultant = new Consultant();
    private String confirmPassword = new String();
    private Subscription subscription = new Subscription();
    //ConsultantFillAccountData
    private Address mainAddress = new Address();
    private Address invoiceAddress = new Address();
    private Integer idWorkingPlace;
    private List<Integer> idSelectedBankList = new ArrayList<>();
    private List<Integer> idSelectedInstitutionList = new ArrayList<>();
    private List<Integer> idProductTypes = new ArrayList<>();
    private Integer idRegion;
    private Integer idMainRegion;
    private Integer idInvoiceRegion;
    private Integer idSubscriptionType;
    private Boolean policy = false;
    private Boolean policy2 = false;
    
    public ConsultantCreateMB() {
    }

    public String savePart1() {
        UserDAO udao = new UserDAO();

        //SET USER TYPE:
        user.setType(2);
        user.setLogin(String.valueOf(new Date().getTime()));
        user.setPassword( Security.sha1(confirmPassword) );
        udao.save(user);
        user.setLogin(user.getIdUser().toString());
        udao.update(user);


        WorkingPlaceDAO wpdao = new WorkingPlaceDAO();
        WorkingPlace wp = (WorkingPlace) wpdao.workingPlaceList().get(3);
        if (wp != null) {
            consultant.setWorkingPlace(wp);
        }
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
        return "/consultant/consultantFillAccountData?faces-redirect=true";
    }

    public String savePart2() {

        DictionaryMB dictionaryMB = new DictionaryMB();
        InstitutionDAO idao=new InstitutionDAO();
        ProductTypeDAO ptdao=new ProductTypeDAO();
        RegionDAO rdao=new RegionDAO();
        
        WorkingPlace wp = dictionaryMB.getWorkingPlace().get(idWorkingPlace - 1);
        consultant.setWorkingPlace(wp);
        //HERE:
        //ADD BANKS
        Set<Institution> institutionSet = new HashSet<Institution>();
        Iterator it=idSelectedBankList.iterator();
        while ( it.hasNext() ) {  
            Integer id = Integer.valueOf( it.next().toString() );
            Institution inst = idao.getInstitution( id );
            institutionSet.add( inst );
        } 
        //ADD INSTITUTIONS
        it=idSelectedInstitutionList.iterator();
        while ( it.hasNext() ) {  
            Integer id = Integer.valueOf( it.next().toString() );
            Institution inst = idao.getInstitution( id );
            institutionSet.add( inst );
        }
        //ADD ALL INSTITUTIONS IN CONSULTANT
        consultant.setInstitutions(institutionSet);
        //ADD PRODUCT TYPES
        Set<ProductType> productTypeSet = new HashSet<>();
        it=idProductTypes.iterator();
        while ( it.hasNext() ) {  
            Integer id = Integer.valueOf( it.next().toString() );
            ProductType pt = ptdao.getProductType(id);
            productTypeSet.add(pt);
        }
        consultant.setProductTypes(productTypeSet);
        //ADD CONSULTANT REGION
        Region r=rdao.getRegion(idRegion);
        consultant.setRegion(r);
        //ADD MAIN REGION
        r=rdao.getRegion(idMainRegion);
        mainAddress.setRegion(r);
        mainAddress.setConsultant(consultant);
        AddressDAO adao=new AddressDAO();
        adao.save(mainAddress);
        //ADD INVOICE REGION
        r=rdao.getRegion(idInvoiceRegion);
        invoiceAddress.setRegion(r);
        invoiceAddress.setConsultant(consultant);
        adao.save(invoiceAddress);
        //ADD SUBSCRIPTION TYPE
        if(idSubscriptionType!=null)
        {
        SubscriptionTypeDAO stdao = new SubscriptionTypeDAO();
        SubscriptionType subscriptionType = stdao.getSubscriptionType(idSubscriptionType);
        subscription.setSubscriptionType(subscriptionType);
        subscription.setConsultant(consultant);
        SubscriptionDAO sdao=new SubscriptionDAO();
        sdao.update(subscription);
        }
        //UPDATE CONSULTANT
        ConsultantDAO cdao = new ConsultantDAO();
        cdao.update(consultant);
        //UPDATE USER
        UserDAO udao = new UserDAO();
        udao.update(user);

        return "/consultant/consultantMainPage?faces-redirect=true";
    }
    
    public void validateSamePassword(FacesContext context, UIComponent toValidate, Object value) 
    {
        String password = (String)value;
        if (!password.equals(confirmPassword)) {
             FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Hasła nie pasują!", "Hasła nie pasują!");
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

    public List<Integer> getIdSelectedBankList() {
        return idSelectedBankList;
    }

    public void setIdSelectedBankList(List<Integer> idSelectedBankList) {
        this.idSelectedBankList = idSelectedBankList;
    }

    public List<Integer> getIdSelectedInstitutionList() {
        return idSelectedInstitutionList;
    }

    public void setIdSelectedInstitutionList(List<Integer> idSelectedInstitutionList) {
        this.idSelectedInstitutionList = idSelectedInstitutionList;
    }

    public List<Integer> getIdProductTypes() {
        return idProductTypes;
    }

    public void setIdProductTypes(List<Integer> idProductTypes) {
        this.idProductTypes = idProductTypes;
    }

    public Integer getIdRegion() {
        return idRegion;
    }

    public void setIdRegion(Integer idRegion) {
        this.idRegion = idRegion;
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