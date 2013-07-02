package MBeans;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import sql.dao.UzytkownikDao;
import sql.entity.Uzytkownik;


@ManagedBean(name="uzytkownik")
@SessionScoped
public class UzytkownikMB {

    private Uzytkownik user = new Uzytkownik();
    private int idUzytkownika;
    
    public String dodajUzytkownika(){
        UzytkownikDao usrDao = new UzytkownikDao();
        usrDao.dodajUzytkownika(user);
        return "accountCreated";
    }
    
    public String edytujUzytkownika(){
        UzytkownikDao usrDao = new UzytkownikDao();
        user = usrDao.pobierzUzytkownika(this.getIdUzytkownika());
        
        return "edytujPanel";
    }
    
    public UzytkownikMB() {
    }

    public Uzytkownik getUser() {
        return user;
    }

    public void setUser(Uzytkownik user) {
        this.user = user;
    }

    public int getIdUzytkownika() {
        return idUzytkownika;
    }

    public void setIdUzytkownika(int idUzytkownika) {
        this.idUzytkownika = idUzytkownika;
    }

  
    
    
}
