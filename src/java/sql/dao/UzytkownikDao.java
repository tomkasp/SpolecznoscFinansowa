package sql.dao;

import org.hibernate.Query;
import org.hibernate.Session;
import sql.util.NewHibernateUtil;
import sql.util.Security;

public class UzytkownikDao {

    public Boolean logowanie(String login, String haslo) {
        try {
            Session session = NewHibernateUtil.getSessionFactory().openSession();

            System.out.println("probuje logowac");
            Query q = session.createQuery("FROM Uzytkownik WHERE login='" + login + "' AND haslo='" + Security.sha1(haslo) + "' ");

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
