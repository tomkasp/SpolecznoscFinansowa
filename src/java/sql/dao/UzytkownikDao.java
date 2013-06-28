package sql.dao;

import java.security.NoSuchAlgorithmException;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import sql.util.NewHibernateUtil;
import sql.util.Security;

public class UzytkownikDao {

    private String message="";
    
    public Boolean logowanie(String login, String haslo) {
        
        
        try {
            Session session = NewHibernateUtil.getSessionFactory().openSession();

            System.out.println("probuje logowac");
            Query q = session.createQuery("FROM Uzytkownik WHERE login='" + login + "' AND haslo='" + Security.sha1(haslo) + "' ");

            System.out.println(q);
            if (q.list().isEmpty()) {
                Query q2 = session.createQuery("FROM Uzytkownik WHERE login='" + login +" ");
                message="Brak użytkownika";
                if(q2.list().isEmpty())
                {
                message="bledne haslo";
                }
                
                Query q3 = session.createQuery("FROM Uzytkownik WHERE haslo='" + Security.sha1(haslo) +" ");
                if(q3.list().isEmpty())
                {
                message="bledny login";
                }
                
                return false;
            } else {
                String message="";
                message="logowanie przebiegli poprawnie";
                return true;
            }
            
            
        } catch (HibernateException | NoSuchAlgorithmException e) {
            message="Błąd połączenia z bazą";
            System.out.println("test");
        }
        finally {
                System.out.println(message);
        }

        //session.close();    
        return false;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    
}
