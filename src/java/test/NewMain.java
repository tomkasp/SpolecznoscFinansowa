package test;

import generatorPDF.core.GeneratorPDF;
import java.util.Date;
import java.util.List;
import org.hibernate.Session;
import sql.dao.KredytyDao;
import sql.dao.UzytkownikDao;
import sql.entity.Klienci;
import sql.entity.Kredyty;
import sql.entity.Uzytkownik;
import sql.util.NewHibernateUtil;

/**
 *
 * @author WR1EI1
 */
public class NewMain {

   
    public static void main(String[] args) { 
     //  GeneratorPDF.generuj(1);
     
//        KlienciDao kdao=new KlienciDao();
//        List<Klienci> l = kdao.getKlientList();
//        System.out.println("P: "+ l.get(0).getImie() );
//    
//        KredytyDao kredytDao=new KredytyDao();
//         List<Kredyty> l = kredytDao.getKredytyOneKlient(5);
//        System.out.println("P: "+ l.get(0).getNrUmowyPosrednictwa() );
//        
        //KlienciDao kdao=new KlienciDao();
        //List<Klienci> l = kdao.getKlientList();
        //System.out.println("P: "+ l.get(0).getImie() );     
              
        
        
        UzytkownikDao userDAO=new UzytkownikDao();
        Boolean b=userDAO.logowanie("ktosik", "dupa");
        System.out.println(b);
        
        
//        Uzytkownik u=new Uzytkownik();
//        u.setAktywne(Boolean.TRUE);
//        u.setDataUtworzenia(new Date());
//        u.setHaslo("admin");
//        u.setImie("Admin");
//        u.setLogin("admin");
//        u.setNazwisko("Admin");
//        u.setOddzial("1");
//        u.setRola("admin");
//        session.save(u);
//        session.getTransaction().commit();
       
    }
    
}
