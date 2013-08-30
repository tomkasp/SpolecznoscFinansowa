package com.efsf.sf.bean.consultant;

import com.efsf.sf.bean.DictionaryMB;
import com.efsf.sf.bean.LoginMB;
import com.efsf.sf.sql.dao.*;
import com.efsf.sf.sql.entity.*;
import com.efsf.sf.util.Security;
import com.efsf.sf.util.SendMail;
import com.efsf.sf.util.Settings;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.servlet.http.HttpServletRequest;


@ManagedBean
@SessionScoped
public class ConsultantCreateMB implements Serializable {
    private static final long serialVersionUID = 1L;

    @ManagedProperty(value="#{loginMB}")
    private LoginMB loginMB;
    
    @ManagedProperty("#{msg}")
    private transient ResourceBundle bundle;
    
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
    private InvoiceData invoiceData=new InvoiceData();
    private Integer idSubscriptionType;
    private Boolean policy = false;
    private Boolean policy2 = false;
    
    public ConsultantCreateMB() {
    }

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
        
        HttpServletRequest request = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
        String host=request.getServerName();
    //    SendMail.sendRegisterMail(user.getEmail(), consultant.getName(), user.getIdUser(), host);
        
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, getBundle().getString("confirmRegistrationTitle"), "")); 
        
        return "/login";
        
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
        mainAddress.setType(1);
        mainAddress.setRegion(r);
        mainAddress.setConsultant(consultant);
        AddressDAO adao=new AddressDAO();
        adao.save(mainAddress);
        
        //ADD INVOICE REGION
        r=rdao.getRegion(idInvoiceRegion);
        invoiceAddress.setType(2);
        invoiceAddress.setRegion(r);
        invoiceAddress.setConsultant(consultant);
        adao.save(invoiceAddress);
        
        //ADD INVOICEDATA
        invoiceData.setAddress(invoiceAddress);
        InvoiceDataDAO iddao=new InvoiceDataDAO();
        iddao.save(invoiceData);
        
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
        
        loginMB.setConsultant(cdao.getCounsultantConnectedToUser(user.getIdUser()));
        
        //CLEAR ALL FIELDS
        clearFields();

        return "/consultant/consultantMainPage?faces-redirect=true";
        
    }
    
    private void clearFields(){
        user = new User();
        consultant = new Consultant();
        confirmPassword = new String();
        subscription = new Subscription();
        //ConsultantFillAccountData
        mainAddress = new Address();
        invoiceAddress = new Address();
        idWorkingPlace=null;
        idSelectedBankList = new ArrayList<>();
        idSelectedInstitutionList = new ArrayList<>();
        idProductTypes = new ArrayList<>();
        idRegion=null;
        idMainRegion=null;
        idInvoiceRegion=null;
        invoiceData=new InvoiceData();
        idSubscriptionType=null;
        policy = false;
        policy2 = false;
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

    public Subscription getSubscription() {
        return subscription;
    }

    public void setSubscription(Subscription subscription) {
        this.subscription = subscription;
    }

    public InvoiceData getInvoiceData() {
        return invoiceData;
    }

    public void setInvoiceData(InvoiceData invoiceData) {
        this.invoiceData = invoiceData;
    }
    
    public Integer getIdSubscriptionType() {
        return idSubscriptionType;
    }

    public void setIdSubscriptionType(Integer idSubscriptionType) {
        this.idSubscriptionType = idSubscriptionType;
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
