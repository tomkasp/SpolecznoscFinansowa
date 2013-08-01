
package com.efsf.sf.sql.dao;

import com.efsf.sf.bean.LoginMB;
import com.efsf.sf.sql.entity.CaseStatus;
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
    
    public void saveClientCase(ClientCase client, LoginMB login){
       Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        
        CaseStatus caseStatus = (CaseStatus)session.load(CaseStatus.class, 1);
        ProductType pt = (ProductType)session.load(ProductType.class, 1);
        
        client.setClient(login.getClient());
        
        client.setProductType(pt);
        client.setCaseStatus(caseStatus);
        
        
        client.setPhase(1);
        client.setViewCounter(0);
        client.setDifficulty(0);
       
        session.save(client);
        
        
        session.getTransaction().commit();
        session.close(); 
        
        
    }

    public List last5Cases()
    {
         List<ClientCase> list;
         Session session = HibernateUtil.getSessionFactory().openSession();
         session.beginTransaction();
         
         Query q = session.createQuery("from ClientCase order by BeginDate");
         
         q.setMaxResults(5);
         
         list = q.list();
         
         return list;
    }
    

}
