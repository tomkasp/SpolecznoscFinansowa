package com.efsf.sf.sql.dao;

import com.efsf.sf.sql.entity.MaritalStatus;
import com.efsf.sf.sql.util.HibernateUtil;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.faces.model.SelectItem;
import org.hibernate.classic.Session;

/**
 *
 * @author admin
 */
public class MaritalStatusDAO {
   
    private List lista = new ArrayList();
    
    public List maritalStatusList(){
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        
        Iterator i = session.createQuery("from MaritalStatus").list().iterator();
        while(i.hasNext()){
            MaritalStatus ms = (MaritalStatus)i.next();
            lista.add(new SelectItem(ms.getIdMaritalStatus(),ms.getName()));
        }
        session.getTransaction().commit();
        session.close();
        return lista;
    }
}
