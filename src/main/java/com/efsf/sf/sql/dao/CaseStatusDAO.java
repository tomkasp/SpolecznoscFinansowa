/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.efsf.sf.sql.dao;

import com.efsf.sf.sql.entity.CaseStatus;
import com.efsf.sf.sql.util.HibernateUtil;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.faces.model.SelectItem;
import org.hibernate.Session;

/**
 *
 * @author admin
 */
public class CaseStatusDAO {
    private List lista = new ArrayList();
    
    public List regionList(){
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        
        Iterator i = session.createQuery("from CaseStatus").list().iterator();
        while(i.hasNext()){
            CaseStatus cs = (CaseStatus)i.next();
            lista.add(new SelectItem(cs.getIdCaseStatus(),cs.getName()));
        }
        session.getTransaction().commit();
        session.close();
        return lista;
    }

}
