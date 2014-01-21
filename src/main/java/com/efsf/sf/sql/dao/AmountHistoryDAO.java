package com.efsf.sf.sql.dao;

import com.efsf.sf.sql.entity.AmountHistory;
import com.efsf.sf.sql.entity.Client;
import com.efsf.sf.sql.util.HibernateUtil;
import java.io.Serializable;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;

public class AmountHistoryDAO implements Serializable {

    public void save(AmountHistory amhist) {
        Session session = HibernateUtil.SESSION_FACTORY.openSession();
        try {
            session.beginTransaction();

            session.save(amhist);

            session.getTransaction().commit();
        } finally {
            session.close();
        }

    }

    public boolean checkMD5(String md5, int client) {
        Session session = HibernateUtil.SESSION_FACTORY.openSession();
        try {
            session.beginTransaction();
            Query query = session.createQuery("FROM AmountHistory as ah, Client as cl left join fetch ah.client where cl.idClient=:client AND ah.hashCode=:md5");
            query.setParameter("md5", md5);
            query.setParameter("client", client);

            List lista = query.list();
            if (lista.isEmpty()) {
                return false;
            }

            session.getTransaction().commit();
        } finally {
            session.close();
        }

        return true;
    }

}
