package com.efsf.sf.bean.client;

import com.efsf.sf.bean.LoginMB;
import com.efsf.sf.sql.dao.AmountHistoryDAO;
import com.efsf.sf.sql.dao.ClientCaseDAO;
import com.efsf.sf.sql.dao.GenericDao;
import com.efsf.sf.sql.entity.AmountHistory;
import com.efsf.sf.sql.entity.Client;
import com.efsf.sf.sql.entity.ClientCase;
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
import org.joda.time.LocalDate;
import org.primefaces.model.UploadedFile;

@ManagedBean
@ViewScoped
public class CsvMB implements Serializable {

    
    private UploadedFile csvFile;
    private Integer selectedBank = 1;
    GenericDao<AmountHistory> dao = new GenericDao(AmountHistory.class);
    private List<AmountHistory> history;
    @ManagedProperty(value = "#{loginMB}")
    private LoginMB loginMB;
    
    private Integer clientCaseId; 
    
    private Client client; 
    private List<OperationType> operationTypes;
    
    
    
    ParserCSV csv = new ParserCSV();
    
    private int month = new LocalDate().getMonthOfYear();
    
    private int year = new LocalDate().getYear();
  

    public void upload() throws ParseException, IOException {
        if (csvFile != null) {
            FacesMessage msg = new FacesMessage("Sukces!", "Plik " + csvFile.getFileName() + " został pomyślnie przetworzony.");
            FacesContext.getCurrentInstance().addMessage(null, msg);

            csv.run(csvFile.getInputstream(), client);       
            init();
        }
    }

    public void init() {
        AmountHistoryDAO amDao = new AmountHistoryDAO();
        history = amDao.getWithMonthAndYear(month, year, client);
        GenericDao<OperationType> typeDao=new GenericDao(OperationType.class);
        operationTypes=typeDao.getAll();
    }
    
    public boolean ifHistoryAdded()
    {
        List<AmountHistory> list = dao.getWhere("fkClient", client);
        if (list == null || list.isEmpty())
            return false;
        else
            return true;
    }
    
    public void loadData() throws IOException {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        if (!facesContext.isValidationFailed())
        {
            ClientCaseDAO cdao = new ClientCaseDAO();   
            if (loginMB.getClient() == null)
            {          
                     
                ClientCase clientCase =  cdao.getClientCaseWithClientDetails(clientCaseId);
                client = clientCase.getClient();
                if (!cdao.checkConsultantAccess(clientCaseId, loginMB.getConsultant().getIdConsultant())) 
                {
                       facesContext.getExternalContext().redirect("./../error.xhtml");
                }
            }
            else
            {
                client = loginMB.getClient();
            }
            if (ifHistoryAdded())
            {
                init();
            }
        }
        
    }
    public void save(){
        System.out.println("Tak :D");
        for(AmountHistory h: history)
        dao.update(h);
        
        FacesMessage msg = new FacesMessage("Sukces!", "Dane zostały zaktualizowane.");
        FacesContext.getCurrentInstance().addMessage(null, msg);  
    }
    
    public String showClientQualityOfLife()
    {
        return csv.calculateClientQualityOfLife(client);
    }
    
    public String showClientCreditChance()
    {
        return csv.calculateClientCreditChance(client);
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
    
    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Integer getClientCaseId() {
        return clientCaseId;
    }

    public void setClientCaseId(Integer clientCaseId) {
        this.clientCaseId = clientCaseId;
    }
}
