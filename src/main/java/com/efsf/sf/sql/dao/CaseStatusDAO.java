package com.efsf.sf.sql.dao;

import com.efsf.sf.sql.entity.CaseStatus;
import com.efsf.sf.sql.util.HibernateUtil;
import java.io.Serializable;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;

public class CaseStatusDAO implements Serializable{

    public List caseStatusList() {
        List<CaseStatus> lista;
        Session session = HibernateUtil.SESSION_FACTORY.openSession();
        try {
            session.beginTransaction();
            lista = session.createQuery("from CaseStatus").list();
            session.getTransaction().commit();
        } finally {
            session.close();
        }
        return lista;
    }

    public CaseStatus read(int id) {

        Session session = HibernateUtil.SESSION_FACTORY.openSession();
        CaseStatus caseStatus = null;
        try {
            session.beginTransaction().begin();
            Query q;
            q = session.createQuery("FROM CaseStatus WHERE id_caseStatus = :id");
            q.setParameter("id", id);
            caseStatus = (CaseStatus) q.list().get(0);
            session.getTransaction().commit();

        } finally {
            session.close();
        }

        return caseStatus;
    }
}
