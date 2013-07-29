/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.efsf.sf.sql.dao;

import com.efsf.sf.sql.entity.Newsletter;
import com.efsf.sf.sql.util.HibernateUtil;
import org.hibernate.Session;

/**
 *
 * @author XaI
 */
public class newsletterDao 
{
    public void saveNewsletter(String email)
    {
        Session session;
        session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction().begin();
        
        Newsletter news = new Newsletter(email);

        session.saveOrUpdate(news);
        session.getTransaction().commit();
        session.close();
    }
            
}
