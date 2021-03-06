package com.efsf.sf.sql.dao;

import com.efsf.sf.sql.entity.RequiredDocuments;
import com.efsf.sf.sql.util.HibernateUtil;
import org.hibernate.Query;
import org.hibernate.Session;

public class RequiredDocumentsDAO {

    public RequiredDocuments read(int id) {

        RequiredDocuments requiredDocuments = null;
        Session session = HibernateUtil.SESSION_FACTORY.openSession();
        
        try {
            session.beginTransaction().begin();
            requiredDocuments = (RequiredDocuments) session.get(RequiredDocuments.class, id);
            session.getTransaction().commit();
        } finally {
            session.close();
        }
        
        return requiredDocuments;

    }

    public RequiredDocuments readForFkClient(int fkClient) {

        RequiredDocuments requiredDocuments = null;
        Session session = HibernateUtil.SESSION_FACTORY.openSession();
        
        try {
            session.beginTransaction().begin();
            Query q = session.createQuery("FROM RequiredDocuments WHERE fk_client = :id");
            q.setParameter("id", fkClient);
            if (!q.list().isEmpty()) {
                requiredDocuments = (RequiredDocuments) q.list().get(0);
            }
            session.getTransaction().commit();
        } finally {
            session.close();
        }
        
        return requiredDocuments;

    }

    public void save(RequiredDocuments requiredDocuments) {

        Session session = HibernateUtil.SESSION_FACTORY.openSession();
        
        try {
            session.beginTransaction().begin();
            session.save(requiredDocuments);
            session.getTransaction().commit();
        } finally {
            session.close();
        }

    }

    public void saveOrUpdate(RequiredDocuments requiredDocuments) {

        Session session = HibernateUtil.SESSION_FACTORY.openSession();
        
        try {
            session.beginTransaction().begin();
            session.saveOrUpdate(requiredDocuments);
            session.getTransaction().commit();
        } finally {
            session.close();
        }

    }

    public void update(RequiredDocuments requiredDocuments) {

        Session session = HibernateUtil.SESSION_FACTORY.openSession();
        
        try {
            session.beginTransaction().begin();
            session.update(requiredDocuments);
            session.getTransaction().commit();
        } finally {
            session.close();
        }

    }

    public void delete(RequiredDocuments requiredDocuments) {

        Session session = HibernateUtil.SESSION_FACTORY.openSession();
        
        try {
            session.beginTransaction().begin();
            session.delete(requiredDocuments);
            session.getTransaction().commit();
        } finally {
            session.close();
        }

    }
}
