package com.efsf.sf.bean;

import com.efsf.sf.sql.dao.ClientDAO;
import com.efsf.sf.sql.dao.GenericDao;
import com.efsf.sf.sql.dao.RequiredDocumentsDAO;
import com.efsf.sf.sql.entity.Bik;
import com.efsf.sf.sql.entity.BikAccountHistory;
import com.efsf.sf.sql.entity.BikAccount;
import com.efsf.sf.sql.entity.BikQuestion;
import com.efsf.sf.sql.entity.RequiredDocuments;
import com.efsf.sf.util.bik.Alghorithm;
import com.efsf.sf.util.ftp.FtpDownloader;
import java.io.Serializable;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean
@SessionScoped
public class BikMB implements Serializable {

    private BikAccount selectedAccount;
      
    private Bik bik;
    private List<BikAccount> rachunki;
    private List<BikQuestion> zapytania;
    private List<BikAccountHistory> historia;
    
    public void parseBik(Integer clientId) throws Exception {
        
        RequiredDocumentsDAO requiredDocumentsDAO = new RequiredDocumentsDAO();
        RequiredDocuments doc=requiredDocumentsDAO.readForFkClient(clientId);
        String password=doc.getBikPassword();
        
        ClientDAO clientDao=new ClientDAO();
        Integer userId=clientDao.read(clientId).getUser().getIdUser();
        
        
        FtpDownloader ftp = new FtpDownloader();
        String file=ftp.downloadBik(userId, doc.getBik());
        
        Alghorithm alg = new Alghorithm(file, password, clientId);
                
        //UPDATE FLAG TO PROCESSING
        alg.setStatus(1);
        
        //PARSE BIK
        alg.start();
        
    }
    
    public String showBik(Integer clientId){
        setSelectedAccount(null);
        
        GenericDao<Bik> dao=new GenericDao(Bik.class);
        setBik(dao.getLastWhere("clientId", String.valueOf(clientId), "idBik", "desc"));
        Integer bikId=getBik().getIdBik();
        
        GenericDao<BikAccount> daoRachunki=new GenericDao(BikAccount.class);
        setRachunki(daoRachunki.getWhere("bik_id", String.valueOf(bikId)));
        
        GenericDao<BikQuestion> daoZapytania=new GenericDao(BikQuestion.class);
        setZapytania(daoZapytania.getWhere("bik_id", String.valueOf(bikId)));
        
        return "/common/bikView?faces-redirect=true";
    }
    
    public String viewAccountHistory(){
        GenericDao<BikAccountHistory> dao=new GenericDao(BikAccountHistory.class);
        setHistoria(dao.getWhere("id_account", String.valueOf(getSelectedAccount().getIdAccount())));
        
        return "/common/bikHistoryView?faces-redirect=true";
    }

    public BikAccount getSelectedAccount() {
        return selectedAccount;
    }

    public void setSelectedAccount(BikAccount selectedAccount) {
        this.selectedAccount = selectedAccount;
    }
    
    public Bik getBik() {
        return bik;
    }

    public void setBik(Bik bik) {
        this.bik = bik;
    }

    public List<BikAccount> getRachunki() {
        return rachunki;
    }

    public void setRachunki(List<BikAccount> rachunki) {
        this.rachunki = rachunki;
    }

    public List<BikQuestion> getZapytania() {
        return zapytania;
    }

    public void setZapytania(List<BikQuestion> zapytania) {
        this.zapytania = zapytania;
    }

    public List<BikAccountHistory> getHistoria() {
        return historia;
    }

    public void setHistoria(List<BikAccountHistory> historia) {
        this.historia = historia;
    }


}