package com.efsf.sf.sql.dao;

import com.efsf.sf.sql.entity.MaritalStatus;
import com.efsf.sf.sql.util.HibernateUtil;
import java.util.List;
import org.hibernate.classic.Session;

public class MaritalStatusDAO {

    public MaritalStatus getMaritalStatus(int id) {
        Session session = HibernateUtil.SESSION_FACTORY.openSession();
        MaritalStatus ms;

        try {
            session.beginTransaction();
            ms = (MaritalStatus) session.get(MaritalStatus.class, id);

            session.getTransaction().commit();
        } finally {
            session.close();
        }

        return ms;
    }

    public List maritalStatusList() {
        List<MaritalStatus> lista;
        Session session = HibernateUtil.SESSION_FACTORY.openSession();

        try {
            session.beginTransaction();
            lista = session.createQuery("from MaritalStatus").list();
            session.getTransaction().commit();
        } finally {
            session.close();
        }

        return lista;
    }
}
