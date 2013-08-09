/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.efsf.sf.sql.dao;

import com.efsf.sf.sql.entity.CaseRating;
import com.efsf.sf.sql.entity.ClientCase;
import com.efsf.sf.sql.util.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;

/**
 * @author WR1EI1
 */

public class CaseRatingDAO {

    public void save(CaseRating rating) {

        Session session = HibernateUtil.getSessionFactory().openSession();
        try{
        session.beginTransaction();
        session.save(rating);
        session.getTransaction().commit();
        }
        catch(HibernateException e)
        {}
        finally{
        session.close();
        }
    }
    
    public ClientCase getCase(Integer caseId){
        Session session = HibernateUtil.getSessionFactory().openSession();
        ClientCase clientCase = null;
        
        try{
           clientCase =  (ClientCase) session.get(ClientCase.class, caseId);
        }
        catch(HibernateException e)
        {}
        finally{
        session.close();
        }
        return clientCase;
    }
    
}   