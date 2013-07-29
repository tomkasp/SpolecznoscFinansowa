package com.efsf.sf.sql.dao;

import com.efsf.sf.sql.entity.MaritalStatus;
import com.efsf.sf.sql.util.HibernateUtil;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.faces.model.SelectItem;
import org.hibernate.classic.Session;

/**
 *
 * @author admin
 */
public class MaritalStatusDAO {
   
        
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