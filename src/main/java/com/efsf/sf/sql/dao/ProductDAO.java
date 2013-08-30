package com.efsf.sf.sql.dao;

import com.efsf.sf.sql.entity.Product;
import com.efsf.sf.sql.entity.ProductDetails;
import com.efsf.sf.sql.util.HibernateUtil;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.classic.Session;

public class ProductDAO {

    public List<ProductDetails> getAllProducts() {

        Session session = HibernateUtil.SESSION_FACTORY.openSession();
        session.beginTransaction();
        List<ProductDetails> products = null;
        
        try {
            Query q = session.createQuery("from ProductDetails as pd left join fetch pd.employmentType et join fetch pd.product pr join fetch pr.institution");
            products = q.list();
            session.getTransaction().commit();
        } finally {
            session.close();
        }

        return products;
    }

    public Product getProductForDetails(Integer idDetails) {
        Session session = HibernateUtil.SESSION_FACTORY.openSession();
        List l = null;
        
        try {
            session.beginTransaction();
            Query q = session.createQuery("from Product as p left join fetch p.productDetailses as pd left join fetch p.institution as ins where pd.idProductDetail = :idDetails");
            q.setParameter("idDetails", idDetails);
            l = q.list();
            session.getTransaction().commit();
        } finally {
            session.close();
        }

        return l != null ? (Product) l.get(0) : null;
    }

    public Product getProductWithInstitution(Integer idProduct) {
        Session session = HibernateUtil.SESSION_FACTORY.openSession();
        List l = null;
        
        try {
            session.beginTransaction();
            Query q = session.createQuery("from Product as p left join fetch p.institution as ins where p.idProduct = :idProduct");
            q.setParameter("idProduct", idProduct);
            l = q.list();
            session.getTransaction().commit();
        } finally {
            session.close();
        }

        return l != null ? (Product) l.get(0) : null;
    }
}
