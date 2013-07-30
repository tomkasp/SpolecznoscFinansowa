/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.efsf.sf.bean;

import java.util.Date;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author XaI
 */
@ManagedBean
@SessionScoped
public class ClientFillAccountDataMB
{
    
    @ManagedProperty(value="#{loginMB}")
    private LoginMB loginMB;

    public LoginMB getLoginMB() {
        return loginMB;
    }

    public void setLoginMB(LoginMB loginMB) {
        this.loginMB = loginMB;
    }
    
    private String name;
    private String surname; 
    private String familyName;
    private Date birthDate;
    private int maritalStatus;
    private int education; 
    private Boolean sex;
    private String pesel;
    private int birthPlace;
    
    

    /**
     * Creates a new instance of ClientFillAccountDataMB
     */
    public ClientFillAccountDataMB() {
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
}
