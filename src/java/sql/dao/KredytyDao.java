package sql.dao;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import org.hibernate.Query;
import org.hibernate.QueryException;
import org.hibernate.Session;
import sql.entity.Klienci;
import sql.entity.KlienciKredyty;
import sql.entity.Kredyty;
import sql.util.HibernateUtil;
import sql.util.Security;

public class KredytyDao implements Serializable {

    private static final long serialVersionUID = 1L;

    public KredytyDao() {
    }

//    //  DOCELOWO DO USUNIECIA!
//    
//    public void createKredyt(Kredyty kredyt, Klienci klient) {
//        Session session = HibernateUtil.getSessionFactory().openSession();
//        session.beginTransaction().begin();
//
//        //POWIĄZANIE KLIENT - KREDYT
//        KlienciKredyty kkw = new KlienciKredyty();
//        kkw.setKlienci(klient);
//        kkw.setKredyty(kredyt);
//        kkw.setWspolkredytobiorca(false);
//
//        try {   
//            kredyt.getKlienciKredyties().add(kkw);
//        } 
//        catch( org.hibernate.LazyInitializationException e){
//        }
//        
//        session.saveOrUpdate(kredyt);
//        
//        session.getTransaction().commit();
//        session.close();
//    }
    
    public void createKredyt(Kredyty kredyt, Klienci klient, Klienci partner) {

        Session session = HibernateUtil.getSessionFactory().openSession();
        
        
        session.beginTransaction().begin();       
        
        String hql = "delete from KlienciKredyty where kredyty_id = :classId";
        session.createQuery(hql).setString("classId", kredyt.getKredytyId().toString() ).executeUpdate();
        
        session.getTransaction().commit();
        
        session.beginTransaction().begin();
        
        Set<KlienciKredyty> klienciKredyties = new HashSet<>(0);
        
        //POWIĄZANIE KLIENT - KREDYT
        KlienciKredyty kkw = new KlienciKredyty();
        kkw.setKlienci(klient);
        kkw.setKredyty(kredyt);
        kkw.setWspolkredytobiorca(false);
        
        klienciKredyties.add(kkw);
        
        //POWIĄZANIE PARTNER - KREDYT
        if (partner != null) {
            
            System.out.println("DODANO TEZ PARTNERA");

            KlienciKredyty kkw2 = new KlienciKredyty();
            kkw2.setKlienci(partner);
            kkw2.setKredyty(kredyt);
            kkw2.setWspolkredytobiorca(true);
            
            klienciKredyties.add(kkw2);
            
        }else{
            System.out.println("CZY NIE?");
        }
        
         try {
                kredyt.setKlienciKredyties(klienciKredyties);
            } catch (org.hibernate.LazyInitializationException e) {
            }

        session.saveOrUpdate(kredyt);

        session.getTransaction().commit();
        session.close();

    }

//     //DOCELOWO DO USUNIECIA!   
//    
//    public void updateKredyt(Kredyty kredyt, Klienci klient) {
//        
//        Session session = HibernateUtil.getSessionFactory().openSession();
//        session.beginTransaction().begin();
//
//        KlienciKredyty kkw = new KlienciKredyty();
//        kkw.setKlienci(klient);
//        kkw.setKredyty(kredyt);
//        kkw.setWspolkredytobiorca(false);
//
//        try {   
//            kredyt.getKlienciKredyties().add(kkw);
//        } 
//        catch( org.hibernate.LazyInitializationException e){
//        }
//        session.saveOrUpdate(kredyt);
//        
//        session.getTransaction().commit();
//        session.close();
//        
//    }
    public void updateKredyt(Kredyty kredyt, Klienci klient, Klienci partner) {

        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction().begin();

        //POWIĄZANIE KLIENT - KREDYT
        KlienciKredyty kkw = new KlienciKredyty();
        kkw.setKlienci(klient);
        kkw.setKredyty(kredyt);
        kkw.setWspolkredytobiorca(false);
        try {
            kredyt.getKlienciKredyties().add(kkw);
        } catch (org.hibernate.LazyInitializationException e) {
        }

        //POWIĄZANIE PARTNER - KREDYT
        if (partner != null) {
            KlienciKredyty kkw2 = new KlienciKredyty();
            kkw2.setKlienci(partner);
            kkw2.setKredyty(kredyt);
            kkw2.setWspolkredytobiorca(true);
            try {
                kredyt.getKlienciKredyties().add(kkw2);
            } catch (org.hibernate.LazyInitializationException e) {
            }
        }

        session.update(kredyt);

        session.getTransaction().commit();
        session.close();

    }
//
//        public Kredyty readKredyty(Integer idKredyt) {
//        Session session = HibernateUtil.getSessionFactory().openSession();
//        session.beginTransaction().begin();
//
//        Kredyty kredyt = (Kredyty) session.load(Kredyty.class, idKredyt);
//
//        session.getTransaction().commit();
//
//        //session.close();
//
//        return kredyt;
//    }
    
    public Kredyty readKredyty(Integer idKredyt) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        
        session.beginTransaction().begin();
        
        Query q = null;
            try {
                q = session.createQuery("FROM KlienciKredyty WHERE kredyty_id = :id ");
                q.setParameter("id", idKredyt);
            } catch (QueryException exp) {
            }
        
        KlienciKredyty kk=(KlienciKredyty) q.list().get(0);
          
        Kredyty kredyt = kk.getKredyty();
        
        kredyt.getKlienciKredyties().add(kk);
        
        session.getTransaction().commit();

        session.close();

        return kredyt;
    }
    
       public KlienciKredyty readKlienciKredyty(Integer idKredyt) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        
        session.beginTransaction().begin();
        
        Query q = null;
            try {
                q = session.createQuery("FROM KlienciKredyty WHERE kredyty_id = :id ");
                q.setParameter("id", idKredyt);
            } catch (QueryException exp) {
            }
        
        KlienciKredyty kk=(KlienciKredyty) q.list().get(0);
        
        kk.setKlienci(kk.getKlienci());
            
        Kredyty kredyt = kk.getKredyty();
        
        kredyt.getKlienciKredyties().add(kk);
        
        session.getTransaction().commit();

        session.close();

        return kk;
    }

    public void updateKredyty(Kredyty kredyt) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction().begin();

        session.saveOrUpdate(kredyt);

        session.getTransaction().commit();

        session.close();
    }

    public void deleteKredyty(Kredyty kredyt) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction().begin();

        session.delete(kredyt);

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
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction().begin();

        Query q = session.createQuery("from Kredyty");
        List<Kredyty> list;
        list = (List<Kredyty>) q.list();

        session.getTransaction().commit();

        session.close();
        return list;
    }

    @SuppressWarnings("unchecked")
    public List<Kredyty> getKredytyOneKlient(int idKlienta) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction().begin();

        Query q = session.createQuery("from KlienciKredyty where klienci_id=" + idKlienta + " ");

        List<Kredyty> kredyty = new LinkedList<>();

        List l = q.list();
        Iterator it = l.iterator();
        while (it.hasNext()) {

            KlienciKredyty kk = (KlienciKredyty) it.next();

            kredyty.add(kk.getKredyty());

        }

        session.getTransaction().commit();
        session.close();

        return kredyty;
    }
}
