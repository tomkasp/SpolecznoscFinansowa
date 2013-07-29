package com.efsf.sf.sql.dao;

import com.efsf.sf.sql.entity.WorkingPlace;
import com.efsf.sf.sql.util.HibernateUtil;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.faces.model.SelectItem;
import org.hibernate.Session;

public class WorkingPlaceDAO {
    
    public List regionList(){
        List<WorkingPlace> lista;
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        
        lista = session.createQuery("from WorkingPlace").list();
        
        session.getTransaction().commit();
        session.close();
        return lista;
    }
}
