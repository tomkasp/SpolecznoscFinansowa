package com.efsf.sf.sql.dao;

import com.efsf.sf.sql.entity.CaseStatus;
import com.efsf.sf.sql.entity.ClientCase;
import com.efsf.sf.sql.util.HibernateUtil;
import java.io.Serializable;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import org.hibernate.Query;
import org.hibernate.Session;
import org.joda.time.DateTime;

public class ClientCaseDAO implements Serializable {

    public void saveClientCase(ClientCase clientCase) {
        Session session = HibernateUtil.SESSION_FACTORY.openSession();
        try {
            session.beginTransaction();

            //pierwsza faza CaseStatus
            clientCase.setCaseStatus((CaseStatus) session.load(CaseStatus.class, 1));
            session.save(clientCase);
        } finally {
            session.getTransaction().commit();
            session.close();
        }
    }

    public ClientCase read(int idClientCase) {
        ClientCase cs;
        Session session = HibernateUtil.SESSION_FACTORY.openSession();
        try {
            session.beginTransaction();

            cs = (ClientCase) session.get(ClientCase.class, idClientCase);
            session.getTransaction().commit();
        } finally {

            session.close();
        }
        return cs;
    }

    public List last5Cases() {
        List<ClientCase> list;

        Session session = HibernateUtil.SESSION_FACTORY.openSession();
        try {
            session.beginTransaction();

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
        } finally {

            session.close();
        }

        return list;
    }

    public boolean doesConsultantObserveCase(int consultantID, int caseID) {
        return doesConsultantConnectedToCase(consultantID, caseID, "clientCases_2");
    }

    public boolean doesConsultantAppliedToCase(int consultantID, int caseID) {
        return doesConsultantConnectedToCase(consultantID, caseID, "clientCases");
    }

    public boolean doesConsultantConnectedToCase(int consultantID, int caseID, String relationPrefix) {
        boolean flag;
        Session session = HibernateUtil.SESSION_FACTORY.openSession();
        session.beginTransaction();

        Query q = session.createQuery("from Consultant as c "
                + "join fetch c.:relation as cc "
                + "where c.idConsultant = :consultantID and cc.idClientCase = :caseID");
        q.setParameter("caseID", caseID);
        q.setParameter("consultantID", consultantID);
        q.setParameter("relation", relationPrefix);

        flag = q.list().isEmpty();

        session.getTransaction().commit();
        session.close();

        return !flag;
    }

