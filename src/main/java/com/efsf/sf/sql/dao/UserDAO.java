/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.efsf.sf.sql.dao;

import com.efsf.sf.sql.entity.User;
import com.efsf.sf.sql.util.HibernateUtil;
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
    
}
