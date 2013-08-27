package com.efsf.sf.sql.dao;

import com.efsf.sf.sql.entity.ProductDetails;
import com.efsf.sf.sql.util.HibernateUtil;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.classic.Session;


public class ProductDAO {
    
    
    public List<ProductDetails> getAllProducts(){
        
        Session session = HibernateUtil.SESSION_FACTORY.openSession();
        session.beginTransaction();
        
        Query q = session.createQuery("from ProductDetails as pd left join fetch pd.employmentType et join fetch pd.product pr join fetch pr.institution");
        
        List<ProductDetails> products =  q.list();
        
        session.getTransaction().commit();
        session.close();
        
        return products;
    }
    
    
}
