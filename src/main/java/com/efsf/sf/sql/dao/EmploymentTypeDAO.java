package com.efsf.sf.sql.dao;

import com.efsf.sf.sql.entity.EmploymentType;
import com.efsf.sf.sql.util.HibernateUtil;
import java.io.Serializable;
import java.util.List;
import org.hibernate.Session;

public class EmploymentTypeDAO implements Serializable{

    public List incomeList() {
        List<EmploymentType> list;
        Session session = HibernateUtil.SESSION_FACTORY.openSession();
        try {
            session.beginTransaction();

            list = session.createQuery("from EmploymentType where companyFlag = 0").list();

            session.getTransaction().commit();
        } finally {
            session.close();
        }
        return list;
    }

    public List businessActivityList() {
        List<EmploymentType> list;
        Session session = HibernateUtil.SESSION_FACTORY.openSession();
        try {
            session.beginTransaction();

            list = session.createQuery("from EmploymentType where companyFlag = 1").list();

            session.getTransaction().commit();
        } finally {
            session.close();
        }
        return list;
    }
}