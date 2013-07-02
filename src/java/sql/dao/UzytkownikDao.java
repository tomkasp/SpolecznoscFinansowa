package sql.dao;

import java.security.NoSuchAlgorithmException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.context.FacesContext;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.QueryException;
import org.hibernate.Session;
import org.hibernate.exception.JDBCConnectionException;
import sql.entity.Uzytkownik;
import sql.util.HibernateUtil;
import sql.util.Security;

public class UzytkownikDao {

    private String message = "";

    public Boolean logowanie(String login, String haslo) {
        
        Session session = null ;
        
        try {
            session = HibernateUtil.getSessionFactory().openSession();

            Query q = null;
            try {
                q = session.createQuery("FROM Uzytkownik WHERE login = :login AND haslo = :password ");
                q.setParameter("login", login);
                q.setParameter("password", Security.sha1(haslo));
            } catch (QueryException exp) {
            }

            System.out.println(q);

            if (q.list().isEmpty()) {
                Query q2 = session.createQuery("FROM Uzytkownik WHERE login = :login ");
                q2.setParameter("login", login);
                if (q2.list().isEmpty()) {
                    FacesContext context = FacesContext.getCurrentInstance();
                    ResourceBundle bundle = context.getApplication().getResourceBundle(context, "msg");
                    message = bundle.getString("failed1");
                }

                Query q3 = session.createQuery("FROM Uzytkownik WHERE haslo = :password ");
                q3.setParameter( "password", Security.sha1(haslo) );
                if (q3.list().isEmpty()) {
                    FacesContext context = FacesContext.getCurrentInstance();
                    ResourceBundle bundle = context.getApplication().getResourceBundle(context, "msg");
                    message = bundle.getString("failed2");
                }

                return false;
            } else {
                String message = "";
                return true;
            }

        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(UzytkownikDao.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JDBCConnectionException e) {
            
            FacesContext context = FacesContext.getCurrentInstance();
            ResourceBundle bundle = context.getApplication().getResourceBundle(context, "msg");
            message = bundle.getString("failed3");
            
        } catch (QueryException exp) {
        } finally {
            System.out.println(message);
            session.close();
        }
        
           
        return false;
    }

    
    public void dodajUzytkownika(Uzytkownik user){

        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        
        Uzytkownik us = user;
        session.save(us);
        
        session.getTransaction().commit();
        session.close();
        
    }
    
    public void edytujUzytkownika(Uzytkownik user){
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        
        session.saveOrUpdate(user);
        
        session.getTransaction().commit();
        session.close();
    }
    
    public Uzytkownik pobierzUzytkownika(Integer idUzytkownika){
        Session session = HibernateUtil.getSessionFactory().openSession();
        
        Uzytkownik user = (Uzytkownik)session.load(Uzytkownik.class, idUzytkownika);
        
        System.out.println(user.getImie());
        session.close();
        
        return user;
    }
    
    
    
    
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
