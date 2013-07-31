/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.efsf.sf.bean;

import com.efsf.sf.sql.dao.*;
import com.efsf.sf.sql.entity.*;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.model.SelectItem;

/**
 *
 * @author admin
 */
@ManagedBean
@ApplicationScoped
public class DictionaryMB {

    /**
     * Creates a new instance of DictionaryMB
     */
    
    private List<Region> region;
    private List<Education> education;
    private List<MaritalStatus> maritalStatus;
    private List<WorkingPlace> workingPlace;
    private ArrayList<SelectItem> workingPlaceMap;
    private List<CaseStatus> caseStatus;
    private List<EmploymentType> businessActivity;
    private List<EmploymentType> income;
    private List<Institution> bank;
    private List<Institution> institution;
    private List<Branch> branch;
    private List<ProductType> productType;
    private List<SubscriptionType> subscriptionType;
    
    
    public DictionaryMB() {
        RegionDAO reg = new RegionDAO();
        region = reg.regionList();
        
        EducationDAO edu = new EducationDAO();
        education = edu.educationList();
        
        MaritalStatusDAO ms = new MaritalStatusDAO();
        maritalStatus = ms.maritalStatusList();
        
        WorkingPlaceDAO wp = new WorkingPlaceDAO();
        workingPlace = wp.workingPlaceList();
        //workingPlaceMap = wp.workingPlaceMap();
        
        CaseStatusDAO cs = new CaseStatusDAO();
        caseStatus = cs.caseStatusList();  
        
        EmploymentTypeDAO e = new EmploymentTypeDAO();
        businessActivity = e.businessActivityList();
        income = e.incomeList();
    
        InstitutionDAO idao = new InstitutionDAO();
        bank = idao.bankList();
        institution = idao.institutionList();
        
        BranchDAO bdao = new BranchDAO();
        branch = bdao.branchList();
        
        ProductTypeDAO ptdao=new ProductTypeDAO();
        productType=ptdao.productTypeList();
        
        SubscriptionTypeDAO stdao=new SubscriptionTypeDAO();
        subscriptionType=stdao.subscriptionTypeList();
    }

    public List<Region> getRegion() {
        return region;
    }

    public List<Education> getEducation() {
        return education;
    }

    public List<MaritalStatus> getMaritalStatus() {
        return maritalStatus;
    }

    public List<WorkingPlace> getWorkingPlace() {
        return workingPlace;
    }

    public ArrayList<SelectItem> getWorkingPlaceMap() {
        return workingPlaceMap;
    }

    public void setWorkingPlaceMap(ArrayList<SelectItem> workingPlaceMap) {
        this.workingPlaceMap = workingPlaceMap;
    }
    
    public List<CaseStatus> getCaseStatus() {
        return caseStatus;
    }

    public List<EmploymentType> getBusinessActivity() {
        return businessActivity;
    }

    public List<EmploymentType> getIncome() {
        return income;
    }

    public List<Institution> getBank() {
        return bank;
    }

    public List<Institution> getInstitution() {
        return institution;
    }

    public List<Branch> getBranch() {
        return branch;
    }

    public List<ProductType> getProductType() {
        return productType;
    }

    public void setProductType(List<ProductType> productType) {
        this.productType = productType;
    }

    public List<SubscriptionType> getSubscriptionType() {
        return subscriptionType;
    }

    public void setSubscriptionType(List<SubscriptionType> subscriptionType) {
        this.subscriptionType = subscriptionType;
    }

    

    
    
}
