package sql.dao;

import java.util.List;
import javax.persistence.Query;
import org.hibernate.Session;
import sql.entity.Klienci;
import sql.entity.Kredyty;
import sql.util.NewHibernateUtil;

public class KredytyDao {

    public KredytyDao() {
    }

    public void createKredyt(Kredyty kredyt,Klienci klient) {
            Session session = NewHibernateUtil.getSessionFactory().openSession();
            session.beginTransaction().begin();
            
            session.save(klient);

            session.getTransaction().commit();

            session.close();
    }
    
    
    public void readClient(Integer idKredyt) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        session.beginTransaction().begin();
       
        session.load(idKredyt.toString(),Kredyty.class);
        
        session.getTransaction().commit();

        session.close();
    }
    
        public void updateClient(Kredyty kredyt) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        session.beginTransaction().begin();
       
        session.saveOrUpdate(kredyt);
        
        session.getTransaction().commit();
        
        session.close();
    }
    
    public void deleteClient(Integer idKredyt) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        session.beginTransaction().begin();
       
        session.delete(idKredyt.toString(),Kredyty.class);
        
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
    public List<Kredyty> getAllClients() {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        session.beginTransaction().begin();
       
        Query q=(Query) session.createQuery("from Kredyty");
        List<Kredyty> list;
        list = (List<Kredyty>) q.getResultList();
        
        session.getTransaction().commit();
        
        session.close();
        return list;
    }
    
}