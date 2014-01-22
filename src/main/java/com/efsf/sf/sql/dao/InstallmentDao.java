/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.efsf.sf.sql.dao;

import com.efsf.sf.sql.entity.Installment;
import com.efsf.sf.sql.util.HibernateUtil;
import java.util.List;
import org.hibernate.classic.Session;

/**
 *
 * @author s.biernacki
 * Syf syf syf - klasa dostosowana poziomem do reszty projektu:) 
 */
public class InstallmentDao extends GenericDao<Installment>{

    public InstallmentDao() {
        super(Installment.class);
    }
    
   public List<Installment> getInstallmentsToNotify() {
        List<Installment> lista;
        Session session = HibernateUtil.SESSION_FACTORY.openSession();
        try {
            lista = (List<Installment>)session.createQuery("from Installment as ins left join fetch ins.clientCase as clc"
                    + " left join fetch clc.client as cl"
                    + " left join fetch cl.addresses WHERE DATEDIFF(repayment_date,CURRENT_DATE()) <=5").list();
        } finally {
            session.close();
        }
        return lista;
    }
}
