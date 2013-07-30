/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.efsf.sf.bean;

import com.efsf.sf.sql.dao.*;
import com.efsf.sf.sql.entity.*;
import java.util.List;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

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
    private List<CaseStatus> caseStatus;
    private List<EmploymentType> businessActivity;
    private List<EmploymentType> income;
    
    
    public DictionaryMB() {
        RegionDAO reg = new RegionDAO();
        region = reg.regionList();
        
        EducationDAO edu = new EducationDAO();
        education = edu.educationList();
        
        MaritalStatusDAO ms = new MaritalStatusDAO();
        maritalStatus = ms.maritalStatusList();
        
        WorkingPlaceDAO wp = new WorkingPlaceDAO();
        workingPlace = wp.regionList();
        
        CaseStatusDAO cs = new CaseStatusDAO();
        caseStatus = cs.caseStatusList();  
        
        EmploymentTypeDAO e = new EmploymentTypeDAO();
        businessActivity = e.businessActivityList();
        income = e.incomeList();
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

    public List<CaseStatus> getCaseStatus() {
        return caseStatus;
    }

    public void setRegion(List<Region> region) {
        this.region = region;
    }

    public void setEducation(List<Education> education) {
        this.education = education;
    }

    public void setMaritalStatus(List<MaritalStatus> maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public void setWorkingPlace(List<WorkingPlace> workingPlace) {
        this.workingPlace = workingPlace;
    }

    public void setCaseStatus(List<CaseStatus> caseStatus) {
        this.caseStatus = caseStatus;
    }

    public List<EmploymentType> getBusinessActivity() {
        return businessActivity;
    }

    public List<EmploymentType> getIncome() {
        return income;
    }

    public void setBusinessActivity(List<EmploymentType> businessActivity) {
        this.businessActivity = businessActivity;
    }

    public void setIncome(List<EmploymentType> income) {
        this.income = income;
    }

    
}
