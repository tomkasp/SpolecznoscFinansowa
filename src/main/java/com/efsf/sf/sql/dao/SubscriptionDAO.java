package com.efsf.sf.sql.dao;

import com.efsf.sf.sql.entity.Subscription;
import com.efsf.sf.sql.util.HibernateUtil;
import org.hibernate.Query;
import org.hibernate.Session;

public class SubscriptionDAO {

    public void save(Subscription subscription) {

        Session session = HibernateUtil.SESSION_FACTORY.openSession();
        
        try {
            session.beginTransaction().begin();
            session.save(subscription);
            session.getTransaction().commit();
        } finally {
            session.close();
        }

    }

    public void update(Subscription subscription) {

        Session session = HibernateUtil.SESSION_FACTORY.openSession();
        
        try {
            session.beginTransaction().begin();
            session.update(subscription);
            session.getTransaction().commit();
        } finally {
            session.close();
        }

    }

    public Subscription loadFkConsultant(Integer fkConsuntant) {

        Subscription subscription = null;
        Session session = HibernateUtil.SESSION_FACTORY.openSession();
        
        try {
            session.beginTransaction();

            Query q = null;
            q = session.createQuery("FROM Subscription WHERE fk_consultant = :id");
            q.setParameter("id", fkConsuntant);
            subscription = (Subscription) q.list().get(0);

            session.getTransaction().commit();
        } finally {
            session.close();
        }
        
        return subscription;
    }
    
    
    public Subscription getSubscriptionDetails(String idSession){
        Session session = HibernateUtil.SESSION_FACTORY.openSession();
        Subscription subscription = null;
        try{
            session.beginTransaction();
            
            Query q = session.createQuery("FROM Subscription sub left join fetch sub.consultant as con left join fetch sub.subscriptionType where sub.sessionId = :idSession");
            q.setParameter("idSession", idSession);
            
            subscription = (Subscription) q.list().get(0);
            session.getTransaction().commit();
        } finally{
            session.close();
        }
        
        return subscription;
    }
}
