/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sql.entity;

import java.math.BigDecimal;
import org.hibernate.Session;
import sql.util.HibernateUtil;

/**
 *
 * @author Admin
 */
public class test {
    public static void main(String args[]){
        
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        
        Uzytkownik uzyt = new Uzytkownik();
        uzyt.setLogin("admin");
        uzyt.setHaslo("admi");
        uzyt.setImie("Grzegorz");
        session.save(uzyt);
        
        Klienci klient = new Klienci();
        klient.setImie("grzes");
        klient.setNazwisko("madej");
        klient.setUzytkownik(uzyt);
        
        Kredyty kredyt = new Kredyty();
        kredyt.setKwotaKonsolidacji(BigDecimal.valueOf(1234.12));
        kredyt.setNazwaBanku("testowa32");
        session.save(kredyt);
        
        KlienciKredyty kkw = new KlienciKredyty();
        kkw.setKlienci(klient);
        kkw.setKredyty(kredyt);
        kkw.setWspolkredytobiorca(true);
         
        klient.getKlienciKredyties().add(kkw);
  
        session.save(klient);
        
        session.getTransaction().commit();
        session.close();
    }
}
