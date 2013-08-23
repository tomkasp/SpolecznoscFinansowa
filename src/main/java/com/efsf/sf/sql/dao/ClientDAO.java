package com.efsf.sf.sql.dao;

import com.efsf.sf.sql.entity.Client;
import com.efsf.sf.sql.util.HibernateUtil;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;


public class ClientDAO {

    private int points;
    private String[] klienci;
    private String[][] ProduktyBankow;
    private String[][] wynik = new String[5][4];
    private String NazwaBanku;
    private String NazwaProduktu;

    public Client read(int id) {

        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction().begin();

        Query q = null;
        q = session.createQuery("FROM Client c left outer join fetch c.user as u WHERE id_client = :id");
        q.setParameter("id", id);

        Client client = (Client) q.list().get(0);

        session.getTransaction().commit();

        session.close();

        return client;
    }

    public void save(Client client) {

        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();

        session.save(client);

        session.getTransaction().commit();

        session.close();
    }

    public void decrementPoints(Client client, Integer p) {
        //odejmowanie punktow po dodaniu wniosku

        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();

        Client cli = (Client) session.load(Client.class, client.getIdClient());
        points = cli.getPoints();
        points = points - p;
        cli.setPoints(points);
        session.save(cli);


        session.getTransaction().commit();
        session.close();

    }

    public void update(Client client) {

        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();

        session.update(client);

        session.getTransaction().commit();

        session.close();
    }

    public void delete(Client client) {

        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction().begin();

        session.delete(client);

        session.getTransaction().commit();

        session.close();
    }


    public Client getClientWithIncomes(int idClient) {

        Session session = HibernateUtil.getSessionFactory().openSession();
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

        Client client = (Client) q.list().get(0);



        session.getTransaction().commit();

        session.close();

        return client;
    }

     public Client checkClientForNewApplication(int idClient)
     {
                Session session = HibernateUtil.getSessionFactory().openSession();
                session.beginTransaction().begin();
                
                Query q = session.createQuery("FROM Client as clt "
                     + "left join fetch clt.incomes as inc "
                     + "left join fetch clt.incomeBusinessActivities as ba "
                     + "left join fetch clt.addresses as addr "
                     + "left join fetch addr.region as reg "
                     + "where clt.idClient = :id");
    
                q.setParameter("id", idClient);
     
                List list = q.list();
                                
                session.getTransaction().commit();
                session.close();
                
                if (list != null && list.size() > 0)
                {
                    return (Client) list.get(0);
                }
                return null;         
     }

    public Client readClientForSettings(int id) {

        Session session = HibernateUtil.getSessionFactory().openSession();
        Client client = null;

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

        } catch (HibernateException exp) {
        } finally {
            session.close();
        }

        return client;
    }

    public Client getClientForCase(Integer idSprawaKlienta) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Query q = session.createQuery("from Client cl "
                + "left join fetch cl.clientCases cc "
                + "left join fetch cl.incomes inc "
                + "left join fetch cl.incomeBusinessActivities iba "
                + "left join fetch inc.employmentType incet "
                + "left join fetch iba.employmentType ibaet "
                + "where cc.idClientCase= :sprawaKlienta ");
        q.setParameter("sprawaKlienta", idSprawaKlienta);

        List l = q.list();
        setKlienci(new String[10]);

        Client klient = (Client) q.list().get(0);


        session.getTransaction().commit();
        session.close();

        return klient;
    }

    public String[] getKlienci() {
        return klienci;
    }

    public String[][] getProduktyBankow() {
        return ProduktyBankow;
    }

    public String[][] getWynik() {
        return wynik;
    }

    public String getNazwaBanku() {
        return NazwaBanku;
    }

    public String getNazwaProduktu() {
        return NazwaProduktu;
    }

    public void setKlienci(String[] klienci) {
        this.klienci = klienci;
    }

    public void setProduktyBankow(String[][] ProduktyBankow) {
        this.ProduktyBankow = ProduktyBankow;
    }

    public void setWynik(String[][] wynik) {
        this.wynik = wynik;
    }

    public void setNazwaBanku(String NazwaBanku) {
        this.NazwaBanku = NazwaBanku;
    }

    public void setNazwaProduktu(String NazwaProduktu) {
        this.NazwaProduktu = NazwaProduktu;
    }
}
