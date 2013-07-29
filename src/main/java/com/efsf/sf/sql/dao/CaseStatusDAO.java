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
    
    public List caseStatusList(){
        List<CaseStatus> lista;
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        lista = session.createQuery("from CaseStatus").list();
        session.getTransaction().commit();
        session.close();
        return lista;
    }

}
