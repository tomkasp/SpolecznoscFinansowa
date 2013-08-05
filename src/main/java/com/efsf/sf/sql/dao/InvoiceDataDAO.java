/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.efsf.sf.sql.dao;

import com.efsf.sf.sql.entity.Address;
import com.efsf.sf.sql.entity.InvoiceData;
import com.efsf.sf.sql.util.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author WR1EI1
 */
public class InvoiceDataDAO {

    public void save(InvoiceData invoiceData) {

        Session session = HibernateUtil.getSessionFactory().openSession();
        try{
        session.beginTransaction();
        session.save(invoiceData);
        session.getTransaction().commit();
        }
        catch(HibernateException e)
        {}
        finally{
        session.close();
        }
        
    }
    
    
    public void update(InvoiceData invoiceData) {

        Session session = HibernateUtil.getSessionFactory().openSession();
        try{
        session.beginTransaction();
        session.update(invoiceData);
        session.getTransaction().commit();
        }
        catch(HibernateException e)
        {}
        finally{
        session.close();
        }
        
    }
    
     public InvoiceData loadFromFkAddress(Integer fkAddress) {
         
        InvoiceData invoiceData = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        try{
        session.beginTransaction();
        
        Query q = null;
        q = session.createQuery("FROM InvoiceData WHERE fk_address = :id ");
        q.setParameter("id", fkAddress);
        invoiceData=(InvoiceData) q.list().get(0);
                
        session.getTransaction().commit();
        }
        catch(HibernateException e)
        {}
        finally{
        session.close();
        }
        return invoiceData;
    }
     
    
}
