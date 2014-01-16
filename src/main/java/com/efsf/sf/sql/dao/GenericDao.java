package com.efsf.sf.sql.dao;

import com.efsf.sf.sql.util.HibernateUtil;
import java.io.Serializable;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.classic.Session;
import org.hibernate.criterion.Projections;

public class GenericDao<T> implements Serializable{

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

    public T getById(Serializable id) {
        Session session = HibernateUtil.SESSION_FACTORY.openSession();
        T obj;
        try {
            obj = (T) session.get(getMyType(), id);
        } finally {
            session.close();
        }
        return obj;
    }
    
    public Integer getMaxInt(String field){
        Session session = HibernateUtil.SESSION_FACTORY.openSession();
        Integer result;
        try {
            
            Criteria criteria = session
                    .createCriteria(getMyType())
                    .setProjection(Projections.max(field));
            result = (Integer) criteria.uniqueResult();
        } finally {
            session.close();
        }
        return result; 
    }

    public List<T> getAll() {
        List<T> lista;
        Session session = HibernateUtil.SESSION_FACTORY.openSession();
        try {
            lista = session.createQuery("from " + getMyTypeAsString()).list();
        } finally {
            session.close();
        }
        return lista;
    }

    public List<T> getAllInOrder(String field, String orderType) {
        List<T> lista;
        Session session = HibernateUtil.SESSION_FACTORY.openSession();
        try {
            lista = session.createQuery("from " + getMyTypeAsString()
                    + " order by " + field + " " + orderType).list();
        } finally {
            session.close();
        }
        return lista;
    }
    
    public T getLastWhere(String whereField, String whereValue, String field, String orderType) {
        T obj;
        Session session = HibernateUtil.SESSION_FACTORY.openSession();
        try {
            obj = (T) session.createQuery("from " + getMyTypeAsString()
                    + " where " + whereField + "=" + whereValue
                    +" order by " + field + " " + orderType + " LIMIT 1").list().get(0);
        } finally {
            session.close();
        }
        return obj;
    }

    public List<T> getWhere(String field, Object value) {
        List<T> lista;
        Session session = HibernateUtil.SESSION_FACTORY.openSession();
        try {
            lista = session.createQuery("from " + getMyTypeAsString() + " where " + field + "=" + value).list();
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
    
    public void delete(T obj) {

        Session session = HibernateUtil.SESSION_FACTORY.openSession();
        try {
            session.beginTransaction().begin();
            session.delete(obj);
            session.getTransaction().commit();
        } finally {
            session.close();
        }

    }    
}