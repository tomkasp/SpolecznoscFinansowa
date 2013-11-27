package com.efsf.sf.sql.dao;

import com.efsf.sf.sql.entity.Client;
import com.efsf.sf.sql.util.HibernateUtil;
import java.io.Serializable;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;

public class ClientDAO implements Serializable{

    public Client read(int id) {
        Client client;
        Session session = HibernateUtil.SESSION_FACTORY.openSession();
        try {
            session.beginTransaction().begin();

            Query q = session.createQuery("FROM Client c left outer join fetch c.user as u WHERE id_client = :id");
            q.setParameter("id", id);

            client = (Client) q.list().get(0);
            session.getTransaction().commit();
        } finally {

            session.close();
        }
        return client;
    }

    public void save(Client client) {

        Session session = HibernateUtil.SESSION_FACTORY.openSession();
        try {
            session.beginTransaction();

            session.save(client);
            session.getTransaction().commit();
        } finally {


            session.close();
        }
    }

    public void decrementPoints(Client client, Integer p) {
        //odejmowanie punktow po dodaniu wniosku

        int points;

        Session session = HibernateUtil.SESSION_FACTORY.openSession();
        try {
            session.beginTransaction();

            Client cli = (Client) session.load(Client.class, client.getIdClient());
            points = cli.getPoints();
            points = points - p;
            cli.setPoints(points);
            session.save(cli);
            session.getTransaction().commit();
        } finally {
            session.close();
        }

    }

    public void update(Client client) {

        Session session = HibernateUtil.SESSION_FACTORY.openSession();
        try {
            session.beginTransaction();

            session.update(client);
            session.getTransaction().commit();
        } finally {
            session.close();
        }
    }

    public void delete(Client client) {

        Session session = HibernateUtil.SESSION_FACTORY.openSession();
        try {
            session.beginTransaction().begin();

            session.delete(client);
            session.getTransaction().commit();
        } finally {
            session.close();
        }
    }

    public Client getClientWithIncomes(int idClient) {
        Client client;
        Session session = HibernateUtil.SESSION_FACTORY.openSession();
        try {
            session.beginTransaction().begin();

            Query q = session.createQuery("FROM Client as clt "
                    + "left join fetch clt.incomes as inc "
                    + "left join fetch clt.incomeBusinessActivities as ba "
                    + "left join fetch inc.branch as br "
                    + "left join fetch inc.employmentType as empltype "
                    + "left join fetch ba.branch as br2 "
                    + "left join fetch ba.employmentType as empltype2 "
                    + "where clt.idClient = :id");

            q.setParameter("id", idClient);
            client = (Client) q.list().get(0);
            session.getTransaction().commit();
        } finally {

            session.close();
        }
        return client;
    }

    public Client checkClientForNewApplication(int idClient) {
        Session session = HibernateUtil.SESSION_FACTORY.openSession();
        List list;
        try {
            session.beginTransaction().begin();

            Query q = session.createQuery("FROM Client as clt "
                    + "left join fetch clt.incomes as inc "
                    + "left join fetch clt.incomeBusinessActivities as ba "
                    + "left join fetch clt.addresses as addr "
                    + "left join fetch addr.region as reg "
                    + "where clt.idClient = :id");

            q.setParameter("id", idClient);

            list = q.list();
            session.getTransaction().commit();
        } finally {

            session.close();
        }
        if (list != null && list.size() > 0) {
            return (Client) list.get(0);
        }
        return null;
    }

    public Client readClientForSettings(int id) {
        Client client = null;
        Session session = HibernateUtil.SESSION_FACTORY.openSession();
        try {
            session.beginTransaction().begin();
            Query q;
            q = session.createQuery("FROM Client c "
                    + " LEFT JOIN FETCH c.user as u "
                    + " LEFT JOIN FETCH c.maritalStatus as m "
                    + " LEFT JOIN FETCH c.education as e "
                    + " LEFT JOIN FETCH c.addresses as a "
                    + " LEFT JOIN FETCH c.incomes as i "
                    + " LEFT JOIN FETCH i.branch as br "
                    + " LEFT JOIN FETCH i.employmentType as et "
                    + " LEFT JOIN FETCH c.incomeBusinessActivities as iba "
                    + " LEFT JOIN FETCH iba.branch as br2 "
                    + " LEFT JOIN FETCH iba.employmentType as et2 "
                    + " WHERE id_client = :id ");

            q.setParameter("id", id);
            client = (Client) q.list().get(0);

            session.getTransaction().commit();
        } finally {

            session.close();
        }

        return client;
    }

    public Client getClientForCase(Integer idSprawaKlienta) {
        Session session = HibernateUtil.SESSION_FACTORY.openSession();
        Client klient;
        try{
        session.beginTransaction();
        Query q = session.createQuery("from Client cl "
                + "left join fetch cl.clientCases cc "
                + "left join fetch cl.incomes inc "
                + "left join fetch cl.incomeBusinessActivities iba "
                + "left join fetch inc.employmentType incet "
                + "left join fetch iba.employmentType ibaet "
                + "where cc.idClientCase= :sprawaKlienta ");
        q.setParameter("sprawaKlienta", idSprawaKlienta);

        klient = (Client) q.list().get(0);

        session.getTransaction().commit();
        } finally {
        session.close();
        }
        return klient;
    }
}
