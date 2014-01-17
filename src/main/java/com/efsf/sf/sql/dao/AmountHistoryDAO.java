package com.efsf.sf.sql.dao;

import com.efsf.sf.sql.entity.AmountHistory;
import com.efsf.sf.sql.entity.Client;
import com.efsf.sf.sql.util.HibernateUtil;
import java.io.Serializable;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;

public class AmountHistoryDAO implements Serializable{
    
    
    public void save(AmountHistory amhist){
        Session session = HibernateUtil.SESSION_FACTORY.openSession();
        try{
            session.beginTransaction();
            
            session.save(amhist);
            
            session.getTransaction().commit();
        }
        finally{
            session.close();
        }
        
    }
    
    public boolean checkMD5(String md5, Client client){
        Session session = HibernateUtil.SESSION_FACTORY.openSession();
        try{
            session.beginTransaction();
            Query query = session.createQuery("FROM amountHistory where HashCode= :md5 and client= :client");
            query.setParameter("md5", md5);
            query.setParameter("client", client.getIdClient());
            
            List lista = query.list();
            if(lista.isEmpty()){
                System.out.println("lista jest pusta!");
}
            
            
            
            session.getTransaction().commit();
        }
        finally{
            session.close();
        }
        
        return true;
    }
    
    
}
