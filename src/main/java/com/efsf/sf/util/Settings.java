/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.efsf.sf.util;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

/**
 *
 * @author XaI
 */
@ManagedBean
@ApplicationScoped
public class Settings 
{
    // USER TYPES!! 
    public static final Integer ADMIN_UNVERIFIED = 10;
    public static final Integer ADMIN_ACTIVE = 11;
    public static final Integer ADMIN_INACTIVE = 12;
    public static final Integer CONSULTANT_UNVERIFIED = 20;
    public static final Integer CONSULTANT_ACTIVE = 21;
    public static final Integer CONSULTANT_INACTIVE = 22;
    public static final Integer CLIENT_UNVERIFIED = 30;
    public static final Integer CLIENT_ACTIVE = 31;
    public static final Integer CLIENT_INACTIVE = 32;  
    public static final Integer LOGGED_OUT = -1;
    
    
    // PRODUCT TYPES 
    public static final Integer CHWILOWKA = 2;

    
    
    
    
    //EL FIELDS
    
    private Integer adminUnverified = ADMIN_UNVERIFIED;
    private Integer adminActive = ADMIN_ACTIVE;
    private Integer adminInactive = ADMIN_INACTIVE;
    private Integer consultantUnverified = CONSULTANT_UNVERIFIED;
    private Integer consultantActive = CONSULTANT_ACTIVE;
    private Integer consultantInactive = CONSULTANT_INACTIVE;
    private Integer clientUnverified = CLIENT_UNVERIFIED;
    private Integer clientActive = CLIENT_ACTIVE;
    private Integer clientInactive = CLIENT_INACTIVE;
    private Integer loggedOut = LOGGED_OUT;

    public Integer getAdminUnverified() {
        return adminUnverified;
    }

    public void setAdminUnverified(Integer adminUnverified) {
        this.adminUnverified = adminUnverified;
    }

    public Integer getAdminActive() {
        return adminActive;
    }

    public void setAdminActive(Integer adminActive) {
        this.adminActive = adminActive;
    }

    public Integer getAdminInactive() {
        return adminInactive;
    }

    public void setAdminInactive(Integer adminInactive) {
        this.adminInactive = adminInactive;
    }

    public Integer getConsultantUnverified() {
        return consultantUnverified;
    }

    public void setConsultantUnverified(Integer consultantUnverified) {
        this.consultantUnverified = consultantUnverified;
    }

    public Integer getConsultantActive() {
        return consultantActive;
    }

    public void setConsultantActive(Integer consultantActive) {
        this.consultantActive = consultantActive;
    }

    public Integer getConsultantInactive() {
        return consultantInactive;
    }

    public void setConsultantInactive(Integer consultantInactive) {
        this.consultantInactive = consultantInactive;
    }

    public Integer getClientUnverified() {
        return clientUnverified;
    }

    public void setClientUnverified(Integer clientUnverified) {
        this.clientUnverified = clientUnverified;
    }

    public Integer getClientActive() {
        return clientActive;
    }

    public void setClientActive(Integer clientActive) {
        this.clientActive = clientActive;
    }

    public Integer getClientInactive() {
        return clientInactive;
    }

    public void setClientInactive(Integer clientInactive) {
        this.clientInactive = clientInactive;
    }

    public Integer getLoggedOut() {
        return loggedOut;
    }

    public void setLoggedOut(Integer loggedOut) {
        this.loggedOut = loggedOut;
    }
            
    
}
