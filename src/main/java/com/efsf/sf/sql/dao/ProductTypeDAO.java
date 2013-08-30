package com.efsf.sf.sql.dao;

import com.efsf.sf.sql.entity.ProductType;
import com.efsf.sf.sql.util.HibernateUtil;
import java.io.Serializable;
import java.util.List;
import org.hibernate.classic.Session;

/**
 *
 * @author admin
 */
public class ProductTypeDAO implements Serializable {

    public ProductType getProductType(int id) {
        Session session = HibernateUtil.SESSION_FACTORY.openSession();
        ProductType ms = null;
        
        try {
            session.beginTransaction();
            ms = (ProductType) session.get(ProductType.class, id);
            session.getTransaction().commit();
        } finally {
            session.close();
        }
        
        return ms;
    }

    public List productTypeList() {
        List<ProductType> lista = null;
        Session session = HibernateUtil.SESSION_FACTORY.openSession();
        
        try {
            session.beginTransaction();
            lista = session.createQuery("from ProductType").list();
            session.getTransaction().commit();
        } finally {
            session.close();
        }
        
        return lista;
    }
}
