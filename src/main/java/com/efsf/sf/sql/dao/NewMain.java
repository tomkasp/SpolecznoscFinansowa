/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.efsf.sf.sql.dao;

import com.efsf.sf.sql.entity.ClientCase;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import sun.audio.AudioPlayer;

/**
 *
 * @author WR1EI1
 */
public class NewMain {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws NoSuchAlgorithmException {
        
//        ConsultantDAO cdao=new ConsultantDAO(); 
//        Consultant c=cdao.readConsultantForSettings(18);     
//          
//        System.out.println( c.getAddresses().iterator().next().getIdAddress() );
//          
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
            
          
          
        ClientCaseDAO ccdao=new ClientCaseDAO();
        @SuppressWarnings("unchecked")
        List<ClientCase> list=ccdao.last5CasesSelectedClient(38);
        ClientCase cc=list.get(0);
        System.out.println(cc.getGoal());
        System.out.println(list.size());    
        
          
    }
}
