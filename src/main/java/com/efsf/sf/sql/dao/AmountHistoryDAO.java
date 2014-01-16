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
            Client cl = (Client)session.get(Client.class, 203);
            
            amhist.setClient(cl);
            session.save(amhist);
            
            session.getTransaction().commit();
        }
        finally{
            session.close();
        }
        
    }
}
