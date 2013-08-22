package com.efsf.sf.sql.dao;

import com.efsf.sf.sql.entity.MaritalStatus;
import com.efsf.sf.sql.util.HibernateUtil;
import java.util.List;
import org.hibernate.classic.Session;

/**
 *
 * @author admin
 */
public class MaritalStatusDAO {
   
    public MaritalStatus getMaritalStatus(int id)
    {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        
        MaritalStatus ms = (MaritalStatus) session.get(MaritalStatus.class, id);
                
        session.getTransaction().commit();
        session.close();
        
        return ms;
        
    }
    
    public List maritalStatusList(){
        List<MaritalStatus> lista;
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        
        lista = session.createQuery("from MaritalStatus").list();
        
        session.getTransaction().commit();
        session.close();
        return lista;
    }
}
