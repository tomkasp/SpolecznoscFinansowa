/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.efsf.sf.sql.dao;

import com.efsf.sf.sql.entity.Consultant;
import com.efsf.sf.sql.util.HibernateUtil;
import org.hibernate.Session;

/**
 *
 * @author WR1EI1
 */
public class ConsultantDAO {

    public Consultant read(int id) {

        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction().begin();

        Consultant consultant = (Consultant) session.load(Consultant.class, id);

        session.getTransaction().commit();

        session.close();

        return consultant;
    }

    public void save(Consultant consultant) {
        
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction().begin();
       
        session.save(consultant);
        
        session.getTransaction().commit();
        
        session.close();    
    }

    public void update(Consultant consultant) {
        
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction().begin();
       
        session.update(consultant);
        
        session.getTransaction().commit();
        
        session.close();
    }

    public void delete(Consultant consultant) {
        
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction().begin();

        session.delete(consultant);

        session.getTransaction().commit();

        session.close();
    }
}
