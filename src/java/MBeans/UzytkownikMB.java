package MBeans;

import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import sql.dao.UzytkownikDao;
import sql.entity.Uzytkownik;

@ManagedBean(name="uzytkownik")
@SessionScoped
public class UzytkownikMB {

    private Uzytkownik user = new Uzytkownik();
    UzytkownikDao usrDao = new UzytkownikDao();
    
//    public String dodajUzytkownika(){
//        usrDao.dodajUzytkownika(user);
//        user = null;
//        return "accountCreated";
//    }
    
    public String zapiszUzytkownika(){
        usrDao.zapiszUzytkownika(user);
        user=null;
        return "accountTable";
    }
    
    public String nowyUzytkownik()
    {
        user=new Uzytkownik();
        return "accountForm";
    }
    
    public String edytujUzytkownik()
    {
        user.setHaslo(null);
        return "accountForm";
    }
    
    public void usunUzytkownika() {
        usrDao.usunUzytkownika(user);
    }
    
    
//    public String edytujUzytkownika(){
//        
//        user = usrDao.pobierzUzytkownika(this.getIdUzytkownika());
//        user = null;
//        return "edytujPanel";
//    }
    
    public List<Uzytkownik> pobierzListeUzytkownikow(){
        
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
