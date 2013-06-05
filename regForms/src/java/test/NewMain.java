package test;

import generatorPDF.core.GeneratorPDF;
import sql.dao.KlienciDao;
import sql.entity.Klienci;
import sql.util.NewHibernateUtil;

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
        //KlienciDao kdao=new KlienciDao();
        //kdao.readKlient(5);
        //bbbbhbbla
        System.out.println("h");
        
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
