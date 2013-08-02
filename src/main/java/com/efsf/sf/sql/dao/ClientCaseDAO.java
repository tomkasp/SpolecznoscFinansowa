package com.efsf.sf.sql.dao;

import com.efsf.sf.bean.LoginMB;
import com.efsf.sf.sql.entity.CaseStatus;
import com.efsf.sf.sql.entity.Client;
import com.efsf.sf.sql.entity.ClientCase;
import com.efsf.sf.sql.entity.ProductType;
import com.efsf.sf.sql.util.HibernateUtil;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author admin
 */
public class ClientCaseDAO {

    public void saveClientCase(ClientCase client) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();

            session.save(client);

        session.getTransaction().commit();
        session.close();

    }

    public List last5Cases()
    {
         List<ClientCase> list;
         Session session = HibernateUtil.getSessionFactory().openSession();
         session.beginTransaction();
         
    //     Query q = session.createQuery("from ClientCase c left outer join fetch c.productType as p left outer join fetch c.client as cil left outer join cil.addresses as add");
         Query q = session.createQuery("FROM ClinetCase fetch all properties order by beginDate");
         q.setMaxResults(5);
         
         list = q.list();
         
         
         session.getTransaction().commit();
         session.close();
         

         return list;
    }
    

        Query q = session.createQuery("from ClientCase order by BeginDate");

        q.setMaxResults(5);

        list = q.list();

        return list;
    }
}
