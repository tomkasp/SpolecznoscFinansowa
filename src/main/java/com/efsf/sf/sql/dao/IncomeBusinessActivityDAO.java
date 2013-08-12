/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.efsf.sf.sql.dao;

import com.efsf.sf.sql.entity.IncomeBusinessActivity;
import com.efsf.sf.sql.util.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;

/**
 * @author WR1EI1
 */
public class IncomeBusinessActivityDAO {

    
    
    public void update(IncomeBusinessActivity income)
    {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try{
        session.beginTransaction().begin();
        session.update(income);
        session.getTransaction().commit();
        }catch(HibernateException e)
        {}
        finally{
        session.close();
        }
    }
    
    public void delete(IncomeBusinessActivity income)
    {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction().begin();
        session.delete(income);
        session.getTransaction().commit();
        session.close();
    }
    
  

    
}
