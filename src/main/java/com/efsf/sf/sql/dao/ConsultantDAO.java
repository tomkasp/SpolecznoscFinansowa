/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.efsf.sf.sql.dao;

import com.efsf.sf.sql.entity.Consultant;
import com.efsf.sf.sql.entity.Region;
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
        q = session.createQuery("FROM Consultant c LEFT JOIN FETCH c.user as u WHERE id_consultant = :id");
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
        {
            e.printStackTrace();
        }
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
    
    
    public Consultant getCounsultantConnectedToUser(int userId)
    {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction().begin();
        
        
        Query q  = session.createQuery("FROM Consultant c "
                + " JOIN Fetch c.user as u "
                + "LEFT JOIN FETCH c.clientCases_2 as cs2 " 
                + "LEFT JOIN FETCH c.clientCases as cs "
                
                + "left join fetch cs.client as clt "
                + "left join fetch cs.productType as pt "      
                + "left join fetch cs.consultants as consul "
                + "left join fetch clt.addresses as addr "
                + "left join fetch cs.caseStatus as cstats "
                + "left join fetch clt.incomes as inc "
                + "left join fetch clt.incomeBusinessActivities as ba "
                + "left join fetch inc.branch as br "
                + "left join fetch inc.employmentType as empltype "
                + "left join fetch ba.branch as br2 "
                + "left join fetch ba.employmentType as empltype2 "
                + "left join fetch clt.requiredDocumentses as rd "  
                
                + "left join fetch cs2.client as clt2 "
                + "left join fetch cs2.productType as pt2 "      
                + "left join fetch cs2.consultants as consul2 "
                + "left join fetch clt2.addresses as addr2 "
                + "left join fetch cs2.caseStatus as cstats2 "
                + "left join fetch clt2.incomes as inc2 "
                + "left join fetch clt2.incomeBusinessActivities as ba2 "
                + "left join fetch inc2.branch as br2 "
                + "left join fetch inc2.employmentType as empltype2 "
                + "left join fetch ba2.branch as br22 "
                + "left join fetch ba2.employmentType as empltype22 "
                + "left join fetch clt2.requiredDocumentses as rd2 "  
                
                + "where u.idUser = :userId");
        
        q.setParameter("userId", userId);
        Consultant result = (Consultant) q.list().get(0);
        
        session.getTransaction().commit();
        session.close();
        
        return result; 
    }
    
    public Consultant readConsultantForSettings(int id) {
       
        Session session = HibernateUtil.getSessionFactory().openSession();
        Consultant consultant=null;
        
        try
        {
        session.beginTransaction().begin();
        Query q = null;
        q = session.createQuery("FROM Consultant c "
                + " LEFT JOIN FETCH c.user as u "
                + " LEFT JOIN FETCH c.region as r "
                + " LEFT JOIN FETCH c.workingPlace as w "
                + " LEFT JOIN FETCH c.subscriptions as s "
                + " LEFT JOIN FETCH c.productTypes as p "
                + " LEFT JOIN FETCH c.institutions as i "
                + " LEFT JOIN FETCH c.addresses as a "
                + " WHERE id_consultant = :id ");
        
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
    
    
}
