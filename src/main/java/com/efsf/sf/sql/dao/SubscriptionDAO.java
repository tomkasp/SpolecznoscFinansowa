package com.efsf.sf.sql.dao;

import com.efsf.sf.sql.entity.Subscription;
import com.efsf.sf.sql.util.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;

/**
 * @author WR1EI1
 */

public class SubscriptionDAO {

    public void save(Subscription subscription) {

        Session session = HibernateUtil.getSessionFactory().openSession();
        try{
        session.beginTransaction().begin();
        session.save(subscription);
        session.getTransaction().commit();
        }catch(HibernateException e)
        {}
        finally{
        session.close();
        }
        
    }
    
    public void update(Subscription subscription) {

        Session session = HibernateUtil.getSessionFactory().openSession();
        try{
        session.beginTransaction().begin();
        session.update(subscription);
        session.getTransaction().commit();
        }catch(HibernateException e)
        {}
        finally{
        session.close();
        }
        
    }
   
}
