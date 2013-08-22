package com.efsf.sf.sql.dao;

import com.efsf.sf.sql.entity.Income;
import com.efsf.sf.sql.util.HibernateUtil;
import org.hibernate.Session;

public class IncomeDAO {

    public void update(Income income) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction().begin();
            session.update(income);
            session.getTransaction().commit();
        } finally {
            session.close();
        }
    }

    public void delete(Income income) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction().begin();
            session.delete(income);
            session.getTransaction().commit();
        } finally {
            session.close();
        }
    }
}
