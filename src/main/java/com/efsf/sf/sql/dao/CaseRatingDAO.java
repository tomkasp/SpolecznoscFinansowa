package com.efsf.sf.sql.dao;

import com.efsf.sf.sql.entity.CaseRating;
import com.efsf.sf.sql.entity.ClientCase;
import com.efsf.sf.sql.util.HibernateUtil;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;

public class CaseRatingDAO {

    public void save(CaseRating rating) {

        Session session = HibernateUtil.SESSION_FACTORY.openSession();
        try {
            session.beginTransaction();
            session.save(rating);
            session.getTransaction().commit();
        } finally {
            session.close();
        }
    }

    public boolean isNotRated(Integer idClientCase) {
        
        CaseRating rating = null;
        Session session = HibernateUtil.SESSION_FACTORY.openSession();
        try {
            rating = (CaseRating) session.get(CaseRating.class, idClientCase);
        } finally {
            session.close();
        }

        return rating == null ? true : false;
    }

    public List<ClientCase> getConsultantRatings(Integer consultantId) {
        List<ClientCase> list;
        Session session = HibernateUtil.SESSION_FACTORY.openSession();

        Query q = session.createQuery("FROM ClientCase as cs "
                + "join fetch cs.client as clt "
                + "join fetch cs.caseRating as rat "
                + "join fetch cs.consultant as con "
                + "where con.idConsultant = :consultantId ");

        q.setParameter("consultantId", consultantId);

        list = q.list();
        session.close();

        return list;
    }
}