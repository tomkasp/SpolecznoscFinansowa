package MBeans;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import sql.dao.UzytkownikDao;
import sql.entity.Uzytkownik;


@ManagedBean(name="uzytkownik")
@SessionScoped
public class UzytkownikMB {

    private Uzytkownik user = new Uzytkownik();
    
    public void dodajUzytkownika(){
        UzytkownikDao usrDao = new UzytkownikDao();
        usrDao.dodajUzytkownika(user);
    }
    
    public void edytujUzytkownika(Integer idUzytkownika){
        
    }
    
    public UzytkownikMB() {
    }

    public Uzytkownik getUser() {
        return user;
    }

    public void setUser(Uzytkownik user) {
        this.user = user;
    }

  
    
    
}
