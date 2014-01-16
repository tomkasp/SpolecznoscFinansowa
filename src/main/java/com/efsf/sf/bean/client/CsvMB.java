package com.efsf.sf.bean.client;

import com.efsf.sf.bean.LoginMB;
import com.efsf.sf.sql.dao.GenericDao;
import com.efsf.sf.sql.entity.AmountHistory;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.primefaces.model.UploadedFile;

@ManagedBean
@ViewScoped
public class CsvMB implements Serializable {

     private UploadedFile csvFile;  
     
     private Integer selectedBank = -1; 
     
     
    
    GenericDao<AmountHistory> dao = new GenericDao(AmountHistory.class);
    
    private List<AmountHistory> history;
    
    @ManagedProperty(value = "#{loginMB}")
    private LoginMB loginMB;

    public void upload() {  
        if(csvFile != null) {  
            FacesMessage msg = new FacesMessage("Sukces!","Plik " + csvFile.getFileName() + " został pomyślnie przetworzony.");  
            FacesContext.getCurrentInstance().addMessage(null, msg);  
        }     
    }  
    
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
    


    public UploadedFile getCsvFile() {
        return csvFile;
    }

    public void setCsvFile(UploadedFile csvFile) {
        this.csvFile = csvFile;
    }

    public Integer getSelectedBank() {
        return selectedBank;
    }

    public void setSelectedBank(Integer selectedBank) {
        this.selectedBank = selectedBank;
    }
    
}
