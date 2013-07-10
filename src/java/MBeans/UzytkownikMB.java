package MBeans;

import java.io.Serializable;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import sql.dao.UzytkownikDao;
import sql.entity.Uzytkownik;

@ManagedBean(name="uzytkownik")
@SessionScoped
public class UzytkownikMB implements Serializable {
    private static final long serialVersionUID = 1L;

    private Uzytkownik user = new Uzytkownik();
    UzytkownikDao usrDao = new UzytkownikDao();
    
    boolean isEdit=false;
    
    public String zapiszUzytkownika(){
        usrDao.zapiszUzytkownika(user);
        user=null;
        isEdit=false;
        return "accountTable";
    }
    
    public String nowyUzytkownik()
    {
        user=new Uzytkownik();
        isEdit=false;
        return "accountForm";
    }
    
    public String edytujUzytkownik()
    {
        user.setHaslo(null);
        isEdit=true;
        return "accountForm";
    }
    
    public void usunUzytkownika() {
        usrDao.usunUzytkownika(user);
    }
 
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

    public boolean isIsEdit() {
        return isEdit;
    }

    public void setIsEdit(boolean isEdit) {
        this.isEdit = isEdit;
    }
    
    

}
