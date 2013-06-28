package sql.dao;

import org.hibernate.Query;
import org.hibernate.Session;
import sql.util.HibernateUtil;
import sql.util.Security;

public class UzytkownikDao {

    public Boolean logowanie(String login, String haslo) {
        try {
            Session session = HibernateUtil.getSessionFactory().openSession();

            System.out.println("probuje logowac");
            Query q = session.createQuery("FROM Uzytkownik WHERE login='" + login + "' AND haslo='" + Security.sha512(haslo) + "' ");

            System.out.println(q);
            if (q.list().isEmpty()) {
                return false;
            } else {
                return true;
            }
        } catch (Exception e) {
        }

        //session.close();    
        return false;
    }
}
