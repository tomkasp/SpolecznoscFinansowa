/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.efsf.sf.beans;

import com.efsf.sf.sql.entity.CaseRating;
import com.efsf.sf.sql.entity.ClientCase;
import com.efsf.sf.sql.util.HibernateUtil;
import org.hibernate.Session;

/**
 *
 * @author admin
 */
public class test {
    public static void main(String args[]){
        
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        
        ClientCase cs = (ClientCase)session.load(ClientCase.class, 3);
        
        System.out.println("z clientCase"+ cs.getConsolidationValue());
        
        CaseRating cr = new CaseRating();
        cr.setClientCase(cs);
        
        cr.setAverage(32.23);
        cr.setCulture(3);
        
        
        session.save(cr);
        session.getTransaction().commit();
        session.close();
    }
}
