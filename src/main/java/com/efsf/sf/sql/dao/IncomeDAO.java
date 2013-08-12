/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.efsf.sf.sql.dao;

import com.efsf.sf.sql.entity.Income;
import com.efsf.sf.sql.entity.User;
import com.efsf.sf.sql.util.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;

/**
 * @author WR1EI1
 */
public class IncomeDAO {

    
    
    public void update(Income income)
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
    
    public void delete(Income income)
    {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction().begin();
        session.delete(income);
        session.getTransaction().commit();
        session.close();
    }
    
  

    
}
