/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.efsf.sf.sql.dao;

import com.efsf.sf.sql.entity.ClientCase;
import com.efsf.sf.sql.util.HibernateUtil;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author XaI
 */
public class ClientCaseDAO 
{
    public List last5Cases()
    {
         List<ClientCase> list;
         Session session = HibernateUtil.getSessionFactory().openSession();
         session.beginTransaction();
         
         Query q = session.createQuery("from ClientCase order by BeginDate");
         
         q.setMaxResults(5);
         
         list = q.list();
         
         
         session.getTransaction().commit();
         session.close();
         

         return list;
    }
    
    
}
