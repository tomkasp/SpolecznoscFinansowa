package com.efsf.sf.bean;

import com.efsf.sf.sql.entity.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

@ManagedBean
@SessionScoped
public class ClientMainPageMB implements Serializable {
    private static final long serialVersionUID = 1L;

   @ManagedProperty(value="#{loginMB}")
    private LoginMB loginMB;
    
    
    private List<ClientCase> clientCaseList = new ArrayList();

    private ClientCase selectedCase;
    
}
