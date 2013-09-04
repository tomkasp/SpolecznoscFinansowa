// Przycisk zobacz historię ma być nie aktywny
// Zapis hasła w ustawieniach zaraz po wciśnięciu klawisza
// Komunikat ma się odświeżać po kliknięciu na analizuj BIK
// Ma się ładować ostatni BIK a nie pierwszy
// BIK w wersji z obrazami

package com.efsf.sf.bean;

import com.efsf.sf.sql.dao.ClientDAO;
import com.efsf.sf.sql.dao.GenericDao;
import com.efsf.sf.sql.dao.RequiredDocumentsDAO;
import com.efsf.sf.sql.entity.Bik;
import com.efsf.sf.sql.entity.BikHistoriaRachunku;
import com.efsf.sf.sql.entity.BikRachunek;
import com.efsf.sf.sql.entity.BikZapytanie;
import com.efsf.sf.sql.entity.RequiredDocuments;
import com.efsf.sf.util.bik.Alghorithm;
import com.efsf.sf.util.ftp.FtpDownloader;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean
@SessionScoped
public class BikMB implements Serializable {

    private BikRachunek selectedAccount;
    
    private Integer clientId;
    private Integer bikId;
    
    public void parseBik(Integer clientId) throws IOException, Exception {
        
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
        this.clientId=clientId;
        selectedAccount=null;
        return "/common/bikView?faces-redirect=true";
    }
    
    public String viewAccountHistory(){
        
        return "/common/bikHistoryView?faces-redirect=true";
    }

    public Bik getBik() {
        GenericDao<Bik> dao=new GenericDao(Bik.class);
        Bik bik=dao.getLastWhere("clientId", String.valueOf(clientId), "idBik", "desc");
        bikId=bik.getIdBik();
        return bik;
    }


    public List<BikRachunek> getRachunki() {
        GenericDao<BikRachunek> dao=new GenericDao(BikRachunek.class);
        return dao.getWhere("bik_id", String.valueOf(bikId));
    }


    public List<BikZapytanie> getZapytania() {
        GenericDao<BikZapytanie> dao=new GenericDao(BikZapytanie.class);
        return dao.getWhere("bik_id", String.valueOf(bikId));
    }

    public BikRachunek getSelectedAccount() {
        return selectedAccount;
    }

    public void setSelectedAccount(BikRachunek selectedAccount) {
        this.selectedAccount = selectedAccount;
    }
    
    public List<BikHistoriaRachunku> getHistory(){
        GenericDao<BikHistoriaRachunku> dao=new GenericDao(BikHistoriaRachunku.class);
        return dao.getWhere("id_rachunek", String.valueOf(selectedAccount.getIdRachunek()));
    }


}