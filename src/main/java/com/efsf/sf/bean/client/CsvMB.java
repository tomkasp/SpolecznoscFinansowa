package com.efsf.sf.bean.client;

import com.efsf.sf.bean.LoginMB;
import com.efsf.sf.sql.dao.GenericDao;
import com.efsf.sf.sql.entity.AmountHistory;
import com.efsf.sf.sql.entity.OperationType;
import com.efsf.sf.util.parsers.ParserCSV;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.text.ParseException;
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
    private InputStream csvInput;
    GenericDao<AmountHistory> dao = new GenericDao(AmountHistory.class);
    private List<AmountHistory> history;
    @ManagedProperty(value = "#{loginMB}")
    private LoginMB loginMB;
    private List<OperationType> operationTypes;

    public void upload() throws ParseException, IOException {
        if (csvFile != null) {
            FacesMessage msg = new FacesMessage("Sukces!", "Plik " + csvFile.getFileName() + " został pomyślnie przetworzony.");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            ParserCSV csv = new ParserCSV();
            csvInput = csvFile.getInputstream();
            csv.run(csvInput);
        }
    }

    @PostConstruct
    public void init() {
        history = dao.getWhere("fkClient", loginMB.getClient().getIdClient());
        GenericDao<OperationType> typeDao=new GenericDao(OperationType.class);
        operationTypes=typeDao.getAll();
    }
    
    public void save(){
        System.out.println("Tak :D");
        for(AmountHistory h: history)
        dao.update(h);
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

    public List<OperationType> getOperationTypes() {
        return operationTypes;
    }

    public void setOperationTypes(List<OperationType> operationTypes) {
        this.operationTypes = operationTypes;
    }
}
