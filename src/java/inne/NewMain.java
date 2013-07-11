/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package inne;

import java.util.Calendar;
import sql.dao.UzytkownikDao;

/**
 *
 * @author WR1EI1
 */
public class NewMain {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
//        FileUploaderFTP ftp=new FileUploaderFTP();
//        
//        ftp.makeDirectory("rice/"+1+" Klient/");
//        ftp.makeDirectory("rice/"+1+" Klient/"+1+" Kredyt/");
//        ftp.Upload("C:\\Documents and Settings\\user\\Pulpit\\Kalkulator decyzji\\wszystkieDokumenty.pdf" , "rice/"+1+" Klient/"+1+" Kredyt/u.pdf");
//        
//        Foo fooo=new Foo();
//        //Foo.decrementInstanceCount();
//       System.out.println( Foo.getTotalLiveInstances() );
       
         Calendar cal = Calendar.getInstance();
         System.out.println(cal.getTimeInMillis());
         System.out.println("sddd");
	 System.out.println(cal.getTimeInMillis());
        
    }
}
