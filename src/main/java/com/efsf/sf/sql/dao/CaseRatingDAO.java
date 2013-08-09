/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.efsf.sf.sql.dao;

import com.efsf.sf.sql.entity.CaseRating;
import com.efsf.sf.sql.util.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;

/**
 * @author WR1EI1
 */
public class CaseRatingDAO {

    public void save(CaseRating rating) {

        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            session.save(rating);
            session.getTransaction().commit();
        } catch (HibernateException e) {
        } finally {
            session.close();
        }
    }

    public boolean isNotRated(Integer idClientCase) {
        CaseRating rating = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            rating = (CaseRating) session.get(CaseRating.class, idClientCase);
        } catch (HibernateException e) {
        } finally {
            session.close();
        }

        return rating == null ? true : false;
    }
}