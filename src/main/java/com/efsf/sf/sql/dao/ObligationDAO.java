package com.efsf.sf.sql.dao;

import com.efsf.sf.sql.entity.Obligation;
import com.efsf.sf.sql.util.HibernateUtil;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import org.hibernate.Session;

@ManagedBean
@SessionScoped
public class ObligationDAO {

    public ObligationDAO() {
    }

    public Obligation getObligation(int id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();

        Obligation ob = (Obligation) session.get(Obligation.class, id);

        session.getTransaction().commit();
        session.close();

        return ob;

    }

    public List obligationList() {
        List<Obligation> lista;
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();

        lista = session.createQuery("from Obligation").list();

        session.getTransaction().commit();
        session.close();
        return lista;
    }

    public void save(Obligation ob) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        
        session.save(ob);
        
        session.getTransaction().commit();
        session.close();
    }
}
