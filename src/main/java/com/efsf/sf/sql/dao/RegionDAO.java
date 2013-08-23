package com.efsf.sf.sql.dao;

import com.efsf.sf.sql.entity.Region;
import com.efsf.sf.sql.util.HibernateUtil;
import java.util.List;
import org.hibernate.Session;


public class RegionDAO {
   
    
    public List regionList(){
        List<Region> lista;
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        
        lista = session.createQuery("from Region").list();
        
        session.getTransaction().commit();
        session.close();
        return lista;
    }
    
    
    public Region getRegion(int id)
    {
        org.hibernate.classic.Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        
        Region ms = (Region) session.get(Region.class, id);
                
        session.getTransaction().commit();
        session.close();
        
        return ms;
        
    }
    
}
