/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.efsf.sf.sql.dao;

import com.efsf.sf.sql.entity.Client;
import com.efsf.sf.sql.entity.Consultant;
import com.efsf.sf.sql.entity.User;
import com.efsf.sf.sql.util.HibernateUtil;
import com.efsf.sf.util.Security;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javax.faces.context.FacesContext;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.QueryException;
import org.hibernate.Session;
import org.hibernate.exception.JDBCConnectionException;

/**
 * @author WR1EI1
 */
public class UserDAO {

    public User read(int id) {

        User client=null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        
        try
        {
        session.beginTransaction().begin();
        client = (User) session.load(User.class, id);
        session.getTransaction().commit();
        }
        catch(HibernateException exp)
        {
        }
        finally{   
        session.close();
        }
      
        return client;

    }

    public User login(String email, String password) {

        Session session = null;
        User user = null;
        try {
            
            session = HibernateUtil.getSessionFactory().openSession();
            Query q = null;
            q = session.createQuery("FROM User WHERE email = :email AND password = :password ");
            q.setParameter("email", email);
            q.setParameter("password", Security.sha1(password));
            ArrayList<User> resultList = (ArrayList<User>) q.list();
            if (!resultList.isEmpty()) {
                user = resultList.get(0);
            }

        } catch (JDBCConnectionException e) {
            FacesContext context = FacesContext.getCurrentInstance();
            ResourceBundle bundle = context.getApplication().getResourceBundle(context, "msg");
            System.out.println(bundle.getString("failed3"));
        } finally {
            session.close();
        }

        return user;
    }
    
    public void save(User user)
    {
        
        Session session = HibernateUtil.getSessionFactory().openSession();
        try{
        session.beginTransaction().begin();
        session.save(user);        
        session.refresh(user);      
        session.getTransaction().commit();
        }catch(HibernateException e)
        {}
        finally{
        session.close();
        }
        
    }
    
    public void update(User user)
    {
        
        Session session = HibernateUtil.getSessionFactory().openSession();
        try{
        session.beginTransaction().begin();
        session.update(user);
        session.getTransaction().commit();
        }catch(HibernateException e)
        {}
        finally{
        session.close();
        }
        
    }
    
    public void delete(User user)
    {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction().begin();
        session.delete(user);
        session.getTransaction().commit();
        session.close();
    }
    
    public Boolean ifEmailExist(String email) {

        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            Query q = null;
            q = session.createQuery("FROM User WHERE email = :email ");
            q.setParameter("email", email);
            ArrayList<User> resultList = (ArrayList<User>) q.list();
            if ( !resultList.isEmpty() ) {
                return true;
            }
        } catch (JDBCConnectionException e) {}
        finally {
            session.close();
        }
        return false;
    }
    
    public int checkLogin(String email,String password) {

        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            Query q = null;
            q = session.createQuery("FROM User WHERE email = :email ");
            q.setParameter("email", email);
            
            @SuppressWarnings("unchecked")
            ArrayList<User> resultList = (ArrayList<User>) q.list();
            if ( !resultList.isEmpty() ) {
                User user=resultList.get(0);
                if( user.getPassword().equals(password) )
                {
                    return 1;
                }
                else{
                    return 0;//wrong password
                }
            }
            
            
        } catch (JDBCConnectionException e) {}
        finally {
            session.close();
        }
        return -1;//wrong email
    }
    
    public Client getClientConnectedToUser(int userId)
    {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction().begin();
        
        Query q  = session.createQuery("FROM Client c "
                + "JOIN Fetch c.user as u "
                + "LEFT JOIN fetch c.incomes as inc "
                + "LEFT JOIN fetch c.incomeBusinessActivities as ba "
                + "left join fetch inc.branch as br "
                + "left join fetch inc.employmentType as empltype "
                + "left join fetch ba.branch as br2 "
                + "left join fetch ba.employmentType as empltype2 "
                + "where u.idUser = :userId");
        q.setParameter("userId", userId);
        
        Client result = (Client) q.list().get(0);
        
        session.getTransaction().commit();
        session.close();
        
        return result; 
    }
    

    
}
