/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.efsf.sf.sql.dao;

import com.efsf.sf.sql.entity.User;
import com.efsf.sf.sql.util.HibernateUtil;
import com.efsf.sf.util.Security;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;

/**
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
    
    public void save(User user)
    {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction().begin();
       
        session.save(user);
        
        session.refresh(user);
        
        session.getTransaction().commit();
        
        session.close();
    }
    
    
    public User login(String email, String password) {
        
        Session session = null ;
        User user=null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            Query q = null;
            
                q = session.createQuery("FROM User WHERE email = :email AND password = :password ");
                q.setParameter("email", email);
                q.setParameter("password", Security.sha1(password));
                
                ArrayList<User> resultList = (ArrayList<User>) q.list();
                if(!resultList.isEmpty()){
                user=resultList.get(0);
                }
                   
        } catch (NoSuchAlgorithmException ex) {   
        }   finally {
            session.close();
        }
           
        return user;
    }
    
}
