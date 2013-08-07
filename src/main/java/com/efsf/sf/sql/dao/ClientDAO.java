/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.efsf.sf.sql.dao;

import com.efsf.sf.sql.entity.Client;
import com.efsf.sf.sql.util.HibernateUtil;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author WR1EI1
 */
public class ClientDAO {
    private int points;
    
    
    public Client read(int id){   
        
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction().begin();
     
        Query q = null;
        q = session.createQuery("FROM Client c left outer join fetch c.user as u WHERE id_client = :id");
        q.setParameter("id", id);
        
        Client client = (Client) q.list().get(0);
        
        session.getTransaction().commit();
        
        session.close();
        
        return client;
    }
    
     public void save(Client client){
         
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
       
        session.save(client);
        
        session.getTransaction().commit();
        
        session.close();    
    }
     
    public void decrementPoints(Client client,Integer p){
        //odejmowanie punktow po dodaniu wniosku
        
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        
        Client cli = (Client)session.load(Client.class, client.getIdClient());
        points = cli.getPoints();
        points = points-p;
        cli.setPoints(points);
        session.save(cli);
        
        
        session.getTransaction().commit();
        session.close();
        
    }
    
   
    public void update(Client client){
        
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
     
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
    
    public Client getClientWithIncomes(int idClient)
    {
                Session session = HibernateUtil.getSessionFactory().openSession();
                session.beginTransaction().begin();
                
                Query q = session.createQuery("FROM Client as clt "
                     + "left join fetch clt.incomes as inc "
                     + "left join fetch clt.incomeBusinessActivities as ba "
                     + "left join fetch inc.branch as br "
                     + "left join fetch inc.employmentType as empltype "
                     + "left join fetch ba.branch as br2 "
                     + "left join fetch ba.employmentType as empltype2 "
                     + "where clt.idClient = :id");
                
                q.setParameter("id", idClient);
                
                Client client = (Client) q.list().get(0);
        
                
                
                session.getTransaction().commit();

                session.close();

                return client;
    }
    
    
    

    
}
