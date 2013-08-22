package com.efsf.sf.sql.dao;

import com.efsf.sf.sql.entity.IncomeBusinessActivity;
import com.efsf.sf.sql.util.HibernateUtil;
import org.hibernate.Session;


public class IncomeBusinessActivityDAO {

    
    
    public void update(IncomeBusinessActivity income)
    {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try{
        session.beginTransaction().begin();
        session.update(income);
        session.getTransaction().commit();
        } finally {
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
