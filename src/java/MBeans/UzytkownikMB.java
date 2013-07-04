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
    private Uzytkownik selectedUser = new Uzytkownik();
    private int idUzytkownika;
    UzytkownikDao usrDao = new UzytkownikDao();
    
//    public String dodajUzytkownika(){
//        usrDao.dodajUzytkownika(user);
//        user = null;
//        return "accountCreated";
//    }
    
    public String zapiszUzytkownika(Uzytkownik usr){
        usrDao.zapiszUzytkownika(usr);
        
        return "zapisanoUzytkownika";
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

    public int getIdUzytkownika() {
        return idUzytkownika;
    }

    public void setIdUzytkownika(int idUzytkownika) {
        this.idUzytkownika = idUzytkownika;
    }

    public Uzytkownik getSelectedUser() {
        return selectedUser;
    }

    public void setSelectedUser(Uzytkownik selectedUser) {
        this.selectedUser = selectedUser;
    }
    
    //for what is that? 
    public String createAcc(){
        return "createAcc";
    }

}
