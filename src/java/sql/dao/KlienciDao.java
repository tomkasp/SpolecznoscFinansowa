package sql.dao;
import java.io.Serializable;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import sql.entity.Klienci;
import sql.entity.Uzytkownik;
import sql.util.HibernateUtil;
import sql.util.Security;

public class KlienciDao implements Serializable {
    private static final long serialVersionUID = 1L;

    public KlienciDao() {
    }
    
    public void createOrUpdateKlient(Klienci klient,int idUzytkownika) 
    {
        
        
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction().begin();
        
        Uzytkownik u = (Uzytkownik) session.get(Uzytkownik.class, idUzytkownika);
        klient.setUzytkownik(u);

        session.saveOrUpdate(klient);
        
        session.getTransaction().commit();
        
        session.close();
    }
    
    
    public Klienci readKlient(Integer idKlient) 
    {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction().begin();
     
        Klienci klient = (Klienci) session.load(Klienci.class,idKlient);
        
        session.getTransaction().commit();
        
        session.close();
        return klient;
    }
    
    public void updateKlient(Klienci klient) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction().begin();
       
        session.saveOrUpdate(klient);
        
        session.getTransaction().commit();
        
        session.close();
    }
    
    public void deleteKlient(Klienci klient) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction().begin();
       
        session.delete(klient);
        
        session.getTransaction().commit();

        session.close();
    }
    
    @SuppressWarnings("unchecked")
    public List<Klienci> getKlientList(int idUzytkownika) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction().begin();
       
        Query q=(Query) session.createQuery("from Klienci where fk_uzytkownik = :idUzytkownika ");
        q.setParameter("idUzytkownika", idUzytkownika);
        List<Klienci> list;
        list = (List<Klienci>) q.list();
        
        session.getTransaction().commit();
        
        session.close();
        return list;
    }
    
    @SuppressWarnings("unchecked")
    public List<Klienci> getKlientList() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction().begin();
       
        Query q=(Query) session.createQuery("from Klienci");
        
        List<Klienci> list;
        list = (List<Klienci>) q.list();
        
        session.getTransaction().commit();
        
        session.close();
        return list;
    }
    
}
