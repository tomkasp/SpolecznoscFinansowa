package com.efsf.sf.sql.dao;

import com.efsf.sf.sql.entity.WorkingPlace;
import com.efsf.sf.sql.util.HibernateUtil;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Session;

public class WorkingPlaceDAO {
    
    public List workingPlaceList(){
        List<WorkingPlace> lista=null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        try{
        lista = session.createQuery("from WorkingPlace").list();
        }catch(HibernateException e)
        {}
        finally{
        }
        return lista;
    }
    
    
}
