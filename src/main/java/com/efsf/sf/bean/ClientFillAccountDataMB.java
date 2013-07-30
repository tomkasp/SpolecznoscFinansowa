/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.efsf.sf.bean;

import com.efsf.sf.collection.IncomeData;
import com.efsf.sf.sql.entity.Address;
import com.efsf.sf.sql.entity.Branch;
import com.efsf.sf.sql.entity.EmploymentType;
import com.efsf.sf.sql.entity.Income;
import com.efsf.sf.sql.entity.IncomeBuisnessActivity;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ActionEvent;

/**
 *
 * @author XaI
 */
@ManagedBean
@SessionScoped
public class ClientFillAccountDataMB implements Serializable
{
    private static final long serialVersionUID = 1L;
   
    @ManagedProperty(value="#{loginMB}")
    private LoginMB loginMB;
    
    @ManagedProperty(value="#{dictionaryMB}")
    private DictionaryMB dictionaryMB;


    
    //General Information
   
    private String name;
    private String surname; 
    private String familyName;
    private Date birthDate;
    private int maritalStatus;
    private int education; 
    private Boolean sex;
    private String pesel;
    private int birthPlace;
    
    //Income
    
    private IncomeData currentIncomeData;
    private ArrayList<IncomeData> incomeTable = new ArrayList<IncomeData>();
    private Income income = new Income();
    private IncomeBuisnessActivity business = new IncomeBuisnessActivity();
     
    //Fields used to make select menus working
    
    private int incomeId;
    private int branchId;
    private boolean isIncome = false;
    
    
    //Address Data
    
    private Address address = new Address();
    
    

    /**
     * Creates a new instance of ClientFillAccountDataMB
     */
    
    public ClientFillAccountDataMB()
    {
        
    }
    
    @PostConstruct
    public void fillFields()
    {
//        if (loginMB != null)
//          name = loginMB.getClient().getName();      
    }
    
    public void cleanDialog()
    {
       
        income = new Income();
        branchId = 0;
        incomeId = 0;
        
        System.out.println("Wyczyściło");
    }
    
    public void addIncome()
    {
        
    }
    
    public void toIncome()
    {
        setIsIncome(true);
        System.out.println(isIncome);
    }
    
    public void toBusinessActivity()
    {
        setIsIncome(false);
        System.out.println(isIncome);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getFamilyName() {
        return familyName;
    }

    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public int getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(int maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public int getEducation() {
        return education;
    }

    public void setEducation(int education) {
        this.education = education;
    }

    public Boolean getSex() {
        return sex;
    }

    public void setSex(Boolean sex) {
        this.sex = sex;
    }

    public String getPesel() {
        return pesel;
    }

    public void setPesel(String pesel) {
        this.pesel = pesel;
    }

    public int getBirthPlace() {
        return birthPlace;
    }

    public void setBirthPlace(int birthPlace) {
        this.birthPlace = birthPlace;
    }

    public boolean isIsIncome() {
        return isIncome;
    }

    public void setIsIncome(boolean isIncome) {
        this.isIncome = isIncome;
    }
    
    public LoginMB getLoginMB() {
        return loginMB;
    }

    public void setLoginMB(LoginMB loginMB) {
        this.loginMB = loginMB;
    }
    
    public IncomeData getCurrentIncomeData() {
        return currentIncomeData;
    }

    public void setCurrentIncomeData(IncomeData currentIncomeData) {
        this.currentIncomeData = currentIncomeData;
    }

    public Income getIncome() {
        return income;
    }

    public void setIncome(Income income) {
        this.income = income;
    }
    
      public DictionaryMB getDictionaryMB() {
        return dictionaryMB;
    }

    public void setDictionaryMB(DictionaryMB dictionaryMB) {
        this.dictionaryMB = dictionaryMB;
    }

    public int getIncomeId() {
        return incomeId;
    }

    public void setIncomeId(int incomeId) {
        this.incomeId = incomeId;
    }

    public int getBranchId() {
        return branchId;
    }

    public void setBranchId(int branchId) {
        this.branchId = branchId;
    }

    public IncomeBuisnessActivity getBusiness() {
        return business;
    }

    public void setBusiness(IncomeBuisnessActivity business) {
        this.business = business;
    }

    public ArrayList<IncomeData> getIncomeTable() {
        return incomeTable;
    }

    public void setIncomeTable(ArrayList<IncomeData> incomeTable) {
        this.incomeTable = incomeTable;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
    
}
