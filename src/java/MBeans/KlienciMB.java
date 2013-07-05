package MBeans;

import java.io.Serializable;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import sql.dao.KlienciDao;
import sql.entity.Klienci;

/**
 *
 * @author EI GLOBAL
 */
@ManagedBean
@SessionScoped
public class KlienciMB implements Serializable {
 
    private static final long serialVersionUID = 1L;
    private KlienciDao kdao = new KlienciDao();
    
    private List<Klienci> klientList;
    
    private Klienci selectedClient;
    
    private Klienci klient = new Klienci();
    
    @ManagedProperty(value = "#{logowanieMB}")
    private LogowanieMB logowanieMB;
    
    public KlienciMB() {}

    public String submit() {
        int idUzytkownika=logowanieMB.getIdUzytkownika();
        
        kdao.createOrUpdateKlient(klient,idUzytkownika);

        klient = null;
        return "klienciTable";
    }
    
    public void deleteClient() {
        kdao.deleteKlient(this.selectedClient);
    }

    public String newClient() {
        klient = new Klienci();
        return "indexstart";
    }

    public String selectedClientRedirect() {
        klient = selectedClient;
        return "indexstart";
    }

    public String previous() {
        return "index";
    }

    public Klienci getKlient() {
        return klient;
    }

    public void setKlient(Klienci klient) {
        this.klient = klient;
    }

    public List<Klienci> getKlientList() {
        klientList=kdao.getKlientList();
        return klientList;
    }

    public void setKlientList(List<Klienci> KlientList) {
        this.klientList = KlientList;
    }

    public Klienci getSelectedClient() {
        return selectedClient;
    }

    public void setSelectedClient(Klienci selectedClient) {
        this.selectedClient = selectedClient;
    }

    public LogowanieMB getLogowanieMB() {
        return logowanieMB;
    }

    public void setLogowanieMB(LogowanieMB logowanieMB) {
        this.logowanieMB = logowanieMB;
    }
    
    

}
