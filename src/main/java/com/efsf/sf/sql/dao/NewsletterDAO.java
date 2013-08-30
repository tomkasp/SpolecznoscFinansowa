package com.efsf.sf.sql.dao;

import com.efsf.sf.sql.entity.Newsletter;
import com.efsf.sf.sql.util.HibernateUtil;
import org.hibernate.Session;


public class NewsletterDAO 
{
    public void saveNewsletter(String email)
    {
        Session session;
        session = HibernateUtil.SESSION_FACTORY.openSession();
        try{
        session.beginTransaction().begin();
        
        Newsletter news = new Newsletter(email);

        session.saveOrUpdate(news);
        session.getTransaction().commit();
        }finally{
            session.close();
        }
    }
            
}
