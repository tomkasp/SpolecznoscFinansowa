package test;

import generatorPDF.core.GeneratorPDF;
import java.util.List;
import sql.dao.KlienciDao;
import sql.entity.Klienci;

/**
 *
 * @author WR1EI1
 */
public class NewMain {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) { 
       GeneratorPDF.generuj(1);
        KlienciDao kdao=new KlienciDao();
        List<Klienci> l = kdao.getKlientList();
        System.out.println("P: "+ l.get(0).getImie() );
    
        //KlienciDao kdao=new KlienciDao();
        //List<Klienci> l = kdao.getKlientList();
        //System.out.println("P: "+ l.get(0).getImie() );     
        
        System.out.println("AKTUALNA");
        
        //kdao.readKlient(5);
        
//        Session session = NewHibernateUtil.getSessionFactory().openSession();
//        session.beginTransaction().begin();
//        
//        Klienci k=new Klienci();
//        k.setImie("test1");
//        session.save(k);
//        
//        session.load(Klienci.class,5);
//        
//        session.getTransaction().commit();
//        session.close();
        
    }
    
}
