/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.efsf.sf.sql.dao;

import com.efsf.sf.sql.entity.WorkingPlace;
import com.efsf.sf.sql.util.HibernateUtil;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.hibernate.Session;

/**
 *
 * @author admin
 */
public class workingPlaceDAO {

    public List workingPlaceList(){
        List lista = new ArrayList();
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        
        Iterator i = session.createQuery("From WorkingPlace").list().iterator();
        while(i.hasNext()){
            WorkingPlace wp = (WorkingPlace)i.next();
            lista.add(wp.getIdWorkingPlace(),wp.getName());
        }
        
        session.getTransaction().commit();
        session.close();   
        return lista;
    }


}
