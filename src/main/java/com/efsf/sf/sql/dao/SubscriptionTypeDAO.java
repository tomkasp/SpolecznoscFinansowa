package com.efsf.sf.sql.dao;

import com.efsf.sf.sql.entity.SubscriptionType;
import com.efsf.sf.sql.util.HibernateUtil;
import java.util.List;
import org.hibernate.classic.Session;

public class SubscriptionTypeDAO {

    public SubscriptionType getSubscriptionType(int id) {
        SubscriptionType st = null;
        Session session = HibernateUtil.SESSION_FACTORY.openSession();
        
        try {
            st = (SubscriptionType) session.get(SubscriptionType.class, id);
        } finally {
            session.close();
        }

        return st;
    }

    public List subscriptionTypeList() {
        List<SubscriptionType> lista;
        Session session = HibernateUtil.SESSION_FACTORY.openSession();
        
        try {
            lista = session.createQuery("from SubscriptionType").list();
        } finally {
            session.close();
        }
        
        return lista;
    }
}
