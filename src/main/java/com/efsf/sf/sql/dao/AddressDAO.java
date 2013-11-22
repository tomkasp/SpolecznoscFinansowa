package com.efsf.sf.sql.dao;

import com.efsf.sf.sql.entity.Address;
import com.efsf.sf.sql.util.HibernateUtil;
import org.hibernate.Query;
import org.hibernate.Session;

public class AddressDAO {

    public void save(Address address) {

        Session session = HibernateUtil.SESSION_FACTORY.openSession();
        try {
            session.beginTransaction();
            session.save(address);
            session.getTransaction().commit();

        } finally {
            session.close();
        }
    }

    public void update(Address address) {

        Session session = HibernateUtil.SESSION_FACTORY.openSession();
        try {
            session.beginTransaction();
            session.update(address);
            session.getTransaction().commit();
        } finally {
            session.close();
        }
    }

    public Address loadMainAddressFromFkConsultant(Integer fkConsuntant) {
        return loadAddressFromFkConsultant(fkConsuntant, 1);
    }

    public Address loadInvoiceAddressFromFkConsultant(Integer fkConsuntant) {
        return loadAddressFromFkConsultant(fkConsuntant, 2);
    }

    private Address loadAddressFromFkConsultant(Integer fkConsuntant, Integer type) {

        Address address = null;
        Session session = HibernateUtil.SESSION_FACTORY.openSession();
        try {
            session.beginTransaction();

            Query q;
            q = session.createQuery("FROM Address WHERE fk_consultant = :id AND type = :type");
            q.setParameter("id", fkConsuntant);
            q.setParameter("type", type);
            address = q.list().isEmpty() ? null : (Address) q.list().get(0);

            session.getTransaction().commit();
        } finally {
            session.close();
        }
        return address;
    }
}