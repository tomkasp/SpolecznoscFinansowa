/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.efsf.sf.sql.dao;

import com.efsf.sf.sql.entity.User;
import com.efsf.sf.sql.util.HibernateUtil;
import java.util.List;
import org.hibernate.Session;

/**
 *
 * @author WR1EI1
 */
public class UserDAO {
    
    public User read(int id){   
        
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction().begin();
     
        User client = (User) session.load(User.class,id);
        
        session.getTransaction().commit();
        
        session.close();
        
        return client;
        
    }
    
    public int getLastUserID()
    {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction().begin();
        
        List result =  session.createQuery("select max(idUser) from User").list();
        
        session.getTransaction().commit();
        session.close();
        
        if (result.isEmpty())
        {
            return 0;
        }
        else
        {
          
           return (Integer) result.get(0); 
        }
        

    }
    
}
