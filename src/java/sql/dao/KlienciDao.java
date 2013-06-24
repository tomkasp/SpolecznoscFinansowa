package sql.dao;
import java.io.Serializable;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import sql.entity.Klienci;
import sql.util.NewHibernateUtil;

public class KlienciDao implements Serializable {
    private static final long serialVersionUID = 1L;

    public KlienciDao() {
    }
    
    public void createOrUpdateKlient(Klienci klient) 
    {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        session.beginTransaction().begin();
       
        session.saveOrUpdate(klient);
        
        session.getTransaction().commit();
        
        session.close();
    }
    
    public Klienci readKlient(Integer idKlient) 
    {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        session.beginTransaction().begin();
     
        Klienci klient = (Klienci) session.load(Klienci.class,idKlient);
        
        session.getTransaction().commit();
        
        //session.close();
        return klient;
    }
    
    public void updateKlient(Klienci klient) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        session.beginTransaction().begin();
       
        session.saveOrUpdate(klient);
        
        session.getTransaction().commit();
        
        session.close();
    }
    
    public void deleteKlient(Klienci klient) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        session.beginTransaction().begin();
       
        session.delete(klient);
        
        session.getTransaction().commit();
//        Query query = session.createQuery("delete from Klienci where idKlientci=" + idKlient);
//        int row = query.executeUpdate();
//        if (row == 0) {
//            System.out.println("nie usunieto nikogo!");
//        }
//        
        session.close();
    }
    
    @SuppressWarnings("unchecked")
    public List<Klienci> getKlientList() {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        session.beginTransaction().begin();
       
        Query q=(Query) session.createQuery("from Klienci");
        List<Klienci> list;
        list = (List<Klienci>) q.list();
        
        session.getTransaction().commit();
        
        session.close();
        return list;
    }
    
}
