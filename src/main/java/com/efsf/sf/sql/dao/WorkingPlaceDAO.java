package com.efsf.sf.sql.dao;

import com.efsf.sf.sql.entity.WorkingPlace;
import com.efsf.sf.sql.util.HibernateUtil;
import java.util.ArrayList;
import java.util.List;
import javax.faces.model.SelectItem;
import org.hibernate.HibernateException;
import org.hibernate.Session;

public class WorkingPlaceDAO {
    
    public List workingPlaceList(){
        List<WorkingPlace> lista=null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        try{
        lista = session.createQuery("from WorkingPlace").list();
        }catch(HibernateException e)
        {}
        finally{
        //session.close();
        }
        return lista;
    }
    
    public ArrayList<SelectItem>  workingPlaceMap(){
        ArrayList<SelectItem> selectItems = new ArrayList<>();
        Session session = HibernateUtil.getSessionFactory().openSession();
        try{
        List<WorkingPlace> lista = session.createQuery("from WorkingPlace").list();
   
        while( lista.iterator().hasNext() ) {
           WorkingPlace wp=lista.iterator().next();
           selectItems.add(new SelectItem( wp.getName(), wp.getIdWorkingPlace().toString() ));
        }
        
        }catch(HibernateException e)
        {}
        finally{
        session.close();
        }
        return selectItems;
    }
}
