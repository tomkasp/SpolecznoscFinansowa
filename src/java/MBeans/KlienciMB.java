package MBeans;

import java.io.Serializable;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import org.hibernate.Session;
import sql.dao.KlienciDao;
import sql.entity.Klienci;
import sql.entity.Uzytkownik;
import sql.util.HibernateUtil;

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
    
    public KlienciMB() {}

    public String submit() {
        //TRZY PONIZSZE LINIJKI SĄ TYMCZASOWE...
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction().begin();
        Uzytkownik u = (Uzytkownik) session.load(Uzytkownik.class, 4);
        u.getAktywne();
        //...CZYLI DO CZASU STWORZENIA UZYTKOWNICY DAO!!!!

        klient.setUzytkownik(u);
        kdao.createOrUpdateKlient(klient);

        klient = null;
        return "klienciTable";
    }
    
    public void deleteClient() {
        kdao.deleteKlient(this.selectedClient);
    }

    public String newClient() {
        klient = new Klienci();
        return "formKlienci";
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
    
    

}
