package com.efsf.sf.sql.dao;

import com.efsf.sf.sql.entity.Education;
import com.efsf.sf.sql.util.HibernateUtil;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.faces.model.SelectItem;
import org.hibernate.classic.Session;

public class EducationDAO {
    
    
    public List educationList(){
        List<Education> lista;
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        
        lista = session.createQuery("from Education").list();
        
        session.getTransaction().commit();
        session.close();
        return lista;
    }
    
    
}