    public List getCasesWithMarketFilter(int phaseMin, int phaseMax, int ageMin, int ageMax, int diffMin, int diffMax, int branch, int region, List<String> incomes, List<String> business) {
        List<ClientCase> list;
        Session session = HibernateUtil.SESSION_FACTORY.openSession();
        try {
            session.beginTransaction();
            Query q;
            //CASE: SELECTED BRANCH AND CASE
            if (region > 0 && branch > 0) {
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
            } // CASE: ALL REGIONS AND SELECTED BRANCH
            else if (branch > 0 && region == 0) {
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
            } // CASE: ALL BRANCHES AND SELECTED REGION
            else if (branch == 0 && region > 0) {
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
            } // CASE: ALL BRANCHES AND ALL REGIONS
            else {
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

            if (incomes.isEmpty()) {
                incomes.add("");
            }
            if (business.isEmpty()) {
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
        } finally {

            session.close();
        }
        Set setItems = new LinkedHashSet(list);
        list.clear();
        list.addAll(setItems);

        return list;
    }

    public boolean checkClientAccess(Integer idClient, Integer idCase) {
        Session session = HibernateUtil.SESSION_FACTORY.openSession();
        List l;
        try {
            session.beginTransaction();

            Query q = session.createQuery("FROM ClientCase as cs "
                    + " left join fetch cs.client as cl "
                    + " where cl.idClient = :idClient and cs.idClientCase = :idCase ");

            q.setParameter("idCase", idCase);
            q.setParameter("idClient", idClient);

            l = q.list();
            session.getTransaction().commit();
        } finally {

            session.close();
        }
        return !(l == null || l.isEmpty());

    }

    public List<ClientCase> last5CasesSelectedClient(Integer fkClient) {
        return casesSelectedClient(fkClient, 5, false);
    }

    public List<ClientCase> casesSelectedClient(Integer fkClient, Integer limit, Boolean premium) {
        List<ClientCase> list;
        Session session = HibernateUtil.SESSION_FACTORY.openSession();
        session.beginTransaction();

        Query q = session.createQuery("FROM ClientCase as cs "
                + "left join fetch cs.client as clt "
                + "left join fetch cs.productType as pt "
                + "left join fetch cs.consultants as consul "
                + "left join fetch cs.caseStatus as cstatus "
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
                + "and cs.premium = :premium "
                + "order by cs.beginDate desc, cs.idClientCase desc ");

        q.setParameter("dateNow", new DateTime().toDate());
        q.setParameter("fk", fkClient);
        q.setParameter("premium", premium);
        q.setMaxResults(limit);

        list = q.list();

        session.getTransaction().commit();
        session.close();

        return list;
    }

    public List<ClientCase> allActiveCasesSelectedClient(Integer fkClient) {
        return casesSelectedClient(fkClient, 100, false);
    }

    public List<ClientCase> awaitingForMarketClientCaseList(Integer fkClient) {
        List<ClientCase> list;
        Session session = HibernateUtil.SESSION_FACTORY.openSession();
        session.beginTransaction();

        Query q = session.createQuery("FROM ClientCase as cs "
                + "left join fetch cs.client as clt "
                + "left join fetch cs.productType as pt "
                + "left join fetch cs.consultants as consul "
                + "left join fetch cs.caseStatus as cstatus "
                + "left join fetch clt.addresses as addr "
                + "left join fetch clt.incomes as inc "
                + "left join fetch clt.incomeBusinessActivities as ba "
                + "left join fetch inc.branch as br "
                + "left join fetch inc.employmentType as empltype "
                + "left join fetch ba.branch as br2 "
                + "left join fetch ba.employmentType as empltype2 "
                + "left join fetch clt.requiredDocumentses as rd "
                + "where cs.beginDate >= :dateNow "
                + "and clt.idClient = :fk "
                + "and cs.caseStatus = 1 "
                + "order by cs.beginDate desc, cs.idClientCase desc ");

        q.setParameter("dateNow", new DateTime().toDate());
        q.setParameter("fk", fkClient);
        q.setMaxResults(100);

        list = q.list();

        session.getTransaction().commit();
        session.close();

        return list;
    }

    public List<ClientCase> awaitingCasesSelectedClient(Integer fkClient) {
        List<ClientCase> list;
        Session session = HibernateUtil.SESSION_FACTORY.openSession();
        session.beginTransaction();

        Query q = session.createQuery("FROM ClientCase as cs "
                + "left join fetch cs.client as clt "
                + "left join fetch cs.productType as pt "
                + "left join fetch cs.consultants as consul "
                + "left join fetch cs.caseStatus as cstatus "
                + "left join fetch clt.addresses as addr "
                + "left join fetch clt.incomes as inc "
                + "left join fetch clt.incomeBusinessActivities as ba "
                + "left join fetch inc.branch as br "
                + "left join fetch inc.employmentType as empltype "
                + "left join fetch ba.branch as br2 "
                + "left join fetch ba.employmentType as empltype2 "
                + "left join fetch clt.requiredDocumentses as rd "
                + "where cs.beginDate <= :dateNow "
                + "and cs.endDate < :dateNow "
                + "and clt.idClient = :fk "
                + "and cs.caseStatus = 1 "
                + "order by cs.beginDate desc, cs.idClientCase desc ");

        q.setParameter("dateNow", new DateTime().toDate());
        q.setParameter("fk", fkClient);
        q.setMaxResults(100);

        list = q.list();

        session.getTransaction().commit();
        session.close();
        return list;
    }

    public List<ClientCase> premiumCasesSelectedClient(Integer fkClient) {
        return casesSelectedClient(fkClient, 100, true);
    }

    public List<ClientCase> currentCasesSelectedClient(Integer fkClient) {
        List<ClientCase> list;
        Session session = HibernateUtil.SESSION_FACTORY.openSession();
        try {
            session.beginTransaction();

            Query q = session.createQuery("FROM ClientCase as cs "
                    + "left join fetch cs.client as clt "
                    + "left join fetch cs.productType as pt "
                    + "left join fetch cs.consultants as consul "
                    + "left join fetch cs.caseStatus as cstatus "
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
                    + "and cs.caseStatus >= 2 "
                    + "and cs.caseStatus <= 7 "
                    + "order by cs.beginDate desc, cs.idClientCase desc ");

            q.setParameter("dateNow", new DateTime().toDate());
            q.setParameter("fk", fkClient);
            q.setMaxResults(100);

            list = q.list();
            session.getTransaction().commit();
        } finally {

            session.close();
        }
        return list;
    }

    public List<ClientCase> finishedCasesSelectedClient(Integer fkClient) {
        List<ClientCase> list;
        Session session = HibernateUtil.SESSION_FACTORY.openSession();
        try {
            session.beginTransaction();

            Query q = session.createQuery("FROM ClientCase as cs "
                    + "left join fetch cs.client as clt "
                    + "left join fetch cs.productType as pt "
                    + "left join fetch cs.consultants as consul "
                    + "left join fetch cs.caseStatus as cstatus "
                    + "left join fetch clt.addresses as addr "
                    + "left join fetch clt.incomes as inc "
                    + "left join fetch clt.incomeBusinessActivities as ba "
                    + "left join fetch inc.branch as br "
                    + "left join fetch inc.employmentType as empltype "
                    + "left join fetch ba.branch as br2 "
                    + "left join fetch ba.employmentType as empltype2 "
                    + "left join fetch clt.requiredDocumentses as rd "
                    + "where clt.idClient = :fk "
                    + "and cs.caseStatus > 7 "
                    + "order by cs.beginDate desc, cs.idClientCase desc ");

            q.setParameter("fk", fkClient);
            q.setMaxResults(100);

            list = q.list();
            session.getTransaction().commit();
        } finally {

            session.close();
        }

        return list;
    }

    public ClientCase getClientCaseWithConsultantDetails(int idClientCase) {
        ClientCase cs;
        Session session = HibernateUtil.SESSION_FACTORY.openSession();
        try {
            session.beginTransaction();

            Query q = session.createQuery("FROM ClientCase as cs "
                    + "left join fetch cs.client as clt "
                    + "left join fetch cs.productType as pt "
                    + "left join fetch cs.consultant as consultant "
                    + "left join fetch consultant.addresses as addr2 "
                    + "left join fetch cs.consultants as consul "
                    + "left join fetch cs.caseStatus as cstatus "
                    + "left join fetch consul.consultantRatings as crs "
                    + "left join fetch consul.addresses as addr "
                    + "left join fetch consul.institutions as inst "
                    + "left join fetch consul.workingPlace as work "
                    + "left join fetch consul.productTypes as ptypes "
                    + "left join fetch cs.productDetails as pd "
                    + "where cs.idClientCase = :id");

            q.setParameter("id", idClientCase);

            cs = (ClientCase) q.list().get(0);
            session.getTransaction().commit();
        } finally {

            session.close();
        }
        return cs;
    }

    public ClientCase getClientCaseWithClientDetails(int idClientCase) {
        ClientCase cs;
        Session session = HibernateUtil.SESSION_FACTORY.openSession();
        try {
            session.beginTransaction();

            Query q = session.createQuery("FROM ClientCase as cs "
                    + "left join fetch cs.client as clt "
                    + "left join fetch cs.productType as pt "
                    + "left join fetch cs.caseStatus as cstatus "
                    + "left join fetch clt.addresses as addr "
                    + "left join fetch clt.incomes as inc "
                    + "left join fetch clt.incomeBusinessActivities as ba "
                    + "left join fetch inc.branch as br "
                    + "left join fetch inc.employmentType as empltype "
                    + "left join fetch ba.branch as br2 "
                    + "left join fetch ba.employmentType as empltype2 "
                    + "left join fetch clt.requiredDocumentses as rd "
                    + "left join fetch cs.productDetails as pd "
                    + "where cs.idClientCase = :id");

            q.setParameter("id", idClientCase);

            cs = (ClientCase) q.list().get(0);
            session.getTransaction().commit();
        } finally {

            session.close();
        }
        return cs;
    }

    public List<ClientCase> ownedCasesSelectedConsultant(Integer fkConsultant) {
        List<ClientCase> list;
        Session session = HibernateUtil.SESSION_FACTORY.openSession();
        try {
            session.beginTransaction();

            Query q = session.createQuery("FROM ClientCase as cs "
                    + "left join fetch cs.client as clt "
                    + "left join fetch cs.productType as pt "
                    + "left join fetch cs.consultant as cons "
                    + "left join fetch cs.caseStatus as cstatus "
                    + "left join fetch clt.addresses as addr "
                    + "left join fetch clt.incomes as inc "
                    + "left join fetch clt.incomeBusinessActivities as ba "
                    + "left join fetch inc.branch as br "
                    + "left join fetch inc.employmentType as empltype "
                    + "left join fetch ba.branch as br2 "
                    + "left join fetch ba.employmentType as empltype2 "
                    + "left join fetch clt.requiredDocumentses as rd "
                    + "where cons.idConsultant = :fk "
                    + "and cs.caseStatus > 1 "
                    + "and cs.caseStatus < 8 "
                    + "order by cs.beginDate desc, cs.idClientCase desc ");

            q.setParameter("fk", fkConsultant);

            list = q.list();

            Set s = new HashSet<ClientCase>(list);
            list.clear();
            list.addAll(s);
            session.getTransaction().commit();
        } finally {

            session.close();
        }
        return list;
    }

    public List<ClientCase> finishedCasesSelectedConsultant(int idConsultant) {
        List<ClientCase> list;
        Session session = HibernateUtil.SESSION_FACTORY.openSession();
        try {
            session.beginTransaction();

            Query q = session.createQuery("FROM ClientCase as cs "
                    + "left join fetch cs.client as clt "
                    + "left join fetch cs.productType as pt "
                    + "left join fetch cs.consultant as cons "
                    + "left join fetch cs.caseStatus as cstatus "
                    + "left join fetch clt.addresses as addr "
                    + "left join fetch clt.incomes as inc "
                    + "left join fetch clt.incomeBusinessActivities as ba "
                    + "left join fetch inc.branch as br "
                    + "left join fetch inc.employmentType as empltype "
                    + "left join fetch ba.branch as br2 "
                    + "left join fetch ba.employmentType as empltype2 "
                    + "left join fetch clt.requiredDocumentses as rd "
                    + "left join fetch cs.caseRating "
                    + "where cons.idConsultant = :fk "
                    + "and cs.caseStatus = 9 "
                    + "order by cs.beginDate desc, cs.idClientCase desc ");

            q.setParameter("fk", idConsultant);

            list = q.list();

            Set s = new HashSet<ClientCase>(list);
            list.clear();
            list.addAll(s);
            session.getTransaction().commit();
        } finally {

            session.close();
        }
        return list;
    }

    public void updateClientCase(ClientCase clientCase) {
        Session session = HibernateUtil.SESSION_FACTORY.openSession();
        try {
            session.beginTransaction();

            session.update(clientCase);
            session.getTransaction().commit();
        } finally {

            session.close();
        }
    }

    public boolean checkConsultantAccess(int idClientCase, int idConsultant) {
        List<ClientCase> list;

        Session session = HibernateUtil.SESSION_FACTORY.openSession();
        try {
            session.beginTransaction();

            Query q = session.createQuery("From ClientCase as cc "
                    + " left join fetch cc.consultant as cons "
                    + " left join fetch cc.caseStatus as cstat "
                    + " where (cstat.idCaseStatus = 1 or  cons.idConsultant = :idConsultant) and cc.idClientCase = :idClientCase");

            q.setParameter("idConsultant", idConsultant);
            q.setParameter("idClientCase", idClientCase);
            list = q.list();
            session.getTransaction().commit();

        } finally {

            session.close();
        }
        return !(list == null || list.isEmpty());
    }

    public List<ClientCase> premiumCasesSelectedConsultant(int idConsultant) {
        List<ClientCase> list;
        Session session = HibernateUtil.SESSION_FACTORY.openSession();
        try {
            session.beginTransaction();

            Query q = session.createQuery("FROM ClientCase as cs "
                    + "left join fetch cs.client as clt "
                    + "left join fetch cs.productType as pt "
                    + "left join fetch cs.consultant as cons "
                    + "left join fetch cs.caseStatus as cstatus "
                    + "left join fetch clt.addresses as addr "
                    + "left join fetch clt.incomes as inc "
                    + "left join fetch clt.incomeBusinessActivities as ba "
                    + "left join fetch inc.branch as br "
                    + "left join fetch inc.employmentType as empltype "
                    + "left join fetch ba.branch as br2 "
                    + "left join fetch ba.employmentType as empltype2 "
                    + "left join fetch clt.requiredDocumentses as rd "
                    + "where cons.idConsultant = :fk "
                    + "and cs.caseStatus = 1 "
                    + "and cs.premium = 1 "
                    + "order by cs.beginDate desc, cs.idClientCase desc ");

            q.setParameter("fk", idConsultant);

            list = q.list();

            Set s = new HashSet<ClientCase>(list);
            list.clear();
            list.addAll(s);
            session.getTransaction().commit();
        } finally {

            session.close();
        }
        return list;
    }

    public boolean checkClientFinishedCases(Integer idClient) {
        List<ClientCase> list;
        Session session = HibernateUtil.SESSION_FACTORY.openSession();
        try {
            session.beginTransaction();

            Query q = session.createQuery("FROM ClientCase as cs "
                    + "left join fetch cs.client as clt "
                    + "left join fetch cs.caseStatus as cstat "
                    + "where clt.idClient = :idClient and cstat.idCaseStatus = 9 ");

            q.setParameter("idClient", idClient);

            list = q.list();
            session.getTransaction().commit();
        } finally {

            session.close();
        }
        return list != null && !list.isEmpty();
    }

    public boolean checkClientCases(Integer idClient) {
        List<ClientCase> list;
        Session session = HibernateUtil.SESSION_FACTORY.openSession();
        try {
            session.beginTransaction();

            Query q = session.createQuery("FROM ClientCase as cs "
                    + "left join fetch cs.client as clt "
                    + "left join fetch cs.caseStatus as cstat "
                    + "where clt.idClient = :idClient ");

            q.setParameter("idClient", idClient);

            list = q.list();
            session.getTransaction().commit();
        } finally {

            session.close();
        }
        return list != null && !list.isEmpty();
    }
}
