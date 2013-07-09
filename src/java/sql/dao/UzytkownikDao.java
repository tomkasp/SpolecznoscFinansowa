package sql.dao;

import java.io.Serializable;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.context.FacesContext;
import org.hibernate.Query;
import org.hibernate.QueryException;
import org.hibernate.Session;
import org.hibernate.exception.JDBCConnectionException;
import sql.entity.Uzytkownik;
import sql.util.HibernateUtil;
import sql.util.Security;

public class UzytkownikDao implements Serializable {

    private String message = "";
    private String rola;
    private int idUzytkownika;

    @SuppressWarnings("unchecked")
    public Boolean logowanie(String login, String haslo) {

        Session session = null;

        try {
            session = HibernateUtil.getSessionFactory().openSession();

            Query q = null;
            try {
                q = session.createQuery("FROM Uzytkownik WHERE login = :login AND haslo = :password ");
                q.setParameter("login", login);
                q.setParameter("password", Security.sha1(haslo));

                List<Uzytkownik> resultList;
                resultList = q.list();
                for (Object o : resultList) {

                    Uzytkownik u = (Uzytkownik) o;
                    this.rola = u.getRola();
                    this.idUzytkownika= u.getUzytkownikId();
                    //this.setIdUzytkownika((int) u.getUzytkownikId());
                    //u.setSurname("test22");
                    System.out.println("Sprawdzam wyswietlenie roli:" + rola);
                }

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
                q3.setParameter("password", Security.sha1(haslo));
                if (q3.list().isEmpty()) {
                    FacesContext context = FacesContext.getCurrentInstance();
                    ResourceBundle bundle = context.getApplication().getResourceBundle(context, "msg");
                    message = bundle.getString("failed2");
                }
                return false;
            } else {
                message = "";
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

    public void dodajUzytkownika(Uzytkownik user) {

        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();

        Uzytkownik us = user;
        try {
            us.setHaslo(Security.sha1(us.getHaslo()));
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(UzytkownikDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        session.saveOrUpdate(us);

        session.getTransaction().commit();
        session.close();

    }

    
    public void zapiszUzytkownika(Uzytkownik user){
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        
        Uzytkownik us = user;
        try {
            us.setHaslo(Security.sha1(us.getHaslo()));
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(UzytkownikDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        session.saveOrUpdate(us);
        session.getTransaction().commit();
        session.close();
        
        
    }
    public void edytujUzytkownika(Uzytkownik user) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();

        session.saveOrUpdate(user);

        session.getTransaction().commit();
        session.close();
    }

    public Uzytkownik pobierzUzytkownika(Integer idUzytkownika) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Uzytkownik user = (Uzytkownik) session.load(Uzytkownik.class, idUzytkownika);

        System.out.println(user.getImie());
        session.close();

        return user;
    }

    public void usunUzytkownika(Uzytkownik user){
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        
        session.delete(user);
        
        session.getTransaction().commit();
        session.close();
        
    }
    
    
    @SuppressWarnings("unchecked")
    public List<Uzytkownik> pobierzListeUzytkownikow() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction().begin();

        Query q = session.createQuery("from Uzytkownik");
        List<Uzytkownik> list;
        list = (List<Uzytkownik>) q.list();

        session.getTransaction().commit();
        session.close();
        return list;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getRola() {
        return rola;
    }

    public void setRola(String rola) {
        this.rola = rola;
    }

    public int getIdUzytkownika() {
        return idUzytkownika;
    }
}
