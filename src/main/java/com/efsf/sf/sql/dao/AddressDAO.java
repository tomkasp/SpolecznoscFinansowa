package com.efsf.sf.sql.dao;

import com.efsf.sf.sql.entity.Address;
import com.efsf.sf.sql.util.HibernateUtil;
import org.hibernate.Query;
import org.hibernate.Session;

public class AddressDAO {

    public void save(Address address) {

        Session session = HibernateUtil.SESSION_FACTORY.openSession();
        try{
        session.beginTransaction();
        session.save(address);
        session.getTransaction().commit();

        }finally{
        session.close();
        }
    }
    
    public void update(Address address) {

        Session session = HibernateUtil.SESSION_FACTORY.openSession();
        try{
        session.beginTransaction();
        session.update(address);
        session.getTransaction().commit();
        }finally{
        session.close();
        }
    }
    
    
     public Address loadMainAddressFromFkConsultant(Integer fkConsuntant) {
         
        Address address = null;
        Session session = HibernateUtil.SESSION_FACTORY.openSession();
        try{
        session.beginTransaction();
        
        Query q;
        q = session.createQuery("FROM Address WHERE fk_consultant = :id AND type = 1");
        q.setParameter("id", fkConsuntant);
        address=(Address) q.list().get(0);
                
        session.getTransaction().commit();
        }finally{
        session.close();
        }
        return address;
    }
     
     
     public Address loadInvoiceAddressFromFkConsultant(Integer fkConsuntant) {
         
        Address address = null;
        Session session = HibernateUtil.SESSION_FACTORY.openSession();
        try{
        session.beginTransaction();
        
        Query q;
        q = session.createQuery("FROM Address WHERE fk_consultant = :id AND type = 2");
        q.setParameter("id", fkConsuntant);
        address=(Address) q.list().get(0);
                
        session.getTransaction().commit();
        }finally{
        session.close();
        }
        return address;
    }
    
}