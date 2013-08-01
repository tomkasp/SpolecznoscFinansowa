/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.efsf.sf.sql.dao;

import com.efsf.sf.sql.entity.Consultant;
import com.efsf.sf.sql.util.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author WR1EI1
 */
public class ConsultantDAO {

    public Consultant read(int id) {
       
        Session session = HibernateUtil.getSessionFactory().openSession();
        Consultant consultant=null;
        
        try
        {
        session.beginTransaction().begin();
        Query q = null;
        q = session.createQuery("FROM Consultant c left outer join fetch c.user as u WHERE id_consultant = :id");
        q.setParameter("id", id);
        consultant=(Consultant) q.list().get(0);
        session.getTransaction().commit();
        
        }
        catch(HibernateException exp)
        {}
        finally{   
        session.close();
        }

        return consultant;
    }

    public void save(Consultant consultant) {

        Session session = HibernateUtil.getSessionFactory().openSession();
        try{
        session.beginTransaction();
        session.save(consultant);
        session.getTransaction().commit();
        }
        catch(HibernateException e)
        {}
        finally{
        session.close();
        }
    }

    public void update(Consultant consultant) {

        Session session = HibernateUtil.getSessionFactory().openSession();
        try{
        session.beginTransaction();
        session.update(consultant);
        session.getTransaction().commit();
        }catch(HibernateException e)
        {}
        finally{
        session.close();
        }
    }

    public void delete(Consultant consultant) {

        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction().begin();

        session.delete(consultant);

        session.getTransaction().commit();

        session.close();
    }
}
