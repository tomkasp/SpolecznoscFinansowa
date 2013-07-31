package com.efsf.sf.sql.dao;

import com.efsf.sf.sql.entity.SubscriptionType;
import com.efsf.sf.sql.util.HibernateUtil;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.classic.Session;

/**
 *
 * @author admin
 */
public class SubscriptionTypeDAO {
   
    public SubscriptionType getSubscriptionType(int id)
    {
        SubscriptionType st=null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        try{
        st = (SubscriptionType) session.get(SubscriptionType.class, id);
        }
        catch(HibernateException e)
        {}
        finally
        {
        session.close();
        }
        
        return st;   
    }
    
    public List subscriptionTypeList(){
        List<SubscriptionType> lista;
        Session session = HibernateUtil.getSessionFactory().openSession();
        
        lista = session.createQuery("from SubscriptionType").list();
        
       // session.close();
        return lista;
    }
    
    
    
    
}
