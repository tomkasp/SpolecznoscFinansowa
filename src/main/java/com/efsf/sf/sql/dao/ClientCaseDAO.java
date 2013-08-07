package com.efsf.sf.sql.dao;

import com.efsf.sf.sql.entity.CaseStatus;
import com.efsf.sf.sql.entity.ClientCase;
import com.efsf.sf.sql.util.HibernateUtil;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
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
    
    public ClientCase read(int idClientCase)
    {
        ClientCase cs;
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        
        cs = (ClientCase) session.get(ClientCase.class, idClientCase);
        
        session.getTransaction().commit();
        session.close();
                
        return cs;  
    }

    public List last5Cases()
    {
         List<ClientCase> list;
         Session session = HibernateUtil.getSessionFactory().openSession();
         session.beginTransaction();
         
    //   Query q = session.createQuery("from ClientCase c left outer join fetch c.productType as p left outer join fetch c.client as cil left outer join cil.addresses as add");
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
                 + "and cs.endDate >= :dateNow "
                 + "and cstats.idCaseStatus = 1 "
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
    
    public boolean doesConsultantAppliedToCase(int consultantID, int caseID)
    {
         boolean flag;
         Session session = HibernateUtil.getSessionFactory().openSession();
         session.beginTransaction();
         
         Query q = session.createQuery("from Consultant as c "
                 + "join fetch c.clientCases as cc "
                 + "where c.idConsultant = :consultantID and cc.idClientCase = :caseID");
         q.setParameter("caseID", caseID);
         q.setParameter("consultantID", consultantID);
         
         flag =  q.list().isEmpty();
         
         session.getTransaction().commit();
         session.close();
         
         return !flag;
    }
    
    public List getCasesWithMarketFilter(int phaseMin, int phaseMax, int ageMin, int ageMax, int diffMin, int diffMax, int branch, int region, ArrayList<String> incomes, ArrayList<String> business)
    {
         List<ClientCase> list;
         Session session = HibernateUtil.getSessionFactory().openSession();
         session.beginTransaction();
         Query q = null; 
         //CASE: SELECTED BRANCH AND CASE
         if (region > 0 && branch > 0)
         {
                q = session.createQuery("FROM ClientCase as cs "
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
                    + "left join fetch addr.region as reg "
                    + "where cs.beginDate <= :dateNow "
                    + "and reg.idRegion = :region "
                    + "and (br.idBranch = :branch or br2.idBranch = :branch) "
                    + "and (empltype.shortcut in (:incomes) or empltype2.shortcut in (:business)) "
                    + "and cs.endDate >= :dateNow "
                    + "and clt.birthDate <= :ageMin "
                    + "and clt.birthDate >= :ageMax "
                    + "and cs.phase >= :phaseMin "
                    + "and cs.phase <= :phaseMax "
                    + "and cs.difficulty >= :diffMin "
                    + "and cs.difficulty <= :diffMax " 
                    + "and cstats.idCaseStatus = 1 "
                    + "order by cs.beginDate desc, cs.idClientCase desc");
                
                    q.setParameter("branch", branch);  
                    q.setParameter("region", region);      
         }
         // CASE: ALL REGIONS AND SELECTED BRANCH
         else if ( branch > 0 && region == 0)
         {
               q = session.createQuery("FROM ClientCase as cs "
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
                    + "left join fetch addr.region as reg "
                    + "where cs.beginDate <= :dateNow "
                    + "and cs.endDate >= :dateNow "
                    + "and (br.idBranch = :branch or br2.idBranch = :branch) " 
                    + "and (empltype.shortcut in (:incomes) or empltype2.shortcut in (:business)) "
                    + "and clt.birthDate <= :ageMin "
                    + "and clt.birthDate >= :ageMax "
                    + "and cs.phase >= :phaseMin "
                    + "and cs.phase <= :phaseMax "
                    + "and cs.difficulty >= :diffMin "
                    + "and cs.difficulty <= :diffMax "
                    + "and cstats.idCaseStatus = 1 "
                    + "order by cs.beginDate desc, cs.idClientCase desc");
               
               q.setParameter("branch", branch);  
         }
         // CASE: ALL BRANCHES AND SELECTED REGION
         else if ( branch == 0 && region > 0)
         {
               q = session.createQuery("FROM ClientCase as cs "
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
                    + "left join fetch addr.region as reg "
                    + "where cs.beginDate <= :dateNow "
                    + "and reg.idRegion = :region "
                    + "and (empltype.shortcut in (:incomes) or empltype2.shortcut in (:business)) "
                    + "and cs.endDate >= :dateNow "
                    + "and clt.birthDate <= :ageMin "
                    + "and clt.birthDate >= :ageMax "
                    + "and cs.phase >= :phaseMin "
                    + "and cs.phase <= :phaseMax "
                    + "and cs.difficulty >= :diffMin "
                    + "and cs.difficulty <= :diffMax " 
                    + "and cstats.idCaseStatus = 1 "
                    + "order by cs.beginDate desc, cs.idClientCase desc");
               
               q.setParameter("region", region);
         }
         // CASE: ALL BRANCHES AND ALL REGIONS
         else
         {
               q = session.createQuery("FROM ClientCase as cs "
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
                    + "left join fetch addr.region as reg "
                    + "where cs.beginDate <= :dateNow "
                    + "and cs.endDate >= :dateNow "
                    + "and (empltype.shortcut in (:incomes) or empltype2.shortcut in (:business)) "
                    + "and clt.birthDate <= :ageMin "
                    + "and clt.birthDate >= :ageMax "
                    + "and cs.phase >= :phaseMin "
                    + "and cs.phase <= :phaseMax "
                    + "and cs.difficulty >= :diffMin "
                    + "and cs.difficulty <= :diffMax "
                    + "and cstats.idCaseStatus = 1 "
                    + "order by cs.beginDate desc, cs.idClientCase desc");
         }
         
         if (incomes.isEmpty())
         {
             incomes.add("");
         }
         if (business.isEmpty())
         {
             business.add("");
         }
         q.setParameterList("incomes", incomes);
         q.setParameterList("business", business);
         q.setParameter("dateNow", new DateTime().toDate());
         q.setParameter("dateNow", new DateTime().toDate()); 
         q.setParameter("phaseMin", phaseMin);
         q.setParameter("phaseMax", phaseMax);
         q.setParameter("ageMin", new DateTime().minusYears(ageMin).toDate());
         q.setParameter("ageMax", new DateTime().minusYears(ageMax).toDate());
         q.setParameter("diffMin", diffMin);
         q.setParameter("diffMax", diffMax);
         list = q.list();
                 
         session.getTransaction().commit();
         session.close();
         
         Set setItems = new LinkedHashSet(list); 
         list.clear();
         list.addAll(setItems);

         return list;
    }
    
    
    
    
    

   
}
