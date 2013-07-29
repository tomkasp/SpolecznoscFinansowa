package com.efsf.sf.sql.dao;

import com.efsf.sf.sql.entity.Education;
import com.efsf.sf.sql.util.HibernateUtil;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.hibernate.Session;

public class educationDAO {

    public List educationList(){
        List lista = new ArrayList();
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        
        Iterator i = session.createQuery("From Education").list().iterator();
        while(i.hasNext()){
            Education edu = (Education)i.next();
            lista.add(edu.getIdEducation(),edu.getName());
        }
        
        session.getTransaction().commit();
        session.close();   
        return lista;
    }
}
