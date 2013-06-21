package sql.dao;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;
import org.hibernate.Query;
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
            
            kredyt.setKlienci(klient);
            session.save(kredyt);

            session.getTransaction().commit();

            session.close();
    }
    
    
    public Kredyty readKredyty(Integer idKredyt) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        session.beginTransaction().begin();
       
        Kredyty kredyt=(Kredyty) session.load(Kredyty.class,idKredyt);
        
        session.getTransaction().commit();

        //session.close();
        
        return kredyt;
    }
    
        public void updateKredyty(Kredyty kredyt) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        session.beginTransaction().begin();
       
        session.saveOrUpdate(kredyt);
        
        session.getTransaction().commit();
        
        session.close();
    }
    
    public void deleteKredyty(Integer idKredyt) {
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
    public List<Kredyty> getAllKredyty() {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        session.beginTransaction().begin();
       
        Query q=(Query) session.createQuery("from Kredyty");
        List<Kredyty> list;
        list = (List<Kredyty>) q.list();
        
        session.getTransaction().commit();
        
        session.close();
        return list;
    }
    
    
    @SuppressWarnings("unchecked")
    public List<Kredyty> getKredytyOneKlient(int idKlienta) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        session.beginTransaction().begin();
       
        Query q=session.createQuery("from Kredyty where klienci_idKlienci="+idKlienta+" ");
        List<Kredyty> list;
        list = (List<Kredyty>) q.list();
        
        session.getTransaction().commit();
        
        session.close();
        return list;
    }
    
 
}
