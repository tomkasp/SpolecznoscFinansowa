package com.efsf.sf.bean;

import com.efsf.sf.sql.dao.*;
import com.efsf.sf.sql.entity.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;

/**
 * @author WR1EI1
 */

@ManagedBean
@RequestScoped
public class ConsultantSettingsMB implements Serializable {
    private static final long serialVersionUID = 1L;
    //Update Settings
    
    @ManagedProperty(value="#{loginMB.consultant.idConsultant}")
    private Integer idConsultant;
   
    private Consultant consultant; 
    private String confirmPassword;
    
    private Integer idWorkingPlace;
    private List<Integer> idSelectedBankList = new ArrayList<>();
    private List<Integer> idSelectedInstitutionList = new ArrayList<>();
    private List<Integer> idProductTypes = new ArrayList<>();
    
    private Integer idRegion;
    private Integer idMainRegion;
    private Integer idInvoiceRegion;
    private Address mainAddress = new Address();
    private Address invoiceAddress = new Address();
    private InvoiceData invoiceData;
    
    private Integer idSubscriptionType;
    private Subscription subscription;
    
    public ConsultantSettingsMB() { 
    }
 
    @PostConstruct
    private void loadConsultant() {
        ConsultantDAO cdao=new ConsultantDAO();
        consultant=cdao.readConsultantForSettings( idConsultant );
        //System.out.println("IDEK: "+consultant.getWorkingPlace().getIdWorkingPlace());
        idWorkingPlace=consultant.getWorkingPlace().getIdWorkingPlace();
        //idSelectedBankList = consultant.getRegion().
               
        Iterator<Institution> it=consultant.getInstitutions().iterator();
        while(it.hasNext())
        {
            Institution i=it.next();
            if(i.getType()==0)
            { idSelectedBankList.add( i.getIdInstitution() ); }
            if(i.getType()==1)
            { idSelectedInstitutionList.add( i.getIdInstitution() ); }       
        }
        
        Iterator<ProductType> it2=consultant.getProductTypes().iterator();
        while(it2.hasNext())
        {
            ProductType pt=it2.next();
            idProductTypes.add( pt.getIdProductType() );          
        }
        
        
        
        idWorkingPlace=consultant.getWorkingPlace().getIdWorkingPlace();
        
        idRegion=consultant.getRegion().getIdRegion();
        
        InvoiceDataDAO iddao=new InvoiceDataDAO();
        
        
        Iterator<Address> it3=consultant.getAddresses().iterator();
        
        while(it3.hasNext())
        {
            Address a=it3.next();
            
            if(a.getType()==1)
            {   
                
                mainAddress=a;
                idMainRegion=mainAddress.getRegion().getIdRegion();
                //idMainRegion=1;
            }
            if(a.getType()==2)
            {  
                  
                invoiceAddress=a;
                idInvoiceRegion=invoiceAddress.getRegion().getIdRegion();
                //idInvoiceRegion=1;
                //I PRZY OKAZJI:
                invoiceData=iddao.loadFromFkAddress(a.getIdAddress());
            } 
           
        }
       
        
        Iterator<Subscription> it4=consultant.getSubscriptions().iterator();
        while(it4.hasNext())
        {
            Subscription s=it4.next();
            if(!it4.hasNext())//ZAWSZE ZWRACA OSTATNI ABONAMENT
            {
                idSubscriptionType=s.getIdSubscription();
                subscription=s;
            }
        }   
    }
    
    public String updateSettings() {

        DictionaryMB dictionaryMB = new DictionaryMB();
        InstitutionDAO idao=new InstitutionDAO();
        ProductTypeDAO ptdao=new ProductTypeDAO();
        RegionDAO rdao=new RegionDAO();
        
        WorkingPlace wp = dictionaryMB.getWorkingPlace().get(idWorkingPlace - 1);
        consultant.setWorkingPlace(wp);
        //HERE:
        
        
        
        //ADD BANKS
        Set<Institution> institutionSet = new HashSet<>();
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
        
        
        Set<Address> addressSet = new HashSet<>();
        
        Region mr=rdao.getRegion(idMainRegion);
        mainAddress.setRegion(mr);
        addressSet.add(mainAddress);
        
        Region ir=rdao.getRegion(idInvoiceRegion);
        invoiceAddress.setRegion(ir);
        addressSet.add(invoiceAddress);
        
        consultant.setAddresses(addressSet);
        
        AddressDAO adao=new AddressDAO();
        adao.update(mainAddress);
        adao.update(invoiceAddress);
        
       
        invoiceData.setAddress(invoiceAddress);
        InvoiceDataDAO iddao=new InvoiceDataDAO();
        iddao.update(invoiceData);
        
        
        //UPDATE CONSULTANT
        ConsultantDAO cdao = new ConsultantDAO();
        cdao.update(consultant);
        //UPDATE USER

        return "/consultant/consultantMainPage?faces-redirect=true";
    }
    
    public void validateSamePassword(FacesContext context, UIComponent toValidate, Object value) 
    {       
        String password = (String) value;
        if (!password.equals(confirmPassword)) {
             FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Hasła nie pasują!", "Hasła nie pasują!");
        throw new ValidatorException(message);
        }      
    }

    public Integer getIdConsultant() {
        return idConsultant;
    }

    public void setIdConsultant(Integer idConsultant) {
        this.idConsultant = idConsultant;
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

    public Subscription getSubscription() {
        return subscription;
    }

    public void setSubscription(Subscription subscription) {
        this.subscription = subscription;
    }
    
}