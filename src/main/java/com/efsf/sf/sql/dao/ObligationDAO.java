package com.efsf.sf.sql.dao;

import com.efsf.sf.sql.entity.Obligation;
import com.efsf.sf.sql.util.HibernateUtil;
import java.io.Serializable;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import org.hibernate.Session;

@ManagedBean
@SessionScoped
public class ObligationDAO implements Serializable{

    public ObligationDAO() {
    }
    
    public void deleteObligation(Obligation ob){
        Session session = HibernateUtil.SESSION_FACTORY.openSession();
        session.beginTransaction();
        
        session.delete(ob);
        
        session.getTransaction().commit();
        session.close();
    }
    
    
    public Obligation getObligation(int id) {
        Session session = HibernateUtil.SESSION_FACTORY.openSession();
        session.beginTransaction();

        Obligation ob = (Obligation) session.get(Obligation.class, id);

        session.getTransaction().commit();
        session.close();

        return ob;

    }

    public List obligationListForClient(Integer idUser) {
        List<Obligation> lista;
        Session session = HibernateUtil.SESSION_FACTORY.openSession();
        session.beginTransaction();

        lista = session.createQuery("from Obligation ob join fetch ob.client cl where cl.idClient= :user ").setParameter("user",idUser).list();
                
        session.getTransaction().commit();
        session.close();
        return lista;
    }

    public void save(Obligation ob) {
        Session session = HibernateUtil.SESSION_FACTORY.openSession();
        session.beginTransaction();
        
        session.save(ob);
        
        session.getTransaction().commit();
        session.close();
    }
}
