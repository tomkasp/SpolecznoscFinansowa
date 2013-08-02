package com.efsf.sf.bean;

import com.efsf.sf.sql.dao.*;
import com.efsf.sf.sql.entity.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;

/**
 * @author WR1EI1
 */
@ManagedBean
@SessionScoped
public class ConsultantSettingsMB implements Serializable {
    private static final long serialVersionUID = 1L;
    //Update Settings
    @ManagedProperty(value="#{loginMB.consultant.idConsultant}")
    private Integer idConsultant;
    
    private Consultant consultant; 
    private String confirmPassword = new String();
    
    private Integer idWorkingPlace;
    private List<Integer> idSelectedBankList = new ArrayList<>();
    private List<Integer> idSelectedInstitutionList = new ArrayList<>();
    private List<Integer> idProductTypes = new ArrayList<>();
    private Integer idRegion;
    private Integer idMainRegion;
    private Integer idInvoiceRegion;
    private Integer idSubscriptionType;
    
    public ConsultantSettingsMB() {
        ConsultantDAO cdao=new ConsultantDAO();
        consultant=cdao.readConsultantForSettings(idConsultant);
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
        //ADD MAIN REGION
        r=rdao.getRegion(idMainRegion);
        
        AddressDAO adao=new AddressDAO();
        
        //ADD INVOICE REGION
        r=rdao.getRegion(idInvoiceRegion);
        
        //ADD SUBSCRIPTION TYPE
        if(idSubscriptionType!=null)
        {
        SubscriptionTypeDAO stdao = new SubscriptionTypeDAO();
        SubscriptionType subscriptionType = stdao.getSubscriptionType(idSubscriptionType);
        
        SubscriptionDAO sdao=new SubscriptionDAO();
        }
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

    public Integer getIdSubscriptionType() {
        return idSubscriptionType;
    }

    public void setIdSubscriptionType(Integer idSubscriptionType) {
        this.idSubscriptionType = idSubscriptionType;
    }
   
}