package com.efsf.sf.sql.dao;

import com.efsf.sf.sql.entity.EmploymentType;
import com.efsf.sf.sql.util.HibernateUtil;
import java.util.List;
import org.hibernate.Session;

/**
 *
 * @author WR1EI1
 */
public class InstitutionDAO {

    public List bankList() {
        List<EmploymentType> list;
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();

        list = session.createQuery("from Institution where type = 0").list();

        session.getTransaction().commit();
        session.close();
        return list;
    }

    public List institutionList() {
        List<EmploymentType> list;
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();

        list = session.createQuery("from Institution where type = 1").list();

        session.getTransaction().commit();
        session.close();
        return list;
    }
}
