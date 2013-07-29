/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.efsf.sf.sql.dao;

import com.efsf.sf.sql.entity.Region;
import com.efsf.sf.sql.util.HibernateUtil;
import java.util.List;
import org.hibernate.Session;

/**
 * @author admin
 */

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
}
