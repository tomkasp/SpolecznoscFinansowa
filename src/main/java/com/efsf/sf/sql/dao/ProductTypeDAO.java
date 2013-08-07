package com.efsf.sf.sql.dao;

import com.efsf.sf.sql.entity.MaritalStatus;
import com.efsf.sf.sql.entity.ProductType;
import com.efsf.sf.sql.util.HibernateUtil;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.faces.model.SelectItem;
import org.hibernate.classic.Session;

/**
 *
 * @author admin
 */
public class ProductTypeDAO implements Serializable{
   
    public ProductType getProductType(int id)
    {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        
        ProductType ms = (ProductType) session.get(ProductType.class, id);
                
        session.getTransaction().commit();
        session.close();
        
        return ms;
        
    }
    
    public List productTypeList(){
        List<ProductType> lista;
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        
        lista = session.createQuery("from ProductType").list();
        
        session.getTransaction().commit();
        session.close();
        return lista;
    }
}
