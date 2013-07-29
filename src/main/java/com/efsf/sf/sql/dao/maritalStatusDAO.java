/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.efsf.sf.sql.dao;

import com.efsf.sf.sql.entity.MaritalStatus;
import com.efsf.sf.sql.util.HibernateUtil;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.hibernate.Session;

/**
 *
 * @author admin
 */
public class maritalStatusDAO {

    public List maritalStatusList(){
        List lista = new ArrayList();
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        
        Iterator i = session.createQuery("From MaritalStatus").list().iterator();
        while(i.hasNext()){
            MaritalStatus ms = (MaritalStatus)i.next();
            lista.add(ms.getIdMaritalStatus(), ms.getName());
        }
        
        session.getTransaction().commit();
        session.close();   
        return lista;
    }

}
