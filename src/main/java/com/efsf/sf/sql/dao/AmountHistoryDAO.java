package com.efsf.sf.sql.dao;

import com.efsf.sf.sql.entity.AmountHistory;
import com.efsf.sf.sql.entity.Client;
import com.efsf.sf.sql.util.HibernateUtil;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.type.Type;

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
    
    public List<AmountHistory> getWithMonthAndYear(int month, int year, Client client)
    {
        List list;
        Session session = HibernateUtil.SESSION_FACTORY.openSession();
        try {
            session.beginTransaction();

            DetachedCriteria criteria = DetachedCriteria.forClass(AmountHistory.class,"ah")
                .createAlias("ah.client", "client")
                .add(Restrictions.sqlRestriction("YEAR(operationDate) = ? ", year,Hibernate.INTEGER))
                .add(Restrictions.sqlRestriction("MONTH(operationDate) = ? ", month,Hibernate.INTEGER))
                .add(Restrictions.eq("client.idClient", client.getIdClient()));
            
            Criteria crit = criteria.getExecutableCriteria(session);
            
            list = crit.list();
            
            session.getTransaction().commit();
        } finally {
            session.close();
        }
        
        return list;
    }
    
    public BigDecimal getClientsSumOfContinousIncome(Client client)
    {
        BigDecimal result; 
        
        Session session = HibernateUtil.SESSION_FACTORY.openSession();
        try {
            session.beginTransaction();
            Query query = session.createQuery(" Select sum(ah.amount) FROM AmountHistory as ah left join ah.client as cl left join ah.operationType as ot where cl.idClient=:client and ot.idOperationType = 6");
            query.setParameter("client", client.getIdClient());
            
          
            result = (BigDecimal) query.list().get(0);

            session.getTransaction().commit();
        } finally {
            session.close();
        }

        return result;
    }
    
    public BigDecimal getClientsSumOfExpenses(Client client)
    {
        BigDecimal result; 
        
        Session session = HibernateUtil.SESSION_FACTORY.openSession();
        try {
            session.beginTransaction();
            Query query = session.createQuery(" Select sum(ah.amount) FROM AmountHistory as ah left join ah.client as cl left join ah.operationType as ot where cl.idClient=:client and ah.amount < 0");
            query.setParameter("client", client.getIdClient());
            
          
            result = (BigDecimal) query.list().get(0);

            session.getTransaction().commit();
        } finally {
            session.close();
        }

        return result;
    }
    
    public int getClientCountForDifferentMonthHistories(Client client)
    {
        List list;
        Session session = HibernateUtil.SESSION_FACTORY.openSession();
        try {
            session.beginTransaction();

            Criteria criteria = session.createCriteria(AmountHistory.class, "ah")
            .createAlias("ah.client", "client")
            .add(Restrictions.eq("client.idClient", client.getIdClient()));

            ProjectionList projectionList = Projections.projectionList();
            projectionList.add(Projections.sum("amount"));
            projectionList.add(Projections.sqlGroupProjection(
                     "month({alias}.operationDate) as month, year({alias}.operationDate) as year", 
                    "month({alias}.operationDate), year({alias}.operationDate)", 
            new String[]{"month","year"}, 
            new Type[] {Hibernate.DOUBLE}));
            criteria.setProjection(projectionList);
           
            
            list = criteria.list();
            
            session.getTransaction().commit();
        } finally {
            session.close();
        }
        
        return (int) list.size();
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
