/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.efsf.sf.sql.dao;

import com.efsf.sf.sql.entity.Client;
import com.efsf.sf.sql.util.HibernateUtil;
import java.util.List;
import org.hibernate.Session;

/**
 *
 * @author WR1EI1
 */
public class ClientDAO {
    
    public Client read(int id){   
        
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction().begin();
     
        Client client = (Client) session.load(Client.class,id);
        
        session.getTransaction().commit();
        
        session.close();
        
        return client;
        
    }
    
     public void save(Client client){
         
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction().begin();
       
        session.save(client);
        
        session.getTransaction().commit();
        
        session.close();    
    }
    
   
    public void update(Client client){
        
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction().begin();
     
        session.update(client);
        
        session.getTransaction().commit();
        
        session.close();
    }
    
    public void delete(Client client){
        
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction().begin();
     
        session.delete(client);
        
        session.getTransaction().commit();
        
        session.close();
    }
    
    public int getLastClientID()
    {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction().begin();
        
        List result =  session.createQuery("select max(idClient) from Client").list();
        
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
