package com.efsf.sf.sql.dao;

import com.efsf.sf.sql.entity.EmploymentType;
import com.efsf.sf.sql.entity.Institution;
import com.efsf.sf.sql.entity.MaritalStatus;
import com.efsf.sf.sql.util.HibernateUtil;
import java.util.List;
import org.hibernate.Session;

/**
 *
 * @author WR1EI1
 */
public class InstitutionDAO {

    public List bankList() {
        List<EmploymentType> list;
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();

        list = session.createQuery("from Institution where type = 0").list();

        session.getTransaction().commit();
        session.close();
        return list;
    }

    public List institutionList() {
        List<EmploymentType> list;
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();

        list = session.createQuery("from Institution where type = 1").list();

        session.getTransaction().commit();
        session.close();
        return list;
    }
    
    public Institution getInstitution(int id)
    {
        org.hibernate.classic.Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        
        Institution ms = (Institution) session.get(Institution.class, id);
                
        session.getTransaction().commit();
        session.close();
        
        return ms;   
    }
    
    
    
    
}
