/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.efsf.sf.sql.dao;

import com.efsf.sf.sql.entity.Region;
import com.efsf.sf.sql.util.HibernateUtil;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.hibernate.Session;

/**
 *
 * @author admin
 */
public class DAOregion {

    public List regionList(){
        List lista = new ArrayList();
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        
        Iterator i = session.createQuery("From Region").list().iterator();
        while(i.hasNext()){
            Region reg = (Region)i.next();
            lista.add(reg.getIdRegion(),reg.getRegion());
        }
        
        session.getTransaction().commit();
        session.close();   
        return lista;
    }
}
