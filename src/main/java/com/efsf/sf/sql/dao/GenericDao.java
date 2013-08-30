package com.efsf.sf.sql.dao;

import com.efsf.sf.sql.util.HibernateUtil;
import java.util.List;
import org.hibernate.classic.Session;

public class GenericDao<T> {

    private final Class<T> type;

    public GenericDao(Class<T> type) {
        this.type = type;
    }

    private Class<T> getMyType() {
        return this.type;
    }

    private String getMyTypeAsString() {
        return this.type.getName();
    }

    public T getById(int id) {
        Session session = HibernateUtil.SESSION_FACTORY.openSession();
        T obj;
        try {
            session.beginTransaction();
            obj = (T) session.get(getMyType(), id);
            session.getTransaction().commit();
        } finally {
            session.close();
        }
        return obj;
    }

    public List getAll() {
        List<T> lista;
        Session session = HibernateUtil.SESSION_FACTORY.openSession();
        try {
            session.beginTransaction();

            lista = session.createQuery("from " + getMyTypeAsString()).list();

            session.getTransaction().commit();
        } finally {
            session.close();
        }
        return lista;
    }

    public List getAllInOrder(String field, String orderType) {
        List<T> lista;
        Session session = HibernateUtil.SESSION_FACTORY.openSession();
        try {
            session.beginTransaction();

            lista = session.createQuery("from " + getMyTypeAsString()
                    + " order by " + field + " " + orderType).list();

            session.getTransaction().commit();
        } finally {
            session.close();
        }
        return lista;
    }

    public List getWhere(String field, String value) {
        List<T> lista;
        Session session = HibernateUtil.SESSION_FACTORY.openSession();
        try {
            session.beginTransaction();
            lista = session.createQuery("from " + getMyTypeAsString() + " where " + field + "=" + value).list();
            session.getTransaction().commit();
        } finally {
            session.close();
        }
        return lista;
    }

    public void save(T obj) {

        Session session = HibernateUtil.SESSION_FACTORY.openSession();
        try {
            session.beginTransaction().begin();
            session.save(obj);
            session.getTransaction().commit();
        } finally {
            session.close();
        }

    }

    public void saveOrUpdate(T obj) {

        Session session = HibernateUtil.SESSION_FACTORY.openSession();
        try {
            session.beginTransaction().begin();
            session.saveOrUpdate(obj);
            session.getTransaction().commit();
        } finally {
            session.close();
        }

    }

    public void saveOrUpdateObject(Object obj) {

        Session session = HibernateUtil.SESSION_FACTORY.openSession();
        try {
            session.beginTransaction().begin();
            session.saveOrUpdate(obj);
            session.getTransaction().commit();
        } finally {
            session.close();
        }

    }

    public void update(T obj) {

        Session session = HibernateUtil.SESSION_FACTORY.openSession();
        try {
            session.beginTransaction().begin();
            session.update(obj);
            session.getTransaction().commit();
        } finally {
            session.close();
        }

    }
}