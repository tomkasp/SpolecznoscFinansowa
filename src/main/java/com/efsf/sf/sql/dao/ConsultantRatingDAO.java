package com.efsf.sf.sql.dao;

import com.efsf.sf.sql.entity.ConsultantRating;
import com.efsf.sf.sql.util.HibernateUtil;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;


public class ConsultantRatingDAO {

    public ConsultantRating getConsultantRatings(Integer consultantId) {
        ConsultantRating cr = null;
        Session session = HibernateUtil.SESSION_FACTORY.openSession();

        try {
            Query q = session.createQuery("FROM ConsultantRating as cr left join fetch cr.consultant as cons "
                    + "where cons.idConsultant = :consultantId ");

            q.setParameter("consultantId", consultantId);

            List l = q.list();
            if (!l.isEmpty()) {
                cr = (ConsultantRating) q.list().get(0);
            }

        }finally{
            session.close();
        }
        return cr;
    }
}
