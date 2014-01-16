package com.efsf.sf.sql.dao;

import com.efsf.sf.sql.entity.AmountHistory;
import com.efsf.sf.sql.entity.Client;
import com.efsf.sf.sql.util.HibernateUtil;
import java.io.Serializable;
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
}
