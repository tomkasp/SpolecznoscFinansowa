package com.efsf.sf.sql.dao;

import com.efsf.sf.sql.entity.Region;
import com.efsf.sf.sql.util.HibernateUtil;
import java.util.List;
import org.hibernate.Session;

public class RegionDAO {

    public List regionList() {

        List<Region> lista = null;
        Session session = HibernateUtil.SESSION_FACTORY.openSession();

        try {
            session.beginTransaction();
            lista = session.createQuery("from Region").list();
            session.getTransaction().commit();
        } finally {
            session.close();
        }
        
        return lista;
    }

    public Region getRegion(int id) {
        Session session = HibernateUtil.SESSION_FACTORY.openSession();
        Region ms = null;
        
        try {
            session.beginTransaction();
            ms = (Region) session.get(Region.class, id);
            session.getTransaction().commit();
        } finally {
            session.close();
        }

        return ms;

    }
}
