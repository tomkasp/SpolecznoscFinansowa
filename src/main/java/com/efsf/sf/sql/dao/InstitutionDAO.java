package com.efsf.sf.sql.dao;

import com.efsf.sf.sql.entity.EmploymentType;
import com.efsf.sf.sql.entity.Institution;
import com.efsf.sf.sql.util.HibernateUtil;
import java.util.List;
import org.hibernate.Session;

public class InstitutionDAO {

    public List bankList() {
        List<EmploymentType> list;
        Session session = HibernateUtil.SESSION_FACTORY.openSession();
        try {
            session.beginTransaction();

            list = session.createQuery("from Institution where type = 0").list();

            session.getTransaction().commit();
        } finally {
            session.close();
        }
        return list;
    }

    public List institutionList() {
        List<EmploymentType> list;
        Session session = HibernateUtil.SESSION_FACTORY.openSession();
        try {
            session.beginTransaction();

            list = session.createQuery("from Institution where type = 1").list();

            session.getTransaction().commit();
        } finally {
            session.close();
        }
        return list;
    }

    public Institution getInstitution(int id) {
        Session session = HibernateUtil.SESSION_FACTORY.openSession();
        Institution ms;
        try {
            session.beginTransaction();
            ms = (Institution) session.get(Institution.class, id);

            session.getTransaction().commit();
        } finally {
            session.close();
        }

        return ms;
    }
}
