package MBeans;

import java.util.ResourceBundle;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import sql.dao.UzytkownikDao;

@ManagedBean
@SessionScoped
public class LogowanieMB {

    private String login;
    private String haslo;
    private boolean zalogowany = false;
    private String message;

    public LogowanieMB() {
    }

    public String logowanie() {

        UzytkownikDao userDao = new UzytkownikDao();
        if (userDao.logowanie(this.login, this.haslo)) {
            System.out.println("zalogowany=========== MadejsoN 0.1");
            zalogowany = true;
            message = userDao.getMessage();
            System.out.println(zalogowany);
            return "klienciTable";
        } else {
            message = userDao.getMessage();
        }
        return "index";
    }

    public String wyloguj() {
        if(this.zalogowany == true){
            FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        }
        zalogowany = false;
        System.out.println(zalogowany);
        return "index";
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getHaslo() {
        return haslo;
    }

    public void setHaslo(String haslo) {
        this.haslo = haslo;
    }

    public boolean isZalogowany() {
        return zalogowany;
    }

    public void setZalogowany(boolean zalogowany) {
        this.zalogowany = zalogowany;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
