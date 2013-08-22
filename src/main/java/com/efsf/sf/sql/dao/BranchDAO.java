package com.efsf.sf.sql.dao;


import com.efsf.sf.sql.entity.Branch;
import com.efsf.sf.sql.util.HibernateUtil;
import java.util.List;
import org.hibernate.Session;


public class BranchDAO
{
        public List branchList(){
        List<Branch> list;
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        list = session.createQuery("from Branch").list();
        session.getTransaction().commit();
        session.close();
        return list;
    }
    
}
