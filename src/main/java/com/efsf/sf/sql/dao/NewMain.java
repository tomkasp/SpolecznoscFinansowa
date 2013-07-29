/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.efsf.sf.sql.dao;

import com.efsf.sf.sql.entity.Client;
import com.efsf.sf.sql.entity.Consultant;
import com.efsf.sf.sql.entity.User;
import com.efsf.sf.util.Security;
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
        
//          ConsultantDAO cdao=new ConsultantDAO(); 
//          Consultant c=cdao.read(4);       
//          ClientDAO cdao=new ClientDAO();
//          Client c=cdao.read(1);
//          System.out.println(c.getUser().getLogin());
//          System.out.println( Security.sha1("admin") );
        
        UserDAO udao=new UserDAO();
        
        User u=udao.login("admin@admin.pl", "admin");
        
        System.out.println(u.getLogin());
    }
}
