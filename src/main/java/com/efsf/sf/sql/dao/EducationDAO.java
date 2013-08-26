package com.efsf.sf.sql.dao;

import com.efsf.sf.sql.entity.Education;
import com.efsf.sf.sql.util.HibernateUtil;
import java.util.List;
import org.hibernate.classic.Session;

public class EducationDAO {
    
    
    public Education getEducation(int id)
    {
                Session session = HibernateUtil.SESSION_FACTORY.openSession();
                session.beginTransaction();
                Education edu = (Education) session.get(Education.class, id);
                
                session.getTransaction().commit();
                session.close();
                
                return edu;
        
    }
    
    public List educationList(){
        List<Education> lista;
        Session session = HibernateUtil.SESSION_FACTORY.openSession();
        session.beginTransaction();
        
        lista = session.createQuery("from Education").list();
        
        session.getTransaction().commit();
        session.close();
        return lista;
    }
    
    
}
