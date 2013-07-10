/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sql.entity;

import org.hibernate.Session;
import sql.util.HibernateUtil;

public class Pobieranie {

    public static void main(String args[]){
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        
        //Uzytkownik usr = (Uzytkownik)session.load(Uzytkownik.class, 9);
        //System.out.println("uzytkownik: " + usr.getLogin());
        
        Klienci klient = (Klienci)session.load(Klienci.class, 7);
        Uzytkownik uzytkownik = klient.getUzytkownik();
        
        
        System.out.println("uzytkownik: " + uzytkownik.getLogin());
        
        
        
        session.getTransaction().commit();
        session.close();
        
    }
    
}
