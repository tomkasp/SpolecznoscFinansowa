package com.efsf.sf.sql.dao;

import com.efsf.sf.sql.entity.CaseStatus;
import com.efsf.sf.sql.entity.ClientCase;
import com.efsf.sf.sql.util.HibernateUtil;
import java.io.Serializable;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.joda.time.DateTime;

/**
 *
 * @author admin
 */
public class ClientCaseDAO implements Serializable {

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
                 + "join fetch cs.caseStatus as cstats "
                 + "left join fetch clt.incomes as inc "
                 + "left join fetch clt.incomeBusinessActivities as ba "
                 + "left join fetch inc.branch as br "
                 + "left join fetch inc.employmentType as empltype "
                 + "left join fetch ba.branch as br2 "
                 + "left join fetch ba.employmentType as empltype2 "
                 + "left join fetch clt.requiredDocumentses as rd "        
                 + "where cs.beginDate <= :dateNow "
                 + "order by cs.beginDate desc, cs.idClientCase desc");
         
         q.setParameter("dateNow", new DateTime().toDate());
         q.setMaxResults(5);
         
         list = q.list();
         
         session.getTransaction().commit();
         session.close();
         

         return list;
    }
   
    public boolean doesConsultantObserveCase(int consultantID, int caseID)
    {
         boolean flag;
         Session session = HibernateUtil.getSessionFactory().openSession();
         session.beginTransaction();
         
         Query q = session.createQuery("from Consultant as c "
                 + "join fetch c.clientCases_2 as cc2 "
                 + "where c.idConsultant = :consultantID and cc2.idClientCase = :caseID");
         q.setParameter("caseID", caseID);
         q.setParameter("consultantID", consultantID);
         
         flag =  q.list().isEmpty();
         
         session.getTransaction().commit();
         session.close();
         
         return !flag;
    }
    
     
    public List last5CasesSelectedClient(Integer fkClient)
    {
         List<ClientCase> list;
         Session session = HibernateUtil.getSessionFactory().openSession();
         session.beginTransaction();
        
         Query q = session.createQuery("FROM ClientCase as cs "
                 + "left join fetch cs.client as clt "
                 + "left join fetch cs.productType as pt "      
                 + "left join fetch cs.consultants as consul "
                 + "left join fetch clt.addresses as addr "
                 + "left join fetch clt.incomes as inc "
                 + "left join fetch clt.incomeBusinessActivities as ba "
                 + "left join fetch inc.branch as br "
                 + "left join fetch inc.employmentType as empltype "
                 + "left join fetch ba.branch as br2 "
                 + "left join fetch ba.employmentType as empltype2 "
                 + "left join fetch clt.requiredDocumentses as rd "
                 + "where cs.beginDate <= :dateNow "
                 + "and cs.endDate >= :dateNow "
                 + "and clt.idClient = :fk "
                 + "and cs.caseStatus = 1 "
                 + "order by cs.beginDate desc, cs.idClientCase desc ");
         
         q.setParameter( "dateNow", new DateTime().toDate() );
         q.setParameter("fk", fkClient );
         q.setMaxResults(5);
         
         list = q.list();
         
         session.getTransaction().commit();
         session.close();
         
         return list;
    }

    public List awaitingCasesSelectedClient(Integer fkClient)
    {
         List<ClientCase> list;
         Session session = HibernateUtil.getSessionFactory().openSession();
         session.beginTransaction();
         
         Query q = session.createQuery("FROM ClientCase as cs "
                 + "left join fetch cs.client as clt "
                 + "left join fetch cs.productType as pt "      
                 + "left join fetch cs.consultants as consul "
                 + "left join fetch clt.addresses as addr "
                 + "left join fetch clt.incomes as inc "
                 + "left join fetch clt.incomeBusinessActivities as ba "
                 + "left join fetch inc.branch as br "
                 + "left join fetch inc.employmentType as empltype "
                 + "left join fetch ba.branch as br2 "
                 + "left join fetch ba.employmentType as empltype2 "
                 + "left join fetch clt.requiredDocumentses as rd "
                 + "where cs.beginDate <= :dateNow "
                 + "and clt.idClient = :fk "
                 + "and cs.caseStatus = 2 "
                 + "order by cs.beginDate desc, cs.idClientCase desc ");
         
         q.setParameter( "dateNow", new DateTime().toDate() );
         q.setParameter("fk", fkClient );
         
         list = q.list();
         
         session.getTransaction().commit();
         session.close();
         return list;
    }
    
    public List currentCasesSelectedClient(Integer fkClient)
    {
         List<ClientCase> list;
         Session session = HibernateUtil.getSessionFactory().openSession();
         session.beginTransaction();
         
         Query q = session.createQuery("FROM ClientCase as cs "
                 + "left join fetch cs.client as clt "
                 + "left join fetch cs.productType as pt "      
                 + "left join fetch cs.consultants as consul "
                 + "left join fetch clt.addresses as addr "
                 + "left join fetch clt.incomes as inc "
                 + "left join fetch clt.incomeBusinessActivities as ba "
                 + "left join fetch inc.branch as br "
                 + "left join fetch inc.employmentType as empltype "
                 + "left join fetch ba.branch as br2 "
                 + "left join fetch ba.employmentType as empltype2 "
                 + "left join fetch clt.requiredDocumentses as rd "
                 + "where cs.beginDate <= :dateNow "
                 + "and clt.idClient = :fk "
                 + "and cs.caseStatus > 2 "
                 + "order by cs.beginDate desc, cs.idClientCase desc ");
         
         q.setParameter( "dateNow", new DateTime().toDate() );
         q.setParameter("fk", fkClient );
         
         list = q.list();
         
         session.getTransaction().commit();
         session.close();
         return list;
    }
    
   
}
