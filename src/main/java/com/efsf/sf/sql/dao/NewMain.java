/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.efsf.sf.sql.dao;

import com.efsf.sf.sql.entity.Address;
import com.efsf.sf.sql.entity.Client;
import com.efsf.sf.sql.entity.Consultant;
import java.security.NoSuchAlgorithmException;

/**
 *
 * @author WR1EI1
 */
public class NewMain {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws NoSuchAlgorithmException {
        
          ConsultantDAO cdao=new ConsultantDAO(); 
          Consultant c=cdao.readConsultantForSettings(18);     
          
          System.out.println( c.getAddresses().iterator().next().getIdAddress() );
          
//        ClientDAO cdao=new ClientDAO();
//        Client c=cdao.read(1);
//         
//        System.out.println(c.getUser().getLogin());
//          
//        System.out.println( Security.sha1("admin") );
//        
//        UserDAO udao=new UserDAO();
//        User u=udao.login("admin@admin.pl", "admin");
//        System.out.println(u.getLogin());
//       
//        AddressDAO adao=new AddressDAO();
//        Address a=adao.loadMainAddressFromFkConsultant(21);
//        System.out.println(a.getIdAddress());
//        
    }
}
