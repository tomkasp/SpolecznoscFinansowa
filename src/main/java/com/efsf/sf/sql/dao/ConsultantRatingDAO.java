/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.efsf.sf.sql.dao;

import com.efsf.sf.sql.entity.ClientCase;
import com.efsf.sf.sql.entity.Consultant;
import com.efsf.sf.sql.entity.ConsultantRating;
import com.efsf.sf.sql.util.HibernateUtil;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author XaI
 */
public class ConsultantRatingDAO {

    public ConsultantRating getConsultantRatings(Integer consultantId) {
        ConsultantRating cr = null;
        Session session = HibernateUtil.getSessionFactory().openSession();

        try {
            Query q = session.createQuery("FROM ConsultantRating as cr left join fetch cr.consultant as cons "
                    + "where cons.idConsultant = :consultantId ");

            q.setParameter("consultantId", consultantId);

            List l = q.list();
            if (!l.isEmpty()) {
                cr = (ConsultantRating) q.list().get(0);
            }

        } catch (HibernateException exp) {
        } finally {
            session.close();
        }



        return cr;
    }
}
