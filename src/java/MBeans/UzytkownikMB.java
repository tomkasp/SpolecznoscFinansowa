package MBeans;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import sql.dao.UzytkownikDao;


@ManagedBean(name="uzytkownik")
@SessionScoped
public class UzytkownikMB {

    private String imie;
    private String nazwisko;
    
    private String login;
    private String haslo;
    
    private String oddzial;
    private boolean aktywny;
    
    private String message;
    
    /**
     * Creates a new instance of UzytkownikMB
     */
    
    public void dodajUzytkownika(){
        UzytkownikDao usrDao = new UzytkownikDao();
        usrDao.dodajUzytkownika(login, haslo, imie, nazwisko, oddzial);
        
    }
    
    
    
    
    public UzytkownikMB() {
    }

    public String getImie() {
        return imie;
    }

    public void setImie(String imie) {
        this.imie = imie;
    }

    public String getNazwisko() {
        return nazwisko;
    }

    public void setNazwisko(String nazwisko) {
        this.nazwisko = nazwisko;
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

    public String getOddzial() {
        return oddzial;
    }

    public void setOddzial(String oddzial) {
        this.oddzial = oddzial;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
 
    
    
    
}
