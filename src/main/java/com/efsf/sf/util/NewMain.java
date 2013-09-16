/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.efsf.sf.util;

import com.efsf.sf.sql.dao.ClientDAO;
import com.efsf.sf.sql.dao.GenericDao;
import com.efsf.sf.sql.dao.InstitutionDAO;
import com.efsf.sf.sql.entity.Client;
import com.efsf.sf.sql.entity.Institution;
import com.efsf.sf.sql.entity.Region;
import com.efsf.sf.sql.util.HibernateUtil;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.hibernate.Session;

/**
 *
 * @author WR1EI1
 */
public class NewMain {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {


        InstitutionDAO idao = new InstitutionDAO();
        
        @SuppressWarnings("unchecked")
        List<Institution> list = idao.bankList();

        for (Institution i : list) {
            System.out.println(i.getName());
        }
        
        System.out.println( "                    KONIEC                 " );
        
        for ( Iterator<Institution> it=list.iterator() ; it.hasNext(); ) {
            Institution i = it.next();
            System.out.println( i.getName() );
        }
        
        
        
        

    }
    
    public enum DayOfWeek {
 PONIEDZIAŁEK, WTOREK, SRODA,
 CZWARTEK, PIĄTEK, SOBOTA, NIEDZIELA
 
 
}
    
}
