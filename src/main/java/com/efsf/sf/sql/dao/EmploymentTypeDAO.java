/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.efsf.sf.sql.dao;

import com.efsf.sf.sql.entity.EmploymentType;
import com.efsf.sf.sql.util.HibernateUtil;
import java.util.List;
import org.hibernate.Session;

/**
 *
 * @author XaI
 */
public class EmploymentTypeDAO 
{
    public List incomeList()
    {
        List<EmploymentType> list;
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        
        list= session.createQuery("from EmploymentType where companyFlag = 0").list();
        
        session.getTransaction().commit();
        session.close();
        return list;
    }
    
    public List businessActivityList()
    {
        List<EmploymentType> list;
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        
        list= session.createQuery("from EmploymentType where companyFlag = 1").list();
        
        session.getTransaction().commit();
        session.close();
        return list;
    }
    
        
}
