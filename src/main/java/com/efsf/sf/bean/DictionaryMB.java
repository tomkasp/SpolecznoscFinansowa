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

    
}
