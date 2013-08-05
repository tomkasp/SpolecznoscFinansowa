package com.efsf.sf.bean;

import com.efsf.sf.sql.entity.*;
import java.io.Serializable;
import java.util.Date;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

@ManagedBean
@SessionScoped
public class ClientMainPageMB implements Serializable {
    private static final long serialVersionUID = 1L;

    @ManagedProperty(value = "#{loginMB}")
    private LoginMB login;
    private int idTypProduktu;
    private ClientCase clientCase = new ClientCase();
    private Date currentDate = new Date();
    private Boolean addNewApp = true;

   
    
}
