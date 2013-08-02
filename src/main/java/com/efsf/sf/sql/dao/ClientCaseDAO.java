package com.efsf.sf.sql.dao;

import com.efsf.sf.bean.LoginMB;
import com.efsf.sf.sql.entity.CaseStatus;
import com.efsf.sf.sql.entity.Client;
import com.efsf.sf.sql.entity.ClientCase;
import com.efsf.sf.sql.entity.ProductType;
import com.efsf.sf.sql.util.HibernateUtil;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.joda.time.DateTime;

/**
 *
 * @author admin
 */
public class ClientCaseDAO {

    public void saveClientCase(ClientCase clientCase) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();

        
            //pierwsza faza CaseStatus.
            clientCase.setCaseStatus( (CaseStatus)session.load(CaseStatus.class, 1) );
            session.save(clientCase);

        session.getTransaction().commit();
        session.close();
    }

    public List last5Cases()
    {
         List<ClientCase> list;
         Session session = HibernateUtil.getSessionFactory().openSession();
         session.beginTransaction();
         
    //     Query q = session.createQuery("from ClientCase c left outer join fetch c.productType as p left outer join fetch c.client as cil left outer join cil.addresses as add");
         Query q = session.createQuery("FROM ClientCase as cs "
                 + "join fetch cs.client as clt "
                 + "join fetch cs.productType as pt "      
                 + "left join fetch cs.consultants as consul "
                 + "join fetch clt.addresses as addr "
                 + "left join fetch clt.incomes as inc "
                 + "left join fetch clt.incomeBusinessActivities as ba "
                 + "left join fetch inc.branch as br "
                 + "left join fetch inc.employmentType as empltype "
                 + "left join fetch ba.branch as br2 "
                 + "left join fetch ba.employmentType as empltype2 "
                 + "left join fetch clt.requiredDocumentses as rd "
                 + "where cs.beginDate <= :dateNow "
                 + "order by cs.beginDate desc, cs.idClientCase");
         
         q.setParameter("dateNow", new DateTime().toDate());
         q.setMaxResults(5);
         
         list = q.list();
         
         
         
         session.getTransaction().commit();
         session.close();
         

         return list;
    }
    

   
}
