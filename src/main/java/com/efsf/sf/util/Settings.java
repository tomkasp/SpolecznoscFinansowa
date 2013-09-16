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
    
    // FTP PROPERTY
    public static final String FTP_SERVER = "192.168.0.5";
    public static final int FTP_PORT = 89;
    public static final String FTP_USER = "sf_ftp";
    public static final String FTP_PASS = "sf_ftp123";
    public static final String FTP_PATH="SF/USERS/";
    
    
    // PRODUCT TYPES 
    public static final Integer CHWILOWKA = 2;
       
        
    // CASE STATUSES 
    
    public static final Integer PRODUCT_ASSIGNED = 3; 
    
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



    public Integer getAdminActive() {
        return adminActive;
    }


    public Integer getAdminInactive() {
        return adminInactive;
    }


    public Integer getConsultantUnverified() {
        return consultantUnverified;
    }


    public Integer getConsultantActive() {
        return consultantActive;
    }


    public Integer getConsultantInactive() {
        return consultantInactive;
    }


    public Integer getClientUnverified() {
        return clientUnverified;
    }


    public Integer getClientActive() {
        return clientActive;
    }


    public Integer getClientInactive() {
        return clientInactive;
    }


    public Integer getLoggedOut() {
        return loggedOut;
    }

            
    
}
