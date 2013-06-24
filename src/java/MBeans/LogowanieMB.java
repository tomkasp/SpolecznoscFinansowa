package MBeans;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import sql.dao.UzytkownikDao;

@ManagedBean
@SessionScoped
public class LogowanieMB {

    private String login;
    private String haslo;
    
    
    
    public LogowanieMB() {
    }

    
    public boolean logowanie(){
        
        UzytkownikDao user = new UzytkownikDao();
        if(user.logowanie(this.login, this.haslo)){
            System.out.println("zalogowany=========== MadejsoN 0.1");
            return true;
        }
        return false;
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
    
    
    
    
    
    
    
    
}
