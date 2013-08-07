/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.efsf.sf.sql.dao;

import com.efsf.sf.sql.entity.CaseStatus;
import com.efsf.sf.sql.entity.Consultant;
import com.efsf.sf.sql.util.HibernateUtil;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.faces.model.SelectItem;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author admin
 */
public class CaseStatusDAO {
    
    public List caseStatusList(){
        List<CaseStatus> lista;
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        lista = session.createQuery("from CaseStatus").list();
        session.getTransaction().commit();
        session.close();
        return lista;
    }
    
     public CaseStatus read(int id) {
       
        Session session = HibernateUtil.getSessionFactory().openSession();
        CaseStatus caseStatus=null;
        try
        {
        session.beginTransaction().begin();
        Query q = null;
        q = session.createQuery("FROM CaseStatus WHERE id_caseStatus = :id");
        q.setParameter("id", id);
        caseStatus=(CaseStatus) q.list().get(0);
        session.getTransaction().commit();
        
        }
        catch(HibernateException exp)
        {}
        finally{   
        session.close();
        }

        return caseStatus;
    }

}
