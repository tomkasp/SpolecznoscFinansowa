package com.efsf.sf.sql.dao;

import com.efsf.sf.sql.entity.WorkingPlace;
import com.efsf.sf.sql.util.HibernateUtil;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.hibernate.Session;

public class WorkingPlaceDAO {
    private List lista = new ArrayList();
    
    public List regionList(){
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        
        Iterator i = session.createQuery("from WorkingPlace").list().iterator();
        while(i.hasNext()){
            WorkingPlace wp = (WorkingPlace)i.next();
            lista.add(wp.getIdWorkingPlace(),wp.getName());
        }
        session.getTransaction().commit();
        session.close();
        return lista;
    }
}
