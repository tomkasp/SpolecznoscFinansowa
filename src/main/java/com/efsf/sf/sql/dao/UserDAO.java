package com.efsf.sf.sql.dao;

import com.efsf.sf.sql.entity.Client;
import com.efsf.sf.sql.entity.User;
import com.efsf.sf.sql.util.HibernateUtil;
import com.efsf.sf.util.Security;
import java.util.ArrayList;
import org.hibernate.Query;
import org.hibernate.Session;

public class UserDAO {

    public User read(int id) {

        User client = null;
        Session session = HibernateUtil.SESSION_FACTORY.openSession();

        try {
            session.beginTransaction().begin();
            client = (User) session.load(User.class, id);
            session.getTransaction().commit();
        } finally {
            session.close();
        }

        return client;

    }

    public User login(String email, String password) {

        User user = null;
        Session session = HibernateUtil.SESSION_FACTORY.openSession();

        try {
            Query q = session.createQuery("FROM User WHERE email = :email AND password = :password ");
            q.setParameter("email", email);
            q.setParameter("password", Security.sha1(password));
            ArrayList<User> resultList = (ArrayList<User>) q.list();
            if (!resultList.isEmpty()) {
                user = resultList.get(0);
            }
        } finally {
            session.close();
        }

        return user;
    }

    public void save(User user) {

        Session session = HibernateUtil.SESSION_FACTORY.openSession();
        
        try {
            session.beginTransaction().begin();
            session.save(user);
            session.refresh(user);
            session.getTransaction().commit();
        } finally {
            session.close();
        }

    }

    public void update(User user) {

        Session session = HibernateUtil.SESSION_FACTORY.openSession();
        
        try {
            session.beginTransaction().begin();
            session.update(user);
            session.getTransaction().commit();
        } finally {
            session.close();
        }

    }

    public void delete(User user) {
        Session session = HibernateUtil.SESSION_FACTORY.openSession();

        try {
            session.beginTransaction().begin();
            session.delete(user);
            session.getTransaction().commit();
        } finally {
            session.close();
        }
        
    }

    public Boolean ifEmailExist(String email) {
        Session session = HibernateUtil.SESSION_FACTORY.openSession();

        try {
            Query q;
            q = session.createQuery("FROM User WHERE email = :email ");
            q.setParameter("email", email);
            ArrayList<User> resultList = (ArrayList<User>) q.list();
            if (!resultList.isEmpty()) {
                return true;
            }
        } finally {
            session.close();
        }

        return false;
    }

    public int checkLogin(String email, String password) {

        Session session = HibernateUtil.SESSION_FACTORY.openSession();

        try {
            Query q;
            q = session.createQuery("FROM User WHERE email = :email ");
            q.setParameter("email", email);

            @SuppressWarnings("unchecked")
            ArrayList<User> resultList = (ArrayList<User>) q.list();
            if (!resultList.isEmpty()) {
                User user = resultList.get(0);
                if (user.getPassword().equals(password)) {
                    return 1;
                } else {
                    return 0;
                }
            }

        } finally {
            session.close();
        }

        return -1;
    }

    public Client getClientConnectedToUser(int userId) {

        Session session = HibernateUtil.SESSION_FACTORY.openSession();
        Client result;

        try {
            session.beginTransaction().begin();
            Query q = session.createQuery("FROM Client c "
                    + "JOIN Fetch c.user as u "
                    + "LEFT JOIN fetch c.incomes as inc "
                    + "LEFT JOIN fetch c.incomeBusinessActivities as ba "
                    + "left join fetch inc.branch as br "
                    + "left join fetch inc.employmentType as empltype "
                    + "left join fetch ba.branch as br2 "
                    + "left join fetch ba.employmentType as empltype2 "
                    + "left join fetch c.requiredDocumentses as docs "
                    + "where u.idUser = :userId");
            q.setParameter("userId", userId);
            result = (Client) q.list().get(0);

            session.getTransaction().commit();
        } finally {
            session.close();
        }

        return result;
    }
    
    
     public User read(String email) {

        User user = null;
        Session session = HibernateUtil.SESSION_FACTORY.openSession();

        try {
            Query q;
            q = session.createQuery("FROM User WHERE email = :email ");
            q.setParameter("email", email);

            @SuppressWarnings("unchecked")
            ArrayList<User> resultList = (ArrayList<User>) q.list();
            if (!resultList.isEmpty()) {
                user = resultList.get(0);
            }

        } finally {
            session.close();
        }

        return user;
    }   
}
