package com.efsf.sf.bean.client;

import com.efsf.sf.bean.LoginMB;
import com.efsf.sf.sql.dao.GenericDao;
import com.efsf.sf.sql.entity.AmountHistory;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

@ManagedBean
@ViewScoped
public class CsvMB implements Serializable {

    GenericDao<AmountHistory> dao = new GenericDao(AmountHistory.class);
    
    private List<AmountHistory> history;
    
    @ManagedProperty(value = "#{loginMB}")
    private LoginMB loginMB;

    @PostConstruct
    public void init() {
        history=dao.getWhere("fkClient", loginMB.getClient().getIdClient());
    }

    public List<AmountHistory> getHistory() {
        return history;
    }

    public void setHistory(List<AmountHistory> history) {
        this.history = history;
    }

    public LoginMB getLoginMB() {
        return loginMB;
    }

    public void setLoginMB(LoginMB loginMB) {
        this.loginMB = loginMB;
    }
    
    
}
