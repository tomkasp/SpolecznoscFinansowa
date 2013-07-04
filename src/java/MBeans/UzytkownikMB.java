package MBeans;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import sql.dao.UzytkownikDao;
import sql.entity.Uzytkownik;
import java.util.List;

@ManagedBean(name="uzytkownik")
@SessionScoped
public class UzytkownikMB {

    private Uzytkownik user = new Uzytkownik();
    
    
    public String dodajUzytkownika(){
        UzytkownikDao usrDao = new UzytkownikDao();
        usrDao.dodajUzytkownika(user);
        user = null;
        return "accountCreated";
    }
    
    public String edytujUzytkownika(){
        UzytkownikDao usrDao = new UzytkownikDao();
        int id=user.getUzytkownikId();
        user = usrDao.pobierzUzytkownika(id);
        user.setHaslo(null);
        return "formAccount";
    }
    
    public List<Uzytkownik> pobierzListeUzytkownikow(){
        UzytkownikDao usrDao = new UzytkownikDao();
        List<Uzytkownik> list = usrDao.pobierzListeUzytkownikow();
        return list;
    }
    
    
    public UzytkownikMB() {
    }

    public Uzytkownik getUser() {
        return user;
    }

    public void setUser(Uzytkownik user) {
        this.user = user;
    }

    //for what is that? 
    public String createAcc(){
        return "createAcc";
    }

}
