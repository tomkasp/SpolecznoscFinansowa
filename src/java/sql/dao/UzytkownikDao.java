package sql.dao;

import org.hibernate.Query;
import org.hibernate.Session;
import sql.util.NewHibernateUtil;
import sql.util.Security;


public class UzytkownikDao {
    
        public Boolean logowanie(String login, String haslo){        
        try{   
            Session session = NewHibernateUtil.getSessionFactory().openSession();  
            
            System.out.println("probuje logowac");
            Query q = session.createQuery("FROM Uzytkownicy where login='" + login + "' AND haslo='" + Security.sha512(haslo) + "' ");
            
            System.out.println(q);
            if(q.list().isEmpty()){
                //System.out.println("sory,nie ma CIe w bazie");
                return false;
            }
            else
            {
                /*
                List resultList = q.list();
                session.getTransaction().commit();
            
                for(Object o : resultList){
                    User u = (User) o;
                    System.out.println("CZARY MARY:" + u.getLogin());
                } 
                */
            return true;
            }
        }
        catch(Exception e){        
        }
    
    //session.close();    
    return false;
    }
}
